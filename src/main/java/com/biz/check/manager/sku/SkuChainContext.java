package com.biz.check.manager.sku;

import com.biz.check.manager.component.AbstractCheckService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Role;
import org.springframework.lang.NonNull;

/**
 *  sku状态检查责任链容器，和初始化
 *
 * @author kevin
 * @date 2020/8/26 10:10
 * @since 1.0.0
 */
public abstract class SkuChainContext extends AbstractCheckService implements BeanFactoryAware, InitializingBean {

    /**
     * Spring Bean 工厂
     */
    private BeanFactory beanFactory;

    /**
     * 检查项
     */
    protected CheckSkuInfo checkChain;

    @Override
    public void setBeanFactory(@NonNull BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        checkChain = (SinglePackageCheckSkuInfo) beanFactory.getBean("SinglePackageCheckSkuInfo");
    }

    /**
     * 注册sku组合检查 为Spring Bean
     * @return sku组合
     */
    @Bean(name = "SkuCombineCheckSkuInfo")
    @Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
    public SkuCombineCheckSkuInfo initSkuCombine() {
        return new SkuCombineCheckSkuInfo();
    }

    /**
     *  注册sku检查 为Spring Bean
     * @param skuCombineCheckSkuInfo sku组合检查的 Bean
     * @return sku
     */
    @Bean(name = "SkuCheckSkuInfo")
    @Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
    public SkuCheckSkuInfo initSku(SkuCombineCheckSkuInfo skuCombineCheckSkuInfo) {
        return new SkuCheckSkuInfo(skuCombineCheckSkuInfo);
    }

    /**
     * 链条的第一个设置为单包件，因为检查最轻，不通过则返回
     * @param productAndBomFeignService 依赖注入 外部bom查询
     * @return 单包件状态检查
     */
    /*@Bean(name = "SinglePackageCheckSkuInfo")
    @Role(value = BeanDefinition.ROLE_INFRASTRUCTURE)
    public SinglePackageCheckSkuInfo initSinglePackage(ProductAndBomFeignService productAndBomFeignService) {
        // 由于 skuCheckSkuInfo和SkuCombineCheckSkuInfo是父子关系，不能通过autowired注入，所以直接设置
        CheckSkuInfo skuCheckSkuInfo = (SkuCheckSkuInfo)beanFactory.getBean("SkuCheckSkuInfo");
        return new SinglePackageCheckSkuInfo(skuCheckSkuInfo, productAndBomFeignService);
    }*/

}
