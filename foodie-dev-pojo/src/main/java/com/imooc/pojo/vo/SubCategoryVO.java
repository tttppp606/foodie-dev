package com.imooc.pojo.vo;

import lombok.Data;

/**
 * 子分类VO
 */
@Data
public class SubCategoryVO {

    private Integer subId;

    private String subName;

    private Integer subType;

    private Integer subFatherId;
}
