package com.jzo2o.health.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 忽略token校验的注解
 * 虽然在注册JwtTokenInterceptor时可以指定忽略的请求路径，但是无法指定请求方式。
 * 对于同一个请求url，请求方式可以有多种，无法做到区别对待
 * 例如：请求url为/dish，其中POST方式对应的方法需要进行token校验，GET方式对应的方法不需要进行token校验
 * 此时就需要通过IgnoreToken注解来标识当前方法不需要校验
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreToken {

}
