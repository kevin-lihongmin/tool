/**
 *  父子线程事务支持，当父线程开启事务后，子线程默认不会进行开启；
 *  当子线程抛出异常时，根据事务边界能进行回滚
 *
 * @author lihongmin
 * @date 2019/12/18 16:53
 * @since 1.0.0
 */
package com.kevin.tool.mutilthreadtransaction.transaction;