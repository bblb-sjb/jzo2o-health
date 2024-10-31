package com.jzo2o.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.health.model.domain.ReservationSetting;
import com.jzo2o.health.model.dto.request.ReservationSettingUpsertReqDTO;
import com.jzo2o.health.model.dto.response.ReservationDateResDTO;
import com.jzo2o.health.model.dto.response.ReservationSettingResDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IReservationSettingService extends IService<ReservationSetting> {
    List<ReservationSettingResDTO> getReservationSettingByMonth(String date);

    void editNumberByDate(ReservationSettingUpsertReqDTO reservationSettingUpsertReqDTO);

    void uploadBatchSetting(MultipartFile file);

    ReservationDateResDTO getReservationDateByMonth(String month);
}
