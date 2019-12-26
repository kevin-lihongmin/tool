package com.kevin.tool.spring.mvc.adaptor;

import org.springframework.web.HttpRequestHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  适配器类型为: {@link HttpRequestHandlerAdapter}，不返回{@link ModelAndView} 或者 {@link View}
 * @author lihongmin
 * @date 2019/12/26 9:58
 * @since 1.0.0
 */
public class HttpRequestController implements HttpRequestHandler {

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().println("HttpRequestController -> HttpRequestHandler!");
    }
}
