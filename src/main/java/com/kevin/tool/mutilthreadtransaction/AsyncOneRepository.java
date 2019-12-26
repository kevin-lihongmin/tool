package com.kevin.tool.mutilthreadtransaction;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author liujiazhong
 * @date 2019/11/26 17:26
 */
public interface AsyncOneRepository extends JpaRepository<AsyncOne, Long> {
}
