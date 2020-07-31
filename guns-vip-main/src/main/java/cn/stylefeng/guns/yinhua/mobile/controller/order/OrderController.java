package cn.stylefeng.guns.yinhua.mobile.controller.order;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.yinhua.admin.entity.PrintAre;
import cn.stylefeng.guns.yinhua.admin.service.PrintAreService;
import cn.stylefeng.guns.yinhua.admin.service.PrintLogService;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderProp;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderPropNote;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderRole;
import cn.stylefeng.guns.yinhua.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单请求
 */
@RestController
@RequestMapping("/mobile/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PrintLogService printLogService;

    @Autowired
    private PrintAreService printAreService;

    private String px = "1234729638731726850";


    /**
     * 获取打印设备
     */
    @RequestMapping("/getPrintArea")
    public JSONResult getPrintArea(String token){
        List<PrintAre> list = printAreService.list();
        return new JSONResult("0","OK",list);
    }
    /**
     * 查询我的默认打印机
     */
    @RequestMapping("/getMyPrintId")
    public JSONResult getMyPrintId(String token){
        Integer id = orderService.getMyPrintId();
        if(null == id){
            return new JSONResult("-1","OK");
        }
        return new JSONResult("0","OK",id);
    }
    /**
     * 获取订单类型
     * @return
     */
    @RequestMapping("/getOrderType")
    @ApiOperation(value="获取订单类型",notes="token", httpMethod = "GET")
    public JSONResult getOrderType(String token){
        List<RestOrderRole> restOrderRoleList = orderService.getOrderType();
        return new JSONResult("0","OK",restOrderRoleList);
    }
    /**
     * 下单
     * @return
     */
    @RequestMapping("/addOrder")
    @ApiOperation(value="下单",notes="token，订单信息", httpMethod = "GET")
    public JSONResult addOrder(@RequestParam("token") String token,@RequestParam("flag") Integer flag,@RequestParam("printId") Integer printId,@RequestParam("remember") Integer remember, @RequestBody Order order){
        Order orders = orderService.addOrder(order,px);
        orderService.sendMessageForMsg(px,order.getOrderNum());
        if(flag == 1 || flag == 3){
            if(remember == 1){
                orderService.updatePrintId(printId);
            }
            printLogService.print(orders.getOrderNum(),1, LoginContextHolder.getContext().getUser().getName(),orderService,printId);
        }
        if(flag == 2 || flag == 3){
            if(remember == 1){
                orderService.updatePrintId(printId);
            }
            printLogService.print(orders.getOrderNum(),2, LoginContextHolder.getContext().getUser().getName(),orderService,printId);
        }
        return new JSONResult("0","OK");
    }
    /**
     *  获取订单进度日志
     * @return
     */
    @RequestMapping("/getOrderPropNote")
    @ApiOperation(value="获取订单进度日志",notes="token，订单信息", httpMethod = "GET")
    public JSONResult getOrderPropNote(String token,String orderNum){
        List<OrderPropNote> orderPropNotes = orderService.getOrderPropNote(orderNum);
        return new JSONResult("0","OK",orderPropNotes);
    }

    /**
     * 获取单个人或则单个角色的进度
     * @return
     */
    @RequestMapping("/getOrderPropById")
    @ApiOperation(value="获取单个人或则单个角色的进度",notes="获取单个人或则单个角色的进度", httpMethod = "GET")
    public JSONResult getOrderPropById(String token,OrderProp orderProp){
        List<OrderPropNote> orderPropNotes = orderService.getUserOrRoleProp(orderProp);
        return new JSONResult("0","OK",orderPropNotes);
    }

    /**
     * 单个订单进度催办
     */
    @RequestMapping("/toUrag")
    @ApiOperation(value="单个订单进度催办",notes="单个订单进度催办", httpMethod = "GET")
    public JSONResult toUrag(String token,OrderPropNote orderPropNote){
        orderService.toUrag(orderPropNote);
        return new JSONResult("0","OK");
    }
    /**
     * 工艺单删除
     */
    @RequestMapping("/deleteOrderSheet")
    @Permission
    @ApiOperation(value="工艺单删除",notes="工艺单删除", httpMethod = "GET")
    public JSONResult deleteOrderSheet(String token,Long id){
        orderService.deleteOrderSheet(id);
        return new JSONResult("0","OK");
    }

    /**
     * 动态菜单获取
     */
    @RequestMapping("/deleteGetAllMenu")
    @Permission
    @ApiOperation(value="动态菜单获取",notes="动态菜单获取", httpMethod = "GET")
    public JSONResult deleteGetAllMenu(){
        List<Map> list = orderService.deleteGetAllMenu();
        return new JSONResult("0","OK",list);
    }

}
