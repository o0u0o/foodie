package com.o0u0o.controller.shop;

import com.o0u0o.pojo.Items;
import com.o0u0o.pojo.ItemsImg;
import com.o0u0o.pojo.ItemsParam;
import com.o0u0o.pojo.ItemsSpec;
import com.o0u0o.pojo.vo.CommentLevelCountsVO;
import com.o0u0o.pojo.vo.ItemInfoVO;
import com.o0u0o.pojo.vo.ShopCartVO;
import com.o0u0o.service.shop.ItemService;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品接口
 * @author mac
 * @date 2020/11/17 3:38 下午
 */
@Api(value = "商品接口", tags = {"商品信息展示的相关接口"})
@RestController
@RequestMapping("items")
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情", notes = "查询商品详情", httpMethod = "GET")
    @GetMapping("/info/{itemId}")
    public IJsonResult info(
            @ApiParam(name = "itemId", value = "商品ID", required = true)
            @PathVariable String itemId){

        if (StringUtils.isBlank(itemId)){
            return IJsonResult.errorMsg(null);
        }

        Items item = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgList = itemService.queryItemImgList(itemId);
        List<ItemsSpec> itemsSpecList = itemService.queryItemSpecList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(item);
        itemInfoVO.setItemImgList(itemsImgList);
        itemInfoVO.setItemSpecList(itemsSpecList);
        itemInfoVO.setItemParams(itemsParam);

        return IJsonResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级", notes = "查询商品评价等级", httpMethod = "GET")
    @GetMapping("/commentLevel")
    public IJsonResult commentLevel(
            @ApiParam(name = "itemId", value = "商品ID", required = true)
            @RequestParam String itemId){

        if (StringUtils.isBlank(itemId)){
            return IJsonResult.errorMsg(null);
        }

        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return IJsonResult.ok(commentLevelCountsVO);
    }

    @ApiOperation(value = "查询商品评论", notes = "查询商品评论", httpMethod = "GET")
    @GetMapping("/comments")
    public IJsonResult comments(
            @ApiParam(name = "itemId", value = "商品ID", required = true)
            @RequestParam String itemId,
            @ApiParam(name = "level", value = "评价等级(1-好评 2-中评 3-差评)", required = false)
            @RequestParam Integer level,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize){

        if (StringUtils.isBlank(itemId)){
            return IJsonResult.errorMsg(null);
        }

        PagedGridResult gridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return IJsonResult.ok(gridResult);
    }

    @ApiOperation(value = "搜索商品列表", notes = "搜索商品列表", httpMethod = "GET")
    @GetMapping("/search")
    public IJsonResult search(
            @ApiParam(name = "keywords", value = "搜索关键字", required = true)
            @RequestParam String keywords,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize){

        if (StringUtils.isBlank(keywords)){
            return IJsonResult.errorMsg(null);
        }

        PagedGridResult gridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return IJsonResult.ok(gridResult);
    }

    @ApiOperation(value = "通过分类ID搜索商品列表", notes = "通过分类ID搜索商品列表", httpMethod = "GET")
    @GetMapping("/catItems")
    public IJsonResult catItems(
            @ApiParam(name = "catId", value = "三级分类ID", required = true)
            @RequestParam Integer catId,
            @ApiParam(name = "sort", value = "排序", required = false)
            @RequestParam String sort,
            @ApiParam(name = "page", value = "查询下一页的第几页", required = false)
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @ApiParam(name = "pageSize", value = "分页的每一页显示的条数", required = false)
            @RequestParam(name = "pageSize", defaultValue = "20") Integer pageSize){

        if (catId == null){
            return IJsonResult.errorMsg(null);
        }

        PagedGridResult gridResult = itemService.searchItems(catId, sort, page, pageSize);
        return IJsonResult.ok(gridResult);
    }

    @ApiOperation(value = "根据商品规格ids查找最新的商品数据",
            notes = "根据商品规格ids查找最新的商品数据(用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格）类似于京东淘宝")
    @GetMapping("/refresh")
    public IJsonResult refresh(@ApiParam(name = "itemSpecIds", value = "拼接的规格ids", required = true, example = "1001,1003,1005")
                               @RequestParam String itemSpecIds){
        if (StringUtils.isBlank(itemSpecIds)){
            return IJsonResult.ok();
        }
        List<ShopCartVO> shopCartVOList = itemService.queryItemsBySpecIds(itemSpecIds);
        return IJsonResult.ok(shopCartVOList);
    }
}
