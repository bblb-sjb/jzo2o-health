package com.jzo2o.health.service;

import com.jzo2o.health.model.dto.request.LoginReqDTO;
import com.jzo2o.health.model.dto.request.MemberLoginReqDTO;

/**
 * 登录相关业务
 *
 * @author itcast
 */
public interface ILoginService {
    /**
     * 管理员登录
     *
     * @param loginReqDTO 管理员登录请求模型
     * @return token
     */
    String adminLogin(LoginReqDTO loginReqDTO);

    /**
     * 普通用户登录
     *
     * @param memberLoginReqDTO 普通用户登录请求模型
     * @return token
     */
    String memberLogin(MemberLoginReqDTO memberLoginReqDTO);
}
