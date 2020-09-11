package com.kevin.tool.mybatis.plugins.test;

import com.kevin.tool.mybatis.plugins.test.dao.UserMapper;
import com.kevin.tool.mybatis.plugins.test.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  测试乐观锁  直接跳过Service层
 * @author kevin
 * @date 2020/9/11 23:26
 * @since 1.0.0
 */
@RestController("optimistic")
@AllArgsConstructor
public class TestOptimisticLock {

    private final UserMapper userMapper;

    @ResponseBody
    @GetMapping("testUpdate")
    public Integer updateUser() {
        User user = userMapper.selectByPrimaryKey(1);
        user.setName("kevin" + System.currentTimeMillis());

        userMapper.updateByPrimaryKey(user);

        User user1 = userMapper.selectByPrimaryKey(1);
        return user1.getVersion();
    }
}
