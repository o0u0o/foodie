package com.o0u0o.pojo.vo;/**

 /**
 * 用于展示商品搜索列表结果的VO
 * @author mac
 * @date 2020/11/26 11:27 上午
 */
public class SearchItemsVO {

    /** 商品ID */
    private String itemId;

    /** 商品名 */
    private String itemName;

    /** 销售量 */
    private int sellCounts;

    /** 商品主图 */
    private String imgUrl;

    /** 销售价格 */
    private int price;

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

    public int getSellCounts() {
        return sellCounts;
    }

    public void setSellCounts(int sellCounts) {
        this.sellCounts = sellCounts;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
