package com.jzo2o.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.health.model.domain.SetmealCheckgroup;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
public interface ISetmealCheckgroupService extends IService<SetmealCheckgroup> {
    /**
     * 绑定套餐和检查组的关系
     *
     * @param setmealId    套餐id
     * @param checkgroupId 检查组id
     */
    void setSetmealAndCheckGroup(Integer setmealId, Integer checkgroupId);
}
