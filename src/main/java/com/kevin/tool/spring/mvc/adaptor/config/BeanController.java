package com.kevin.tool.spring.mvc.adaptor.config;

import com.kevin.tool.spring.mvc.adaptor.ControllerAdaptorController;
import com.kevin.tool.spring.mvc.adaptor.HttpRequestController;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

/**
 *  将com.kevin.tool.spring.mvc.adaptor下其他类型的控制器注入为Bean
 *
 * @author lihongmin
 * @date 2019/12/26 10:29
 * @since 1.0.0
 */
@Controller
public class BeanController {

    @Bean("/controllerAdaptorController")
    public ControllerAdaptorController initControllerAdaptorController() {
        return new ControllerAdaptorController();
    }

    @Bean("/httpRequestController")
    public HttpRequestController initHttpRequestController() {
        return new HttpRequestController();
    }

    @Bean("simpleServletHandlerAdapter")
    public SimpleServletHandlerAdapter initSimpleServletHandlerAdapter() {
        return new SimpleServletHandlerAdapter();
    }

}
