package com.jzo2o.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.common.model.PageResult;
import com.jzo2o.health.model.domain.Setmeal;
import com.jzo2o.health.model.dto.request.CommonPageQueryReqDTO;
import com.jzo2o.health.model.dto.response.SetmealDetailResDTO;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
public interface ISetmealService extends IService<Setmeal> {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    List<Setmeal> findAll();

    Setmeal findById(int id);

    /**
     * 查询套餐详情（包含关联检查组、检查项）
     *
     * @param id 套餐id
     * @return 套餐详情
     */
    SetmealDetailResDTO findDetail(Integer id);

    PageResult<Setmeal> findPage(CommonPageQueryReqDTO commonPageQueryReqDTO);
}
