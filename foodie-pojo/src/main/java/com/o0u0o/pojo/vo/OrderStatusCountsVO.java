package com.o0u0o.pojo.vo;

import jdk.nashorn.internal.objects.annotations.Getter;
import jdk.nashorn.internal.objects.annotations.Setter;

/**
 * 订单状态概览数量VO
 * @author mac
 * @date 2021/1/11 1:55 下午
 */
public class OrderStatusCountsVO {

    private Integer waitPayCounts;

    private Integer waitDeliverCounts;

    private Integer waitReceiveCount;

    private Integer waitCommentCount;

    public OrderStatusCountsVO() {
    }

    public OrderStatusCountsVO(Integer waitPayCounts, Integer waitDeliverCounts, Integer waitReceiveCount, Integer waitCommentCount) {
        this.waitPayCounts = waitPayCounts;
        this.waitDeliverCounts = waitDeliverCounts;
        this.waitReceiveCount = waitReceiveCount;
        this.waitCommentCount = waitCommentCount;
    }

    public Integer getWaitPayCounts() {
        return waitPayCounts;
    }

    public void setWaitPayCounts(Integer waitPayCounts) {
        this.waitPayCounts = waitPayCounts;
    }

    public Integer getWaitDeliverCounts() {
        return waitDeliverCounts;
    }

    public void setWaitDeliverCounts(Integer waitDeliverCounts) {
        this.waitDeliverCounts = waitDeliverCounts;
    }

    public Integer getWaitReceiveCount() {
        return waitReceiveCount;
    }

    public void setWaitReceiveCount(Integer waitReceiveCount) {
        this.waitReceiveCount = waitReceiveCount;
    }

    public Integer getWaitCommentCount() {
        return waitCommentCount;
    }

    public void setWaitCommentCount(Integer waitCommentCount) {
        this.waitCommentCount = waitCommentCount;
    }
}
