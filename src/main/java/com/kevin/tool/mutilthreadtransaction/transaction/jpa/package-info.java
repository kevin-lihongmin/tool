/**
 *  子线程感知父线程事务，但是当使用Jpa {@link org.springframework.data.jpa.repository.JpaRepository}
 *  的子类为{@link org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean#getObject()}
 *  的代理对象，需要该包的支持
 *
 * @author lihongmin
 * @date 2019/12/18 16:55
 * @since 1.0.0
 */
package com.kevin.tool.mutilthreadtransaction.transaction.jpa;