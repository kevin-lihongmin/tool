package com.kevin.tool.order.sendpay;

import com.kevin.tool.KevinToolApplication;
import lombok.NoArgsConstructor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@NoArgsConstructor
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KevinToolApplication.class)
public class CodeApplicationContextTest {

    /*@Autowired
    private CodeApplicationContextImpl context;

    @Test
    public void annotationEventTest() {
        String s = context.generateCode(new CodeParam());
        System.out.println(s);
    }*/

}
