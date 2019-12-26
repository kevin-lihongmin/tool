package com.kevin.tool.spring.mvc.adaptor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;

/**
 *  适配器类型为: {@link RequestMappingHandlerAdapter}, 处理注解{@link RequestMapping} 类型的方法
 * @author lihongmin
 * @date 2019/12/26 9:50
 * @since 1.0.0
 */
@Controller
public class AnnotationController {

    @ResponseBody
    @RequestMapping("annotationController")
    public String annotationController() {
        return "AnnotationController -> @Controller!";
    }
}
