package com.kevin.tool.mutilthreadtransaction;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liujiazhong
 * @date 2019/11/26 17:27
 */
public interface AsyncTwoRepository extends JpaRepository<AsyncTwo, Long> {
}
