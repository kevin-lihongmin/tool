package com.kevin.tool.order.code.purchase;

import com.kevin.tool.order.code.CodeApplicationContextImpl;
import com.kevin.tool.order.code.SaleCreateDTO;
import com.kevin.tool.order.code.SegmentState;
import com.kevin.tool.order.code.generate.param.PurchaseCodeParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *  采购订单货源安排
 *
 * @author lihongmin
 * @date 2020/8/1 10:08
 * @since 1.0.0
 */
@Service
public class PurchaseOrderService {

    private CodeApplicationContextImpl context;

    @Autowired
    public PurchaseOrderService(CodeApplicationContextImpl context) {
        this.context = context;
    }

    /**
     *  货源安排
     */
    public void turnOrder() {
        // 销售开单检查服务
        Boolean check = context.check(new PurchaseCodeParam(), SegmentState.SALE_CREATE);
        if (!check) {
            // 不通过则设置状态
            saveState();
            return;
        }

        // 是否紧缺检查
        Boolean saleShortage = context.isSaleShortage("");
        if (!saleShortage) {
            // 不检查直接走创建so路径
            turnSo();
            return;
        }

        // 是否在紧缺中
        Boolean skuShortage = isSkuShortage();
        if (skuShortage) {
            // 紧缺中，走vso路径
            turnVso();
        } else {
            turnSo();
        }
    }

    private void turnSo() {
        // 是否转so控制
        Boolean soControl = context.isSoControl("");
        if (!soControl) {
            // 不可以转so，设置状态，结束
            saveState();
            return;
        }
        // 是否检查Atp
        Boolean atp = context.isAtp("");
        if (!atp) {
            // 不检查atp，设置状态，结束
            saveState();
            return;
        }

        // 检查是否在黑名单中
        Boolean blacklist = isBlacklist();
        if (blacklist) {
            // 在黑名单中，可以转向走vso分支
            turnVso();
            return;
        }

        // 检查atp量（atp - vso - 冻结量）
        Boolean atpPass = isAtpPass();
        if (!atpPass) {
            // 没有库存， 保存状态，结束
            saveState();
            return;
        }
        // 创建销售订单
        createSo();
    }

    private void turnVso() {
        // 是否Vso控制
        Boolean vsoControl = context.isVsoControl("");
        if (!vsoControl) {
            // 不可以转Vso，设置状态，结束
            saveState();
            return;
        }

        // 是否检查预售计划量
        Boolean checkPreSell = context.isCheckPreSell("");
        if (!checkPreSell) {
            // 不检查预售量，直接开预售单
            createVso();
            return;
        }
        // 检查预销售量
        Boolean preSell = preSell();
        if (!preSell) {
            // 检查预销售量不通过，保存状态，结束
            saveState();
        }
        // 有预售量，创建Vso
        createVso();
    }

    private Boolean createSo() {
        return true;
    }

    private Boolean createVso() {
        return true;
    }

    private void saveState() {

    }

    private Boolean preSell() {
        return false;
    }

    private Boolean isBlacklist() {
        return false;
    }

    private Boolean isAtpPass() {
        return false;
    }

    /**
     *  是否sku紧缺
     * @return 是否紧缺
     */
    private Boolean isSkuShortage() {
        return true;
    }
}
