package com.jzo2o.health.controller.open;

import com.jzo2o.health.model.dto.request.LoginReqDTO;
import com.jzo2o.health.model.dto.request.MemberLoginReqDTO;
import com.jzo2o.health.model.dto.response.LoginResDTO;
import com.jzo2o.health.service.ILoginService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author itcast
 */
@RestController("openLoginController")
@RequestMapping("/open/login")
@Api(tags = "白名单接口 - 登录相关接口")
public class LoginController {

    @Resource
    private ILoginService loginService;

    @PostMapping("/admin")
    @ApiOperation("管理员登录")
    public LoginResDTO adminLogin(@RequestBody LoginReqDTO loginReqDTO) {
        String token = loginService.adminLogin(loginReqDTO);
        return new LoginResDTO(token);
    }

    @PostMapping("/user")
    @ApiOperation("普通用户登录")
    public LoginResDTO userLogin(@RequestBody MemberLoginReqDTO memberLoginReqDTO) {
        String token = loginService.memberLogin(memberLoginReqDTO);
        return new LoginResDTO(token);
    }
}
