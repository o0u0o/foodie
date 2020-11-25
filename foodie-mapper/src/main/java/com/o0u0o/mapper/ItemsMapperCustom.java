package com.o0u0o.mapper;

import com.o0u0o.my.mapper.MyMapper;
import com.o0u0o.pojo.Items;
import com.o0u0o.pojo.vo.ItemCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

}