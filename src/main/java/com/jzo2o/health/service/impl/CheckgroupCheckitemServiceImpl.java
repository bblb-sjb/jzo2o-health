package com.jzo2o.health.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jzo2o.health.mapper.CheckgroupCheckitemMapper;
import com.jzo2o.health.model.domain.CheckgroupCheckitem;
import com.jzo2o.health.service.ICheckgroupCheckitemService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
@Service
public class CheckgroupCheckitemServiceImpl extends ServiceImpl<CheckgroupCheckitemMapper, CheckgroupCheckitem> implements ICheckgroupCheckitemService {
    /**
     * 删除关联关系
     *
     * @param checkGroupId 检查组id
     */
    @Override
    public void deleteAssociation(Integer checkGroupId) {
        LambdaQueryWrapper<CheckgroupCheckitem> queryWrapper = Wrappers.<CheckgroupCheckitem>lambdaQuery().eq(CheckgroupCheckitem::getCheckgroupId, checkGroupId);
        baseMapper.delete(queryWrapper);
    }

    /**
     * 根据检查组id查询检查项id
     *
     * @param checkGroupId 检查组id
     * @return 检查项id
     */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId) {
        LambdaQueryWrapper<CheckgroupCheckitem> queryWrapper = Wrappers.<CheckgroupCheckitem>lambdaQuery()
                .eq(CheckgroupCheckitem::getCheckgroupId, checkGroupId)
                .select(CheckgroupCheckitem::getCheckitemId);
        List<CheckgroupCheckitem> checkgroupCheckitemList = baseMapper.selectList(queryWrapper);
        if (ObjectUtil.isEmpty(checkgroupCheckitemList)) {
            return Collections.emptyList();
        }

        return checkgroupCheckitemList.stream().map(CheckgroupCheckitem::getCheckitemId).collect(Collectors.toList());
    }

    /**
     * 新增关联关系
     *
     * @param checkGroupId 检查组id
     * @param checkItemId  检查项id
     */
    @Override
    public void add(Integer checkGroupId, Integer checkItemId) {
        CheckgroupCheckitem checkgroupCheckitem = new CheckgroupCheckitem();
        checkgroupCheckitem.setCheckgroupId(checkGroupId);
        checkgroupCheckitem.setCheckitemId(checkItemId);
        baseMapper.insert(checkgroupCheckitem);
    }

    /**
     * 根据检查项id查询数量
     *
     * @param checkItemId 检查项id
     * @return 数量
     */
    @Override
    public long findCountByCheckItemId(Integer checkItemId) {
        LambdaQueryWrapper<CheckgroupCheckitem> queryWrapper = Wrappers.<CheckgroupCheckitem>lambdaQuery().eq(CheckgroupCheckitem::getCheckitemId, checkItemId);
        return baseMapper.selectCount(queryWrapper);
    }
}
