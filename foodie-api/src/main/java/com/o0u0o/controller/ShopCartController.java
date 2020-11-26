package com.o0u0o.controller;

import com.o0u0o.pojo.bo.ShopCartBO;
import com.o0u0o.pojo.vo.ShopCartVO;
import com.o0u0o.service.ItemService;
import com.o0u0o.utils.IJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author mac
 * @date 2020/11/26 3:19 下午
 */
@Api(value = "购物车接口controller", tags = {"购物车接口相关的api"})
@RestController
@RequestMapping("shopcart")
public class ShopCartController {

    @Autowired
    private ItemService itemService;

    final static Logger logger  = LoggerFactory.getLogger("ShopCartController");

    @ApiOperation(value = "添加商品到购物车", notes = "添加商品到购物车")
    @PostMapping
    public IJsonResult add(@RequestParam String userId,
                           @RequestBody ShopCartBO shopCartBO,
                           HttpServletRequest request,
                           HttpServletResponse response){
        if (StringUtils.isBlank(userId)){
            return IJsonResult.errorMsg("");
        }

        logger.info("shopCartBO:{}", shopCartBO);
        //TODO 前端用户在登录的情况下，添加商品到购物车，会同时在后端同步购物车到redis缓存
        return IJsonResult.ok();
    }


}
