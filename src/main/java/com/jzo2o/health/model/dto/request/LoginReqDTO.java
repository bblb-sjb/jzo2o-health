package com.jzo2o.health.model.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("管理员登录模型")
@Data
public class LoginReqDTO {

    /**
     * 管理员账号
     */
    @ApiModelProperty("管理员账号")
    private String username;
    /**
     * 登录密码
     */
    @ApiModelProperty("登录密码")
    private String password;
}
