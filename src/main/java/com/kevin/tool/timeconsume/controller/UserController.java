package com.kevin.tool.timeconsume.controller;

import com.kevin.tool.timeconsume.TimeConsume;
import com.kevin.tool.timeconsume.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 *  方法性能监控
 *  1、测试url为： localhost:9999/getUser
 *  2、在需要监控的方法上添加 {@link TimeConsume} 注解，
 *     若 {@link TimeConsume#print()}不设置则进行跳过
 *  3、在启动类上添加 {@link com.kevin.tool.timeconsume.EnableTimeConsume}
 *  4、支持方法嵌套调用
 *
 * @author lihongmin
 * @date 2019/10/24 9:26
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    /**
     *  获取用户信息
     * @return 测试数据，不用看
     * @throws InterruptedException 线程中断异常
     */
    @GetMapping("getUser")
    @TimeConsume(taskName = "UserController.getUser", print = true)
    public String getUser() throws InterruptedException {
        int init = userService.getInit();
        int inr = userService.getInr(init);
        return "ok:" + inr;
    }

}
