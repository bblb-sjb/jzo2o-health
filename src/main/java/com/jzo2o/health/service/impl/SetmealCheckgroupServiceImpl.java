package com.jzo2o.health.service.impl;

import com.jzo2o.health.model.domain.SetmealCheckgroup;
import com.jzo2o.health.mapper.SetmealCheckgroupMapper;
import com.jzo2o.health.service.ISetmealCheckgroupService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
@Service
public class SetmealCheckgroupServiceImpl extends ServiceImpl<SetmealCheckgroupMapper, SetmealCheckgroup> implements ISetmealCheckgroupService {

    /**
     * 绑定套餐和检查组的关系
     *
     * @param setmealId    套餐id
     * @param checkgroupId 检查组id
     */
    @Override
    public void setSetmealAndCheckGroup(Integer setmealId, Integer checkgroupId) {
        SetmealCheckgroup setmealCheckgroup = new SetmealCheckgroup();
        setmealCheckgroup.setSetmealId(setmealId);
        setmealCheckgroup.setCheckgroupId(checkgroupId);
        baseMapper.insert(setmealCheckgroup);
    }
}
