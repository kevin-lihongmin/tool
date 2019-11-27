package com.kevin.tool.spring.ioc.customlabel;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class KevinNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("kevinInfo", new KevinInfoParser());
    }
}
