package com.kevin.tool.spring.cycledependency;

import com.kevin.tool.mutilthreadtransaction.AsyncTwoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AaaComponent {

    /*BbbComponent bbbComponent;

    public AaaComponent(BbbComponent bbbComponent) {
        this.bbbComponent = bbbComponent;
    }*/
}
