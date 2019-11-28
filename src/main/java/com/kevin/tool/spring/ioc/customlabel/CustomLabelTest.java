package com.kevin.tool.spring.ioc.customlabel;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 *  Spring5 {@link BeanFactory}
 * @author kevin
 * @date 2019/11/6 9:55
 * @since 1.0.0
 */
public class CustomLabelTest {
    public static void main(String[] args) {
        BeanFactory beanFactory = new XmlBeanFactory(new ClassPathResource("spring-bean.xml"));
        KevinInfo kevinInfo = (KevinInfo)beanFactory.getBean("beanId");
        System.out.println(kevinInfo.getName());
    }
}

