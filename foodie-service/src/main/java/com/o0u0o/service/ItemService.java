package com.o0u0o.service;

import com.o0u0o.pojo.Items;
import com.o0u0o.pojo.ItemsImg;
import com.o0u0o.pojo.ItemsParam;
import com.o0u0o.pojo.ItemsSpec;
import com.o0u0o.pojo.vo.CommentLevelCountsVO;
import com.o0u0o.pojo.vo.ShopCartVO;
import com.o0u0o.utils.PagedGridResult;

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

    /**
     * 根据商品ID和评价等级查询该商品的评价(分页)
     * @param itemId
     * @param level
     * @return
     */
    public PagedGridResult queryPagedComments(String itemId, Integer level, Integer pageNum, Integer pageSize);

    /**
     * 搜索商品列表
     * @param keywords 搜索关键字
     * @param sort 排序规则
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(String keywords, String sort, Integer pageNum, Integer pageSize);

    /**
     * 根据分类ID搜索商品列表
     * @param catId 分类ID
     * @param sort
     * @param pageNum
     * @param pageSize
     * @return
     */
    public PagedGridResult searchItems(Integer catId, String sort, Integer pageNum, Integer pageSize);

    /**
     * 根据规格ids查询最新的购物车中商品数据（用于刷新渲染购物车中的商品数据）
     * @param specIds
     * @return
     */
    public List<ShopCartVO> queryItemsBySpecIds(String specIds);
}
