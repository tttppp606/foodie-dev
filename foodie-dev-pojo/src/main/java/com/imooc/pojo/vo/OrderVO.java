package com.imooc.pojo.vo;

import com.imooc.pojo.bo.ShopcartBO;
import lombok.Data;

import java.util.List;
@Data
public class OrderVO {

    private String orderId;
    //mynote 一个VO里可以包含另一个VO，嵌套在一起，尤其当子VO的业务含义与父VO不一样，且自成体系
    private MerchantOrdersVO merchantOrdersVO;
    private List<ShopcartBO> toBeRemovedShopcatdList;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public MerchantOrdersVO getMerchantOrdersVO() {
        return merchantOrdersVO;
    }

    public void setMerchantOrdersVO(MerchantOrdersVO merchantOrdersVO) {
        this.merchantOrdersVO = merchantOrdersVO;
    }
}