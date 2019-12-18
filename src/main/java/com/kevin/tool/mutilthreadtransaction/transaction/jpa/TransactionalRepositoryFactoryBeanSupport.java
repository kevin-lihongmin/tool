package com.kevin.tool.mutilthreadtransaction.transaction.jpa;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.core.support.PersistenceExceptionTranslationRepositoryProxyPostProcessor;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.data.repository.core.support.RepositoryProxyPostProcessor;
import org.springframework.data.repository.util.TxUtils;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

/**
 *	修改{@link #setBeanFactory} 方法初始化的{@link #txPostProcessor}
 *	为{@link ThreadTransactionalRepositoryProxyPostProcessor}类型
 *
 * @author lihongmin
 * @date 2019/12/18 17:01
 * @since 1.0.0
 * @see JpaRepositoryFactoryBean#afterPropertiesSet()
 */
public abstract class TransactionalRepositoryFactoryBeanSupport<T extends Repository<S, ID>, S, ID>
		extends RepositoryFactoryBeanSupport<T, S, ID> implements BeanFactoryAware {

	private String transactionManagerName = TxUtils.DEFAULT_TRANSACTION_MANAGER;
	private @Nullable
	RepositoryProxyPostProcessor txPostProcessor;
	private @Nullable RepositoryProxyPostProcessor exceptionPostProcessor;
	private boolean enableDefaultTransactions = true;

	/**
	 * Creates a new {@link TransactionalRepositoryFactoryBeanSupport} for the given repository interface.
	 *
	 * @param repositoryInterface must not be {@literal null}.
	 */
	protected TransactionalRepositoryFactoryBeanSupport(Class<? extends T> repositoryInterface) {
		super(repositoryInterface);
	}

	/**
	 * Setter to configure which transaction manager to be used. We have to use the bean name explicitly as otherwise the
	 * qualifier of the {@link org.springframework.transaction.annotation.Transactional} annotation is used. By explicitly
	 * defining the transaction manager bean name we favour let this one be the default one chosen.
	 *
	 * @param transactionManager
	 */
	public void setTransactionManager(String transactionManager) {
		this.transactionManagerName = transactionManager == null ? TxUtils.DEFAULT_TRANSACTION_MANAGER : transactionManager;
	}

	public void setEnableDefaultTransactions(boolean enableDefaultTransactions) {
		this.enableDefaultTransactions = enableDefaultTransactions;
	}

	@Override
	protected final RepositoryFactorySupport createRepositoryFactory() {

		RepositoryFactorySupport factory = doCreateRepositoryFactory();

		RepositoryProxyPostProcessor exceptionPostProcessor = this.exceptionPostProcessor;

		if (exceptionPostProcessor != null) {
			factory.addRepositoryProxyPostProcessor(exceptionPostProcessor);
		}

		RepositoryProxyPostProcessor txPostProcessor = this.txPostProcessor;

		if (txPostProcessor != null) {
			factory.addRepositoryProxyPostProcessor(txPostProcessor);
		}

		return factory;
	}

	/**
	 * Creates the actual {@link RepositoryFactorySupport} instance.
	 *
	 * @return
	 */
	protected abstract RepositoryFactorySupport doCreateRepositoryFactory();

	/**
	 * (non-Javadoc)
	 * @see BeanFactoryAware#setBeanFactory(BeanFactory)
	 */
	@Override
	public void setBeanFactory(BeanFactory beanFactory) {

		Assert.isInstanceOf(ListableBeanFactory.class, beanFactory);

		super.setBeanFactory(beanFactory);

		ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
		this.txPostProcessor = new ThreadTransactionalRepositoryProxyPostProcessor(listableBeanFactory, transactionManagerName,
				enableDefaultTransactions);
		this.exceptionPostProcessor = new PersistenceExceptionTranslationRepositoryProxyPostProcessor(listableBeanFactory);
	}
}
