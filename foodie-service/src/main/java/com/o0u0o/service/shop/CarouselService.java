package com.o0u0o.service.shop;

import com.o0u0o.pojo.Carousel;

import java.util.List;

/**
 * 轮播服务接口
 * @author o0u0o
 * @date 2020/10/25 12:25 下午
 */
public interface CarouselService {

    /**
     * 查询所有轮播图列表
     * @param isShow 是否展示
     * @return
     */
    public List<Carousel> queryAll(Integer isShow);
}
