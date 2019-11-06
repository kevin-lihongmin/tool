package com.kevin.tool.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.XmlWebApplicationContext;

/**
 *  Spring5 {@link BeanFactory}
 * @author kevin
 * @date 2019/11/6 9:55
 * @since 1.0.0
 */
public class BeanFactoryTest {

    public static void main(String[] args) {
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource(""));
        Object kevin = beanFactory.getBean("kevin");
        System.out.println(kevin);
    }
}
