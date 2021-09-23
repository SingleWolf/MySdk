package com.walker.protect.helper;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author  : walker
 * Date    : 2021/9/18  1:23 下午
 * Email   : feitianwumu@163.com
 * Summary : Protector
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Protector {
    int level();
    int resultType();
}
