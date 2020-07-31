package cn.stylefeng.guns.yinhua.mobile.controller.plateMaking;


import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderPropNote;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestType;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.service.SelectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 制版工作
 */
@RestController
@RequestMapping("/mobile/plateMaking")
public class PlateMakingController {


    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;
    //工作统计
    private static String px= "1234729858706194434";
    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @ApiOperation(value="获取订单列表",notes="token,传入11代表催单列表", httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status){
        Page<Order> orderList = selectService.getPlateMakingData(page, pageSize, px,status);
        if(orderList == null){
            return new PageResult(page,pageSize,0L,0,null);
        }
        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
    }
    /**
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(2,px);
        return new JSONResult("0","OK",dataCount);
    }
    /**
     * 获取订单详情信息
     * @return
     */
    @RequestMapping("/getOrderInfo")
    @Permission
    @ApiOperation(value="生产单编号",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrderInfo(String token, String order_num){
        RestAllOrder restAllOrder = orderService.getOrderInfo(1,order_num,false);
        return new JSONResult("0","OK",restAllOrder);
    }
    /**
     *
     * 更新进度
     * @return
     */
    @RequestMapping("/updateOrderProp")
    @ApiOperation(value="更新订单进度",notes="token，生产单编号", httpMethod = "GET")
    public JSONResult updateOrderProp(String token, OrderPropNote orderPropNote){
        /* 检查是否有未填写位置的订单*/
        if(orderPropNote.getFlagDo() == 1){
            orderService.checkAllModelInfoForPlace(orderPropNote.getOrderNum());
        }
        orderService.updateOrderProp(px,0,orderPropNote);
        orderService.updateOrderUser(orderPropNote,2);
        if(orderPropNote.getFlagDo() == 1){
            orderService.sendMessageForMsg(px,orderPropNote.getOrderNum());
        }
        return new JSONResult("0","ok");
    }
    /**
     *
     * 工作统计
     * @return
     */
    @RequestMapping("/getCount")
    @ApiOperation(value="工作统计",notes="token，月份", httpMethod = "GET")
    public JSONResult getCount(String token, Integer mouth){
        List<RestType> restTypeList = orderService.getCount(mouth,"(2)");
        return new JSONResult("0","ok",restTypeList);
    }

}
