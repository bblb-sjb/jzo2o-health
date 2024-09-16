package com.jzo2o.health;

import com.jzo2o.api.publics.MapApi;
import com.jzo2o.api.publics.dto.response.LocationResDTO;
import com.jzo2o.health.model.domain.ReservationSetting;
import com.jzo2o.health.model.dto.response.SetmealDetailResDTO;
import com.jzo2o.health.service.IReservationSettingService;
import com.jzo2o.health.service.ISetmealService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class HealthApplicationTests {
    @Resource
    private MapApi mapApi;
    @Resource
    private ISetmealService setmealService;
    @Resource
    private IReservationSettingService reservationSettingService;


    @Test
    void contextLoads() {
    }

    @Test
    void test001() {
        LocationResDTO location = mapApi.getLocationByAddress("北京市昌平区三旗百汇市场");
        System.out.println(location);
    }

    @Test
    void findDetail() {
        SetmealDetailResDTO detail = setmealService.findDetail(12);
        System.out.println(detail);
    }

    @Test
    void test004() {
        ReservationSetting reservationSetting = new ReservationSetting();
        reservationSetting.setId(152);
        reservationSetting.setNumber(100);
        reservationSettingService.updateById(reservationSetting);
    }

}
