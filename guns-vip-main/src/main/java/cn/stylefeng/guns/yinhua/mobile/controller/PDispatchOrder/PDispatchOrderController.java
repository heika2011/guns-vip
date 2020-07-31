package cn.stylefeng.guns.yinhua.mobile.controller.PDispatchOrder;


import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderNumLog;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.Teams;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestType;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.service.SelectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分配订单（生产）
 */
@RestController
@RequestMapping("/mobile/PDispatchOrder")
public class PDispatchOrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;

    private static String px = "1237267345014022146";
    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @Permission
    @ApiOperation(value="获取订单列表",notes="token", httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status){
        Page<Order> orderList = selectService.getPDispatchOrderData(page, pageSize, px,status);
        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
    }
    /**
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(8,px);
        return new JSONResult("0","OK",dataCount);
    }
    /**
     * 获取小组数据
     * @return
     */
    @RequestMapping("/getTeamData")
    @ApiOperation(value="获取小组数据",notes="token" , httpMethod = "GET")
    public JSONResult getTeamData(String token){
        List<RestType> restTypes = orderService.getTeamData();
        return new JSONResult("0","OK",restTypes);
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
     * 获取小组信息
     * @return
     */
    @RequestMapping("/getTeam")
    @ApiOperation(value="获取小组信息",notes="token", httpMethod = "GET")
    public JSONResult getTeam(String token,String orderNum){
        List<UserTeam> team = orderService.getTeam(orderNum);
        return new JSONResult("0","ok",team);
    }

    /**
     * 获取属于该订单类型的小组成员信息
     * @return
     */
    @RequestMapping("/getTeamUser")
    @ApiOperation(value="获取属于该订单类型的小组成员信息",notes="token", httpMethod = "GET")
    public JSONResult getTeamUser(String token,String orderNum){
        List<UserTeam> team = orderService.getPattenUser(orderNum);
        return new JSONResult("0","ok",team);
    }
    /**
     * 选择小组的某个成员
     * @return
     */
    @RequestMapping("/changeTeamUser")
    @ApiOperation(value="选小组",notes="token，小组id", httpMethod = "GET")
    public JSONResult changeTeamUser(String token, OrderTeam orderTeam){
        orderService.changeOrderPatten(px,orderTeam);
        if(orderTeam.getTeamId()!=null){
            orderService.sendMessageForMsg(px,orderTeam.getOrderNum());
        }
        return new JSONResult("0","ok");
    }
    /**
     * 选小组
     * @return
     */
    @RequestMapping("/changeTeam")
    @ApiOperation(value="选小组",notes="token，小组id", httpMethod = "GET")
    public JSONResult changeTeam(String token, OrderTeam orderTeam){
        orderService.changeTeam(px,orderTeam);
        if(orderTeam.getTeamId()!=null){
            orderService.sendMessageForMsg(px,orderTeam.getOrderNum());
        }
        return new JSONResult("0","ok");
    }
    /**
     * 获取出货日志
     * @return
     */
    @RequestMapping("/getOutForOrder")
    @ApiOperation(value="获取出货日志",notes="token，生产单编号", httpMethod = "GET")
    public JSONResult getOutForOrder(String token, String order_num){
        List<OrderNumLog> orderNumLogs = orderService.getOutForOrder(order_num);
        return new JSONResult("0","ok",orderNumLogs);
    }

}
