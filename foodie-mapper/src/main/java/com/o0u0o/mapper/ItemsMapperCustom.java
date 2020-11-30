package com.o0u0o.mapper;

import com.o0u0o.pojo.vo.ItemCommentVO;
import com.o0u0o.pojo.vo.SearchItemsVO;
import com.o0u0o.pojo.vo.ShopCartVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom {

    public List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String, Object> map);

    public List<SearchItemsVO> searchItemsByThirdCat(@Param("paramsMap") Map<String, Object> map);

    public List<ShopCartVO> queryItemsBySpecIds(@Param("paramsList") List specIdsList);

    /**
     * 扣除库存
     * @param specId 规格ID
     * @param pendingCounts 将被扣除的数量
     * @return
     */
    public int decreaseItemSpecStock(@Param("specId") String specId,
                                     @Param("pendingCounts") int pendingCounts);

}