package com.jzo2o.health.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.health.mapper.ReservationSettingMapper;
import com.jzo2o.health.model.domain.ReservationSetting;
import com.jzo2o.health.model.domain.User;
import com.jzo2o.health.model.dto.request.ReservationSettingUpsertReqDTO;
import com.jzo2o.health.model.dto.response.ReservationDateResDTO;
import com.jzo2o.health.model.dto.response.ReservationSettingResDTO;
import com.jzo2o.health.service.IReservationSettingService;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationSettingServiceImpl extends ServiceImpl<ReservationSettingMapper, ReservationSetting> implements IReservationSettingService {

    @Override
    public List<ReservationSettingResDTO> getReservationSettingByMonth(String date) {
        //获取月份
        String month = date.split("-")[1];
        if(month.length()==1) {
            month = "0" + month;
        }
        date=date.split("-")[0]+"-"+month;
        //转化为LocalDate
        LocalDate beginDate = LocalDate.parse(date + "-01");
        //查询
        LambdaQueryWrapper<ReservationSetting> queryWrapper = Wrappers.<ReservationSetting>lambdaQuery()
                .ge(ReservationSetting::getOrderDate, beginDate.withDayOfMonth(1))
                .le(ReservationSetting::getOrderDate, beginDate.withDayOfMonth(beginDate.lengthOfMonth()));
        List<ReservationSetting> list = super.list(queryWrapper);
        //转化为ReservationSettingResDTO
        List<ReservationSettingResDTO> dtoList=list.stream().map(reservationSetting -> ReservationSettingResDTO.builder()
                .date(reservationSetting.getOrderDate().toString())
                .number(reservationSetting.getNumber())
                .reservations(reservationSetting.getReservations())
                .build()).collect(Collectors.toList());
        return dtoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editNumberByDate(ReservationSettingUpsertReqDTO reservationSettingUpsertReqDTO) {
        //校验人数
        if(reservationSettingUpsertReqDTO.getNumber()>1000||reservationSettingUpsertReqDTO.getNumber()<0) {
            throw new RuntimeException("预约人数不能大于1000或小于0");
        }
        //获取日期
        LocalDate orderDate = reservationSettingUpsertReqDTO.getOrderDate();
        //查询
        LambdaQueryWrapper<ReservationSetting> queryWrapper = Wrappers.<ReservationSetting>lambdaQuery()
                .eq(ReservationSetting::getOrderDate, orderDate);
        if(super.count(queryWrapper)>0) {
            //更新
            //1.查询当前预约人数
            ReservationSetting reservationSetting = super.getOne(queryWrapper);
            int reservations = reservationSetting.getReservations();
            if(reservations>reservationSettingUpsertReqDTO.getNumber()) {
                throw new RuntimeException("预约人数不能小于已预约人数");
            }
            //2.更新
            super.update(Wrappers.<ReservationSetting>lambdaUpdate()
                    .set(ReservationSetting::getNumber, reservationSettingUpsertReqDTO.getNumber())
                    .eq(ReservationSetting::getOrderDate, orderDate));
        }else {
            //新增
            ReservationSetting reservationSetting = new ReservationSetting();
            reservationSetting.setOrderDate(orderDate);
            reservationSetting.setNumber(reservationSettingUpsertReqDTO.getNumber());
            reservationSetting.setReservations(0);
            baseMapper.insert(reservationSetting);
        }
    }

    @Resource
    private ReservationSettingServiceImpl owner;
    @Override
    public void uploadBatchSetting(MultipartFile file) {
        //解析xlsx文件
        //1.获取文件名
        String filename = file.getOriginalFilename();
        //2.校验文件类型
        if(!filename.endsWith(".xlsx")) {
            throw new RuntimeException("文件类型错误");
        }
        List<ReservationSettingUpsertReqDTO>list=new ArrayList<>();
        //3.解析xlsx行数据
        // 获取输入流,使用 Apache POI 读取 .xlsx 文件
        try (InputStream inputStream = file.getInputStream();
             Workbook workbook = new XSSFWorkbook(inputStream)) {
            // 获取第一个工作表
            Sheet sheet = workbook.getSheetAt(0);
            // 遍历行和单元格
            int cellindex=0;
            for (Row row : sheet) {
                LocalDate orderDate = null;
                Integer number = null;
                String value=null;

                for (Cell cell : row) {
                    // 获取单元格内容
                    if(cellindex>=2){
                        if(cellindex%2==0){
                            value = cell.getStringCellValue();
                            String date=value.split(",")[0];
                            String month = date.split("-")[1];
                            if(month.length()==1) {
                                month = "0" + month;
                            }
                            date=date.split("-")[0]+"-"+month+"-"+date.split("-")[2];
                            orderDate = LocalDate.parse(date);
                        }
                        else{
                            number= (int) cell.getNumericCellValue();
                        }
                    }
                    // 跳过表头
                    if (cell.getRowIndex() == 0) {
                        cellindex++;
                        continue;
                    }
                    cellindex++;
                }
                if(cellindex>2&&orderDate != null&&number != null){
                    // 封装ReservationSettingUpsertReqDTO
                    ReservationSettingUpsertReqDTO reservationSettingUpsertReqDTO = new ReservationSettingUpsertReqDTO();
                    reservationSettingUpsertReqDTO.setOrderDate(orderDate);
                    reservationSettingUpsertReqDTO.setNumber(number);
                    list.add(reservationSettingUpsertReqDTO);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //去掉当前日期之前的数据，当天也去掉
        list.removeIf(reservationSettingUpsertReqDTO -> !reservationSettingUpsertReqDTO.getOrderDate().isAfter(LocalDate.now()));
        //批量更新
        for (ReservationSettingUpsertReqDTO reservationSettingUpsertReqDTO : list) {
            owner.editNumberByDate(reservationSettingUpsertReqDTO);
        }
    }

    @Override
    public ReservationDateResDTO getReservationDateByMonth(String month) {
        //获取月份
        if(month.length()==1) {
            month = "0" + month;
        }
        month=month+"-01";
        //转化为LocalDate
        LocalDate beginDate = LocalDate.parse(month);
        //查询
        LambdaQueryWrapper<ReservationSetting> queryWrapper = Wrappers.<ReservationSetting>lambdaQuery()
                .ge(ReservationSetting::getOrderDate, beginDate.withDayOfMonth(1))
                .le(ReservationSetting::getOrderDate, beginDate.withDayOfMonth(beginDate.lengthOfMonth()));
        List<ReservationSetting> list = super.list(queryWrapper);
        //转化为ReservationDateResDTO
        List<String> dateList=new ArrayList<>();
        for (ReservationSetting reservationSetting : list) {
            if (reservationSetting.getReservations() < reservationSetting.getNumber()) {
                dateList.add(reservationSetting.getOrderDate().toString());
            }
        }
        ReservationDateResDTO reservationDateResDTO = new ReservationDateResDTO();
        reservationDateResDTO.setDateList(dateList);
        return reservationDateResDTO;
    }
}
