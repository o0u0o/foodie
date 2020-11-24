package com.o0u0o.pojo.vo;

/**
 * TODO
 *
 * @author mac
 * @date 2020/11/24 1:36 下午
 */
public class SubCategoryVO {

    /** 三级分类id */
    private Integer subId;

    /** 三级分类名 */
    private String subName;

    /** 三级分级类型*/
    private Integer subType;

    private Integer subFatherId;

    public Integer getSubId() {
        return subId;
    }

    public void setSubId(Integer subId) {
        this.subId = subId;
    }

    public String getSubName() {
        return subName;
    }

    public void setSubName(String subName) {
        this.subName = subName;
    }

    public Integer getSubType() {
        return subType;
    }

    public void setSubType(Integer subType) {
        this.subType = subType;
    }

    public Integer getSubFatherId() {
        return subFatherId;
    }

    public void setSubFatherId(Integer subFatherId) {
        this.subFatherId = subFatherId;
    }
}
