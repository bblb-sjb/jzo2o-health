package com.jzo2o.health.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jzo2o.health.model.domain.Setmeal;
import com.jzo2o.health.model.dto.response.SetmealDetailResDTO;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
public interface SetmealMapper extends BaseMapper<Setmeal> {
    /**
     * 查询套餐详情（包含关联检查组、检查项）
     *
     * @param id 套餐id
     * @return 套餐详情
     */
    SetmealDetailResDTO findDetail(@Param("id") Integer id);
}
