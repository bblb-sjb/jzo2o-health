package com.jzo2o.health.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 普通用户登录请求模型
 *
 * @author itcast
 * @create 2023/11/2 15:48
 **/
@Data
@ApiModel("普通用户登录模型")
public class MemberLoginReqDTO {
    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号", required = true)
    private String phone;

    /**
     * 验证码
     */
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCode;
}
