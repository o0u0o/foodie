package com.o0u0o.controller;

import com.o0u0o.enums.YesOrNo;
import com.o0u0o.pojo.Carousel;
import com.o0u0o.service.CarouselService;
import com.o0u0o.utils.IJsonResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author mac
 * @date 2020/11/17 2:26 下午
 */
@Api(value = "首页", tags = {"首页展示的相关接口"})
@RestController
@RequestMapping("index")
public class IndexController {

    @Autowired
    private CarouselService carouselService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public IJsonResult carousel(){
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return IJsonResult.ok(list);
    }

}