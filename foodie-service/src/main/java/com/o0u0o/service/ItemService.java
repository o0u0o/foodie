package com.o0u0o.service;

import com.o0u0o.pojo.Items;
import com.o0u0o.pojo.ItemsImg;
import com.o0u0o.pojo.ItemsParam;
import com.o0u0o.pojo.ItemsSpec;
import com.o0u0o.pojo.vo.CommentLevelCountsVO;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品ID查询详情
     * @param itemId 商品ID
     * @return
     */
    public Items queryItemById(String itemId);

    /**
     * 根据商品ID查询商品图片列表
     * @param itemId 商品ID
     * @return
     */
    public List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品ID查询商品规格
     * @param itemId 商品ID
     * @return
     */
    public List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品ID查询商品属性
     * @param itemId 商品ID
     * @return
     */
    public ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品ID查询商品评价等级数量
     * @param itemId
     */
    public CommentLevelCountsVO queryCommentCounts(String itemId);

}
