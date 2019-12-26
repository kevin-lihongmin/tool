package com.kevin.tool.spring.mvc.adaptor;

import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 *  适配器类型为: {@link SimpleServletHandlerAdapter}
 * @author lihongmin
 * @date 2019/12/26 9:48
 * @since 1.0.0
 */
public class HttpServletController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().write("HttpServletController -> HttpServlet!");
    }
}
