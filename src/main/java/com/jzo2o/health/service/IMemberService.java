package com.jzo2o.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.health.model.domain.Member;

/**
 * <p>
 * 普通用户 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-11-02
 */
public interface IMemberService extends IService<Member> {
    /**
     * 根据手机号查询普通用户
     *
     * @param phone 手机号
     * @return 普通用户
     */
    Member findByPhone(String phone);

    /**
     * 新增普通用户
     *
     * @param phone 手机号
     * @return 普通用户
     */
    Member add(String phone);
}
