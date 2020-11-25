package com.o0u0o.pojo.vo;

/**
 * 用户展示商品评价数的VO
 * @author mac
 * @date 2020/11/25 4:16 下午
 */
public class CommentLevelCountsVO {

    /** 总评论 */
    public Integer totalCounts;

    /** 好评 */
    public Integer goodCounts;

    /** 中评 */
    public Integer normalCounts;

    /** 差评 */
    public Integer badCounts;

    public Integer getTotalCounts() {
        return totalCounts;
    }

    public void setTotalCounts(Integer totalCounts) {
        this.totalCounts = totalCounts;
    }

    public Integer getGoodCounts() {
        return goodCounts;
    }

    public void setGoodCounts(Integer goodCounts) {
        this.goodCounts = goodCounts;
    }

    public Integer getNormalCounts() {
        return normalCounts;
    }

    public void setNormalCounts(Integer normalCounts) {
        this.normalCounts = normalCounts;
    }

    public Integer getBadCounts() {
        return badCounts;
    }

    public void setBadCounts(Integer badCounts) {
        this.badCounts = badCounts;
    }
}
