package com.xz.managersystem.web.resolver;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 类描述:      迁移到其他模块            <br>
 * 创建时间: 2016年3月25日 下午3:52:23 <br>
 *
 * @author 仲李
 * @version
 */
@Target({  PARAMETER })
@Retention(RUNTIME)
public @interface Authorization {

}