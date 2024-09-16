package com.jzo2o.health.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jzo2o.health.model.domain.User;

/**
 * <p>
 * 管理员 服务类
 * </p>
 *
 * @author itcast
 * @since 2023-10-31
 */
public interface IUserService extends IService<User> {
    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);
}
