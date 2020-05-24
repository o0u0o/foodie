package com.o0u0o.my.mapper;

/**
 * @Author aiuiot
 * @Date 2020/5/24 5:00 下午
 * @Descripton:
 **/

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;


/**
 * 继承自己的MyMapper
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
