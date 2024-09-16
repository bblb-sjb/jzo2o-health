package com.jzo2o.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.health.model.domain.CheckgroupCheckitem;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
public interface ICheckgroupCheckitemService extends IService<CheckgroupCheckitem> {
    /**
     * 删除关联关系
     *
     * @param checkGroupId 检查组id
     */
    void deleteAssociation(Integer checkGroupId);

    /**
     * 根据检查组id查询检查项id
     *
     * @param checkGroupId 检查组id
     * @return 检查项id
     */
    List<Integer> findCheckItemIdsByCheckGroupId(Integer checkGroupId);

    /**
     * 新增关联关系
     *
     * @param checkGroupId 检查组id
     * @param checkItemId  检查项id
     */
    void add(Integer checkGroupId, Integer checkItemId);

    /**
     * 根据检查项id查询数量
     *
     * @param checkItemId 检查项id
     * @return 数量
     */
    long findCountByCheckItemId(Integer checkItemId);
}
