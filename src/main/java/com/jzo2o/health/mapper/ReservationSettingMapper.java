package com.jzo2o.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzo2o.health.model.domain.ReservationSetting;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itcast
 * @since 2023-11-01
 */
public interface ReservationSettingMapper extends BaseMapper<ReservationSetting> {
    @Update("UPDATE reservation_setting SET reservations = reservations + 1 WHERE id = #{id} AND reservations < number")
    Integer updateReservations(@Param("id") Integer id);
}
