package com.common.service.enable;

import com.common.service.ConfShippingSiteService;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 *  发货装运点配置服务 开关
 * @author kevin
 * @date 2020/9/4 16:38
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import(ConfShippingSiteService.class)
public @interface EnableConfShippingSiteService {
}
