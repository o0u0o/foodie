package com.o0u0o.controller;

import com.o0u0o.pojo.Items;
import com.o0u0o.pojo.ItemsImg;
import com.o0u0o.pojo.ItemsParam;
import com.o0u0o.pojo.ItemsSpec;
import com.o0u0o.pojo.vo.CommentLevelCountsVO;
import com.o0u0o.pojo.vo.ItemInfoVO;
import com.o0u0o.service.ItemService;
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

}
