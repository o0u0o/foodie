package com.o0u0o.controller.shop;

import com.o0u0o.enums.YesOrNo;
import com.o0u0o.pojo.Carousel;
import com.o0u0o.pojo.Category;
import com.o0u0o.pojo.vo.CategoryVO;
import com.o0u0o.pojo.vo.NewItemsVO;
import com.o0u0o.service.shop.CarouselService;
import com.o0u0o.service.shop.CategoryService;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.JsonUtils;
import com.o0u0o.utils.RedisOperator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private CarouselService carouselService;

    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图列表", notes = "获取首页轮播图列表", httpMethod = "GET")
    @GetMapping("/carousel")
    public IJsonResult carousel(){
        List<Carousel> list = carouselService.queryAll(YesOrNo.YES.type);
        return IJsonResult.ok(list);
    }

    /**
     * 首页分类展示需求
     * 1.第一次刷新主页查询大分类，渲染展示到首页
     * 2.如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类(一级分类)", notes = "获取商品分类(一级分类)", httpMethod = "GET")
    @GetMapping("/cats")
    public IJsonResult cats(){
        List<Category> list = categoryService.queryAllRootLevelCat();
        return IJsonResult.ok(list);
    }

    @ApiOperation(value = "获取商品子分类", notes = "获取商品子分类", httpMethod = "GET")
    @GetMapping("/subCat/{rootCatId}")
    public IJsonResult subCat(
            @ApiParam(name = "rootCatId", value = "一级分类ID", required = true)
            @PathVariable Integer rootCatId){
        if (rootCatId == null){
            return IJsonResult.errorMsg("分类不存在");
        }
        List<CategoryVO> list = new ArrayList<>();
        String catsStr = redisOperator.get("subCat:" + rootCatId);
        if (StringUtils.isBlank(catsStr)){
            list = categoryService.getSubCatList(rootCatId);
            /**
             * 缓存穿透：
             * 查询的key 在redis中不存在，对应的id在数据库也不存在，
             * 此时被非法用户进行攻击，大量的请求会直接打在数据库上，
             * 造成宕机，从而影响整个系统，这种现象被称为缓存穿透，
             *
             * 解决方法1：
             * 把空的数据也缓存起来，比如空字符串、空对象、空数组或者list
             *
             * 解决方案2：
             * 布隆过滤器：代码复杂度会更大，不建议使用
             */
            if (list != null && list.size() > 0){
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list));
            }else {
                //5分钟的空缓存
                redisOperator.set("subCat:" + rootCatId, JsonUtils.objectToJson(list), 5*60);
            }

        }else {
           list = JsonUtils.jsonToList(catsStr, CategoryVO.class);
        }

        return IJsonResult.ok(list);
    }

    @ApiOperation(value = "查询每个一级分类下的最新6条商品数据", notes = "查询每个一级分类下的最新6条商品数据", httpMethod = "GET")
    @GetMapping("/sixNewItems/{rootCatId}")
    public IJsonResult sixNewItems(
            @ApiParam(name = "rootCatId", value = "一级分类ID", required = true)
            @PathVariable Integer rootCatId){
        if (rootCatId == null){
            return IJsonResult.errorMsg("分类不存在");
        }
        List<NewItemsVO> list = categoryService.getSixNewItemsLazy(rootCatId);
        return IJsonResult.ok(list);
    }


}
