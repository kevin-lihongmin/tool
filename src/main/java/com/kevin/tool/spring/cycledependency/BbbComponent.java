package com.kevin.tool.spring.cycledependency;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BbbComponent {

    /*AaaComponent aaaComponent;

    public BbbComponent(AaaComponent aaaComponent) {
        this.aaaComponent = aaaComponent;
    }*/
}
