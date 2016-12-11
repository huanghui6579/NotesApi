package com.yunxinlink.notes.api.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对于API的接口而言，提交请求时是否可以忽视token，一般仅限于登录、注册、绑定账号
 * @author tiger
 * @date 2016年12月4日 上午1:13:54
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TokenIgnore {

}
