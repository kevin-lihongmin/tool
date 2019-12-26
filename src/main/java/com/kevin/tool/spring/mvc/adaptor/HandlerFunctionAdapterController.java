package com.kevin.tool.spring.mvc.adaptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;
import org.springframework.web.servlet.function.support.HandlerFunctionAdapter;

import static org.springframework.web.servlet.function.EntityResponse.fromObject;
import static org.springframework.web.servlet.function.ServerResponse.ok;

/**
 *  适配器类型为:{@link HandlerFunctionAdapter}
 *  reactive 相关，当前总是报错MediaType设置问题
 * @author lihongmin
 * @date 2019/12/26 10:21
 * @since 1.0.0
 */
@Configuration
public class HandlerFunctionAdapterController {
    @Bean
    public RouterFunction<ServerResponse> testRouterFunction() {
        return RouterFunctions.route(RequestPredicates.path("/handlerFunctionAdapter")
                        .and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                request -> ok().body(fromObject("HandlerFunctionAdapterController -> HandlerFunctionAdapter!")));
    }
}