package com.kevin.tool.spring.ioc.customlabel;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 *  解析器{@link KevinInfo}
 * @author lihongmin
 * @date 2019/11/26 15:41
 */
public class KevinInfoParser extends AbstractSingleBeanDefinitionParser {
    @Override
    protected Class<?> getBeanClass(Element element) {
        return KevinInfo.class;
    }

    @Override
    protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
        String name = element.getAttribute("name");
        builder.addPropertyValue("name", name);
    }
}
