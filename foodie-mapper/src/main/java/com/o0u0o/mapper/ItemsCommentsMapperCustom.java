package com.o0u0o.mapper;

import com.o0u0o.my.mapper.MyMapper;
import com.o0u0o.pojo.ItemsComments;
import com.o0u0o.pojo.vo.MyCommentVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsCommentsMapperCustom extends MyMapper<ItemsComments> {

    public void saveComments(Map<String, Object> map);

    public List<MyCommentVO> queryMyComments(@Param("paramsMap") Map<String, Object> map);
}