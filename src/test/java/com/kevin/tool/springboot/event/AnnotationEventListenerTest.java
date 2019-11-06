package com.kevin.tool.springboot.event;

import com.kevin.tool.KevinToolApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @EventListener 测试
 *
 * @author kevin
 * @date 2019/11/6 13:30
 * @since 1.0.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = KevinToolApplication.class )
public class AnnotationEventListenerTest {

    @Autowired
    private UserService userService;

    @Test
    public void annotationEventTest() {
        userService.addUser(new User());
    }
}
