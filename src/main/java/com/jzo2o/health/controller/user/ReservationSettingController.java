package com.jzo2o.health.controller.user;

import com.jzo2o.health.annotation.IgnoreToken;
import com.jzo2o.health.model.dto.response.ReservationDateResDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 预约设置操作
 *
 * @author itcast
 */
@Slf4j
@RestController("userReservationSettingController")
@RequestMapping("/user/reservation-setting")
@Api(tags = "用户端 - 预约设置相关接口")
public class ReservationSettingController {

    @GetMapping("/getReservationDateByMonth")
    @IgnoreToken
    @ApiOperation("按月查询可预约日期")
    @ApiImplicitParam(name = "month", value = "月份，格式：yyyy-MM", required = true, dataTypeClass = String.class)
    public ReservationDateResDTO getReservationDateByMonth(@RequestParam("month") String month) {
        return null;
    }
}
