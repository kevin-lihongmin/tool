package com.kevin.tool.mutilthreadtransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThreadDemoController {

    @Autowired
    private ThreadDemoService threadDemoService;

    @GetMapping("ThreadDemo")
    public String getUser() {
        return threadDemoService.saveInThread();
    }
}
