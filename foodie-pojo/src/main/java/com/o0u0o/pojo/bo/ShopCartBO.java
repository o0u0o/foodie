package com.o0u0o.pojo.bo;

/**
 * @author mac
 * @date 2020/11/26 5:23 下午
 */
public class ShopCartBO {

    private String itemId;
    private String itemImgUrl;
    private String itemName;
    private String specId;
    private String specName;
    private String buyCounts;
    private String priceDiscount;
    private String priceNormal;

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemImgUrl() {
        return itemImgUrl;
    }

    public void setItemImgUrl(String itemImgUrl) {
        this.itemImgUrl = itemImgUrl;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getSpecId() {
        return specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getBuyCounts() {
        return buyCounts;
    }

    public void setBuyCounts(String buyCounts) {
        this.buyCounts = buyCounts;
    }

    public String getPriceDiscount() {
        return priceDiscount;
    }

    public void setPriceDiscount(String priceDiscount) {
        this.priceDiscount = priceDiscount;
    }

    public String getPriceNormal() {
        return priceNormal;
    }

    public void setPriceNormal(String priceNormal) {
        this.priceNormal = priceNormal;
    }

    @Override
    public String toString() {
        return "ShopCartBO{" +
                "itemId='" + itemId + '\'' +
                ", itemImgUrl='" + itemImgUrl + '\'' +
                ", itemName='" + itemName + '\'' +
                ", specId='" + specId + '\'' +
                ", specName='" + specName + '\'' +
                ", buyCounts='" + buyCounts + '\'' +
                ", priceDiscount='" + priceDiscount + '\'' +
                ", priceNormal='" + priceNormal + '\'' +
                '}';
    }
}
