package com.kevin.tool.spring.ioc.customlabel;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 *  自定义命名空间 初始化操作器
 * @author kevin
 * @date 2021/2/25 9:57
 * @since 1.0.0
 */
public class KevinNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("kevinInfo", new KevinInfoParser());
    }
}
