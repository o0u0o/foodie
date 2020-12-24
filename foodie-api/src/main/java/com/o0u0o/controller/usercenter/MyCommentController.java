package com.o0u0o.controller.usercenter;

import com.o0u0o.controller.shop.BaseController;
import com.o0u0o.enums.YesOrNo;
import com.o0u0o.pojo.OrderItems;
import com.o0u0o.pojo.Orders;
import com.o0u0o.pojo.bo.center.OrderItemsCommentBO;
import com.o0u0o.service.usercenter.MyCommentsService;
import com.o0u0o.service.usercenter.MyOrdersService;
import com.o0u0o.utils.IJsonResult;
import com.o0u0o.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户中心接口
 * @author mac
 * @date 2020/12/3 2:59 下午
 */
@Api(value = "用户中心 - 评价模块", tags = {"用户中心评价模块相关接口"})
@RestController
@RequestMapping("/mycomments")
public class MyCommentController extends BaseController {

    @Autowired
    private MyCommentsService myCommentsService;

    @ApiOperation(value = "查询订单列表", notes = "查询订单列表", httpMethod = "POST")
    @PostMapping("/pending")
    public IJsonResult pending(
            @ApiParam(name = "userId", value = "用户ID", required = true)
            @RequestParam String userId,
            @ApiParam(name = "orderId", value = "订单ID", required = true)
            @RequestParam String orderId){

        // 判断用户和订单是否关联
        IJsonResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }
        // 判断该笔订单是否评价过，评价过，就不再继续
        Orders myOrder = (Orders)checkResult.getData();
        if (YesOrNo.YES.type.equals(myOrder.getIsComment())){
            return IJsonResult.errorMsg("该笔订单已经评价");
        }

        List<OrderItems> list = myCommentsService.queryPendingComment(orderId);

        return IJsonResult.ok(list);
    }

    @ApiOperation(value = "保存评论列表", notes = "保存评论列表", httpMethod = "POST")
    @PostMapping("/saveList")
    public IJsonResult saveList(@ApiParam(name = "userId", value = "用户ID", required = true)
                                @RequestParam String userId,
                                @ApiParam(name = "orderId", value = "订单ID", required = true)
                                @RequestParam String orderId,
                                @RequestBody List<OrderItemsCommentBO> commentList){
        // 判断用户和订单是否关联
        IJsonResult checkResult = checkUserOrder(userId, orderId);
        if (checkResult.getStatus() != HttpStatus.OK.value()) {
            return checkResult;
        }

        //判断评论内容list不能为空
        if (commentList == null || commentList.isEmpty() || commentList.size() == 0){
            return IJsonResult.errorMsg("评论内容不能为空!");
        }

        myCommentsService.saveComments(orderId, userId, commentList);

        return IJsonResult.ok();
    }

}
