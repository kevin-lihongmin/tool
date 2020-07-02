package com.kevin.tool.order.code.check;

import com.kevin.tool.order.code.check.CheckService;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 *  模拟用户任务
 * @author lihongmin
 * @date 2020/6/30 16:31
 * @since 1.0.0
 */
@Service
public class UserCheckService implements CheckService {

    @Override
    public Boolean isCheck() {
        return new Random().nextBoolean();
    }
}
