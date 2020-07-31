package cn.stylefeng.guns.yinhua.mobile.controller.DispatchOrder;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderNumLog;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.Teams;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
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
 * 分派订单(打样)
 */
@RestController
@RequestMapping("/mobile/DispatchOrder")
public class DispatchOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;
    private static String px = "1234730554360872962";
    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @Permission
    @ApiOperation(value="获取订单列表",notes="token,20 未分组 21 已分组", httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status){
        Page<Order> orderList =  selectService.getDispatchOrderData(page, pageSize, px,status);
        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
    }
    /**
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(6,px);
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
     * 获取打样工
     * @return
     *//*
    @RequestMapping("/getTeam")
    @ApiOperation(value="获取打样工",notes="token", httpMethod = "GET")
    public JSONResult getTeam(String token){
        List<UserTeam> team = orderService.getPattenUser();
        return new JSONResult("0","ok",team);
    }*/
    /**
     * 选择打样工
     * @return
     */
    @RequestMapping("/changeTeam")
    @ApiOperation(value="选小组",notes="token，小组id", httpMethod = "GET")
    public JSONResult changeTeam(String token, OrderTeam orderTeam){
        orderService.changeOrderPatten(px,orderTeam);
        if(orderTeam.getTeamId()!=null){
            orderService.sendMessageForMsg(px,orderTeam.getOrderNum());
        }
        return new JSONResult("0","ok");
    }
    /**
     * 获取出货时间
     * @return
     */
    @RequestMapping("/getOutTime")
    @ApiOperation(value="获取出货时间",notes="token，生产单编号", httpMethod = "GET")
    public JSONResult getOutTime(String token, String order_num){
        List<OrderNumLog> orderNumLogs = orderService.getOutForOrder(order_num);
        return new JSONResult("0","ok",orderNumLogs);
    }
}
