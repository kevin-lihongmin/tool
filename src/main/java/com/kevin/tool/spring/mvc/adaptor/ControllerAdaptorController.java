package com.kevin.tool.spring.mvc.adaptor;


import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  适配器为: {@link SimpleControllerHandlerAdapter} 但是不能与{@link ResponseBody}一起使用（不生效）
 *
 * @author lihongmin
 * @date 2019/12/26 9:40
 * @since 1.0.0
 */
public class ControllerAdaptorController implements Controller {

    @Override
    @ResponseBody
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        ModelAndView modelAndView = new ModelAndView("test");
        modelAndView.addObject("key", "ControllerAdaptorController -> Controller！");
        return modelAndView;
    }
}
