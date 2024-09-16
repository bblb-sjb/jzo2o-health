package com.jzo2o.health.service.impl;

import com.jzo2o.api.publics.SmsCodeApi;
import com.jzo2o.common.constants.UserType;
import com.jzo2o.common.enums.SmsBussinessTypeEnum;
import com.jzo2o.common.expcetions.BadRequestException;
import com.jzo2o.common.expcetions.RequestForbiddenException;
import com.jzo2o.common.utils.JwtTool;
import com.jzo2o.common.utils.StringUtils;
import com.jzo2o.health.model.domain.Member;
import com.jzo2o.health.model.domain.User;
import com.jzo2o.health.model.dto.request.LoginReqDTO;
import com.jzo2o.health.model.dto.request.MemberLoginReqDTO;
import com.jzo2o.health.service.ILoginService;
import com.jzo2o.health.service.IMemberService;
import com.jzo2o.health.service.IUserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author itcast
 */
@Service
public class LoginServiceImpl implements ILoginService {

    @Resource
    private IUserService userService;
    @Resource
    private JwtTool jwtTool;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private SmsCodeApi smsCodeApi;
    @Resource
    private IMemberService memberService;

    /**
     * 管理员登录
     *
     * @param loginReqDTO 管理员登录请求模型
     * @return token
     */
    @Override
    public String adminLogin(LoginReqDTO loginReqDTO) {
        //根据输入的账号查询用户信息
        User user = userService.findByUsername(loginReqDTO.getUsername());
        if (user == null) {
            throw new RequestForbiddenException("账号或密码错误，请重新输入");
        }
        // 比对密码
        if (!passwordEncoder.matches(loginReqDTO.getPassword(), user.getPassword())) {
            throw new RequestForbiddenException("账号或密码错误，请重新输入");
        }
        //生成令牌
        return jwtTool.createToken(user.getId(), user.getName(), user.getAvatar(), UserType.OPERATION);
    }

    /**
     * 普通用户登录
     *
     * @param memberLoginReqDTO 普通用户登录请求模型
     * @return token
     */
    @Override
    public String memberLogin(MemberLoginReqDTO memberLoginReqDTO) {
        // 校验验证码是否为空
        if (StringUtils.isBlank(memberLoginReqDTO.getVerifyCode())) {
            throw new BadRequestException("手机号码或短信验证码错误");
        }
        // 校验验证码是否正确
        //家政项目中，验证码业务类型是枚举类，固定死的，这里我们借用 SmsBussinessTypeEnum.SERVE_STAFF_LOGIN 类型
        boolean verifyResult = smsCodeApi.verify(memberLoginReqDTO.getPhone(), SmsBussinessTypeEnum.SERVE_STAFF_LOGIN, memberLoginReqDTO.getVerifyCode()).getIsSuccess();
        if (!verifyResult) {
            throw new BadRequestException("手机号码或短信验证码错误");
        }

        // 登录校验，根据手机查询普通用户信息
        Member member = memberService.findByPhone(memberLoginReqDTO.getPhone());

        // 自动注册
        if (member == null) {
            member = memberService.add(memberLoginReqDTO.getPhone());
        }

        // 生成登录token
        return jwtTool.createToken(member.getId(), member.getNickname(), member.getAvatar(), UserType.C_USER);
    }
}
