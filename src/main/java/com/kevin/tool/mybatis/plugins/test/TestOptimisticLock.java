package com.kevin.tool.mybatis.plugins.test;

import com.kevin.tool.mybatis.plugins.test.dao.UserMapper;
import com.kevin.tool.mybatis.plugins.test.entity.User;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *  测试乐观锁  直接跳过Service层
 * @author kevin
 * @date 2020/9/11 23:26
 * @since 1.0.0
 */
@Slf4j
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

    @ResponseBody
    @GetMapping("updateUserWithUpdated")
    public String updateUserWithUpdated() {
        // 先获取
        User user = userMapper.selectByPrimaryKey(1);
        user.setName("kevin" + System.currentTimeMillis());

        // 后获取先更新
        User user2 = userMapper.selectByPrimaryKey(1);
        user2.setName("kevin2-" + System.currentTimeMillis());
        userMapper.updateByPrimaryKey(user2);

        // 后更新，先获取的
        try {
            userMapper.updateByPrimaryKey(user);
            User userNew = userMapper.selectByPrimaryKey(1);
            return "当前的乐观锁版本号为：" + userNew.getVersion();
        } catch (Exception e) {
            log.error("别人更新了，需要提示用户");
            return "您当前操作的数据有人更改过，请刷新页面后重新更改：";
        }
    }
}
