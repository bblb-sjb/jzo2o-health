package com.jzo2o.health.controller.admin;

import com.jzo2o.health.service.IReservationSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 预约设置操作
 *
 * @author itcast
 */
@Slf4j
@RestController("adminReservationBatchSettingController")
@RequestMapping("/admin/reservation-setting")
@Api(tags = "管理端 - 批量预约设置相关接口")
public class ReservationBatchSettingController {

    @Resource
    private IReservationSettingService reservationSettingService;

    @PostMapping("/upload")
    @ApiOperation("上传文件批量预约设置")
    public void upload(@RequestPart("file") MultipartFile file) {
        reservationSettingService.uploadBatchSetting(file);
        return;
    }
}
