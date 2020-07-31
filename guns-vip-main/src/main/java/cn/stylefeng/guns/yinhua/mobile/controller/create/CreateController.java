package cn.stylefeng.guns.yinhua.mobile.controller.create;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.yinhua.admin.service.PrintLogService;
import cn.stylefeng.guns.yinhua.entity.yinhua.Express;
import cn.stylefeng.guns.yinhua.entity.yinhua.FaConst;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelNote;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderRole;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderSheet;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderRole;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestPageAndData;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.service.SelectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 生成单
 */
@RestController
@RequestMapping("/mobile/create_order")
public class CreateController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;

    @Autowired
    private PrintLogService printLogService;
    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @Permission
    @ApiOperation(value="获取订单列表",notes="token", httpMethod = "GET")
    public RestPageAndData getOrderList(String token, Long page, Long pageSize, Integer status,Integer type,Integer sort,String keyWord){
        RestPageAndData orderList = selectService.getCreateOrderList(page, pageSize,null ,status,type,sort,keyWord);
        return orderList;
    }
    /**
     * 获取所有订单类型
     * @return
     */
    @RequestMapping("/getOrderRole")
    @ApiOperation(value="获取所有订单类型",notes="token" , httpMethod = "GET")
    public JSONResult getOrderRole(String token){
        List<RestOrderRole> orderType = orderService.getOrderType();
        return new JSONResult("0","OK",orderType);
    }
    /**
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(1,null);
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
     * 单独获取订单信息
     * @return
     */
    @RequestMapping("/getOrder")
    @ApiOperation(value="生产单编号",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrder(String token, String order_num){
        RestOrder order = orderService.getOrder(order_num);
        return new JSONResult("0","OK",order);
    }
    /**
     * 催单
     * @return
     */
    @RequestMapping("/uragOrder")
    @ApiOperation(value="催单",notes="token,生产单编号", httpMethod = "GET")
    public JSONResult uragOrder(String token, String order_num){
        orderService.uragOrder(order_num);
        return new JSONResult("0","OK");
    }
    /**
     * 复制生产单信息
     * @return
     */
    @RequestMapping("/copyOrder")
    @ApiOperation(value="催单",notes="token,生产单编号", httpMethod = "GET")
    public JSONResult copyOrder(String token, String order_num){
        orderService.copyOrder(order_num);
        return new JSONResult("0","OK");
    }


    /**
     * 生产单异常
     * @return
     */
    @RequestMapping("/errorOrder")
    @ApiOperation(value="生产单异常",notes="token,生产单编号，异常状态", httpMethod = "GET")
    public JSONResult errorOrder(String token, String order_num,Integer errorCode,String text){
        orderService.errorOrder(order_num,errorCode,"1234729638731726850",text);
        return new JSONResult("0","OK");
    }
    /**
     * 生产单修改
     * @return
     */
    @RequestMapping("/changeOrder")
    @ApiOperation(value="生产单修改",notes="token", httpMethod = "GET")
    public JSONResult changeOrderStatus(String token, Order order){
        orderService.updateOrder(order);
        return new JSONResult("0","OK");
    }
    /**
     * 获取跟单员
     */
    @RequestMapping("/getCreater")
    @ApiOperation(value="获取其他跟单员",notes="token", httpMethod = "GET")
    public JSONResult getCreater(String token){
        List<Map<String, String>> creater = orderService.getCreater();
        return new JSONResult("0","OK",creater);
    }
    /**
     * 生产单转让
     * @return
     */
    @RequestMapping("/changeOrderCreate")
    @ApiOperation(value="生产单转让",notes="token,生产单Id,转向跟单员id", httpMethod = "GET")
    public JSONResult changeOrderCreate(String token, String order_num,Long user_id){
        orderService.changeOrderCreate(order_num,user_id);
        return new JSONResult("0","OK");
    }
    /**
     * 请求报价
     */
    @RequestMapping("/sendOrderOffer")
    @ApiOperation(value="请求报价",notes="token,生产单Id", httpMethod = "GET")
    public JSONResult sendOrderOffer(String token, String order_num){
        orderService.sendOrderOffer(order_num);
        return new JSONResult("0","OK");
    }
    /**
     * 请求打印生产单
     */
    @RequestMapping("/printOrder")
    @ApiOperation(value="请求打印生产单",notes="token,生产单Id", httpMethod = "GET")
    public JSONResult printOrder(String token, String order_num,Integer printId,Integer remember){
        if(remember == 1){
            orderService.updatePrintId(printId);
        }
        printLogService.print(order_num,1, LoginContextHolder.getContext().getUser().getName(),orderService,printId);
        return new JSONResult("0","OK");
    }
    /**
     * 请求打印工艺单
     */
    @RequestMapping("/printOrderSheet")
    @ApiOperation(value="请求打印工艺单",notes="token,生产单Id", httpMethod = "GET")
    public JSONResult printOrderSheet(String token, String order_num,Integer printId,Integer remember){
        if(remember == 1){
            orderService.updatePrintId(printId);
        }
        printLogService.print(order_num,2, LoginContextHolder.getContext().getUser().getName(),orderService,printId);
        return new JSONResult("0","OK");
    }
    /**
     * 送货通知
     */
    @RequestMapping("/deliveryOrderOffer")
    @ApiOperation(value="送货通知",notes="token,生产单Id", httpMethod = "GET")
    public JSONResult deliveryOrderOffer(String token, String order_num){
        orderService.deliveryOrderOffer(order_num);
        return new JSONResult("0","OK");
    }
    /**
     * 寄货通知
     */
    @RequestMapping("/sendJHOrderOffer")
    @ApiOperation(value="寄货通知",notes="token,生产单Id，快递名称，快递编号", httpMethod = "GET")
    public JSONResult sendJHOrderOffer(String token, String order_num, Express express){
        orderService.sendJHOrderOffer(order_num,express);
        return new JSONResult("0","OK");
    }
    /**
     * 获取快递信息
     */
    @RequestMapping("/getExpressInfo")
    @ApiOperation(value="获取快递信息",notes="token,生产单Id，快递名称，快递编号", httpMethod = "GET")
    public JSONResult getExpressInfo(String token, String order_num){
        Express express = orderService.getExpressInfo(order_num);
        return new JSONResult("0","OK",express);
    }
    /**
     * 提货通知
     */
    @RequestMapping("/sendPickUpOffer")
    @ApiOperation(value="提货通知",notes="token,生产单Id", httpMethod = "GET")
    public JSONResult sendPickUpOffer(String token, String order_num,String addr,String phone){
        orderService.sendPickUpOffer(order_num,addr,phone);
        return new JSONResult("0","OK");
    }
    /**
     * 获取款式日志
     */
    @RequestMapping("/getOrderNote")
    @ApiOperation(value="获取款式日志",notes="token,生产单Id", httpMethod = "GET")
    public JSONResult getOrderNote(String token, String order_num){
        List<ModelNote> modelNotes = orderService.getOrderNote(order_num);
        return new JSONResult("0","OK",modelNotes);
    }
    /**
     * 订单完成！
     */
    @RequestMapping("/overOrder")
    @ApiOperation(value="订单完成！",notes="token,生产单Id", httpMethod = "GET")
    public JSONResult overOrder(String token, String order_num){
        orderService.overOrder(order_num);
        return new JSONResult("0","OK");
    }

    /**
     * 暂不开单和不开单
     */
    @RequestMapping("/setOrderNoTicket")
    @ApiOperation(value="暂不开单和不开单",notes="token,生产单Id", httpMethod = "GET")
    public JSONResult setOrderNoTicket(String token, String orderNum,Integer type){
        if(type == 5){
            throw new OutExcetion("不开单功能暂停使用");
        }
        orderService.setOrderStatus(type,orderNum,"1234731593336111106",null,null,null);
        orderService.checkOrderConsolt(orderNum);
        return new JSONResult("0","OK");
    }

    /**
     * 查看回单
     * @param token
     * @return
     */
    @RequestMapping("/getOrderSheet")
    @ApiOperation(value="查看回单",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrderSheet(String token,String orderNum){
        OrderSheet orderSheet = orderService.getOrderSheetByOrderNum(orderNum);
        return new JSONResult("0","ok",orderSheet);
    }
}
