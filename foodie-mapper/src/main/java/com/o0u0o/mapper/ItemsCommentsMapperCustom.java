package com.o0u0o.mapper;

import com.o0u0o.my.mapper.MyMapper;
import com.o0u0o.pojo.ItemsComments;

import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public void saveComments(Map<String, Object> map);
}