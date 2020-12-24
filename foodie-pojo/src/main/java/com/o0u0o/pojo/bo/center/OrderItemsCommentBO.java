package com.o0u0o.pojo.bo.center;

/**
 * 订单项评论BO
 * @author mac
 * @date 2020/12/24 4:56 下午
 */
public class OrderItemsCommentBO {
    private String commentId;

    private String itemId;

    private String itemName;

    private String itemSpecId;

    private String itemSpecName;

    private Integer commentLevel;

    private String content;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpecId() {
        return itemSpecId;
    }

    public void setItemSpecId(String itemSpecId) {
        this.itemSpecId = itemSpecId;
    }

    public String getItemSpecName() {
        return itemSpecName;
    }

    public void setItemSpecName(String itemSpecName) {
        this.itemSpecName = itemSpecName;
    }

    public Integer getCommentLevel() {
        return commentLevel;
    }

    public void setCommentLevel(Integer commentLevel) {
        this.commentLevel = commentLevel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "OrderItemsCommentBO{" +
                "commentId='" + commentId + '\'' +
                ", itemId='" + itemId + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemSpecId='" + itemSpecId + '\'' +
                ", itemSpecName='" + itemSpecName + '\'' +
                ", commentLevel=" + commentLevel +
                ", content='" + content + '\'' +
                '}';
    }
}
