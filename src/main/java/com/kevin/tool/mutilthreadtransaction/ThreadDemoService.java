package com.kevin.tool.mutilthreadtransaction;

import com.kevin.tool.async.SimpleThreadPool;
import com.kevin.tool.mutilthreadtransaction.transaction.TransactionThreadPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

@Service
public class ThreadDemoService {

    @Autowired
    private TransactionThreadPool transactionThreadPool;

    @Autowired
    private AsyncOneRepository asyncOneRepository;

    @Transactional
    public String saveInThread() {
        List<Callable<Object>> tasks = new ArrayList<>(1);

        AsyncOne one = new AsyncOne();
        long now = System.currentTimeMillis();
        one.setCreateTime(new Timestamp(now));
        one.setUpdateTime(new Timestamp(now));

        tasks.add(() -> asyncOneRepository.save(one));

        transactionThreadPool.invokeTransactionAll(SimpleThreadPool.ThreadPoolEnum.CREATE_ORDER, tasks);
        return "ok";
    }
}
