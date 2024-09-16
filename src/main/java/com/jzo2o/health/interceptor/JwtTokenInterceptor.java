package com.jzo2o.health.interceptor;

import com.jzo2o.common.expcetions.RequestUnauthorizedException;
import com.jzo2o.common.model.CurrentUserInfo;
import com.jzo2o.common.utils.JwtTool;
import com.jzo2o.common.utils.StringUtils;
import com.jzo2o.health.annotation.IgnoreToken;
import com.jzo2o.health.model.UserThreadLocal;
import com.jzo2o.health.properties.ApplicationProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * token检查拦截器，用于检查后台系统发送的请求
 */
@Slf4j
@Component
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Resource
    private ApplicationProperties applicationProperties;

    /**
     * token header名称
     */
    private static final String HEADER_TOKEN = "Authorization";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("拦截器拦截到请求：{}", request.getRequestURI());

        // 如果不是映射到方法的请求则直接放行，例如 /doc.html
        if (!(handler instanceof HandlerMethod)) {
            log.info("{}无需处理，直接放行", request.getRequestURI());
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        //检查当前请求的Controller方法上是否有IgnoreToken注解
        boolean hasIgnoreToken = handlerMethod.hasMethodAnnotation(IgnoreToken.class);
        if (hasIgnoreToken) {
            return true;
        }

        //获取前端提交的token
        String token = request.getHeader(HEADER_TOKEN);
        if (StringUtils.isEmpty(token)) {
            throw new RequestUnauthorizedException();
        }
        log.info("获取到token:{}", token);

        try {
            log.info("开始解析token");
            String tokenKey = applicationProperties.getTokenKey().get(JwtTool.getUserType(token) + "");
            if (StringUtils.isEmpty(tokenKey)) {
                throw new RequestUnauthorizedException();
            }

            JwtTool jwtTool = new JwtTool(tokenKey);
            CurrentUserInfo currentUserInfo = jwtTool.parseToken(token);
            UserThreadLocal.set(currentUserInfo);
            //token解析成功，放行
            return true;
        } catch (Exception ex) {
            log.error("token解析失败");
            //401表示未授权，需要前端配合跳转回登录页面
            response.setStatus(401);
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        // 清理用户信息
        UserThreadLocal.clear();
    }
}
