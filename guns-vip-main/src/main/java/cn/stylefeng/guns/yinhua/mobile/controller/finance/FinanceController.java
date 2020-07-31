package cn.stylefeng.guns.yinhua.mobile.controller.finance;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.FaConst;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderConstLog;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderSheet;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderConst;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.service.SelectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 财务
 */
@RestController
@RequestMapping("/mobile/finance")
public class FinanceController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;
    private static String px = "1234731593336111106";
    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @Permission
    @ApiOperation(value="获取订单列表",notes="token 25 未报价 26已报价 27暂不开单 28不开单", httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer type,Integer sort,String keyWord){
        Page<Order> orderList =  selectService.getFinanceData(page, pageSize, px,type,sort,keyWord);
        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
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
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(11,px);
        return new JSONResult("0","OK",dataCount);
    }
    /**
     * 财务设置订单状态
     * @return
     */
    @RequestMapping("/setOrderStatus")
    @ApiOperation(value="财务设置订单状态",notes="token" , httpMethod = "GET")
    public JSONResult setOrderStatus(String token,Integer type,String orderNum,Double money,String num,String url){
        orderService.setOrderStatus(type,orderNum,px,money,num,url);
        if(type!=null){
            orderService.sendMessageForMsg(px,orderNum);
        }
        return new JSONResult("0","OK");
    }
    /**
     * 财务设置赔偿金额
     * @return
     */
    @RequestMapping("/setOrderMakeUp")
    @ApiOperation(value="财务设置订单状态",notes="token" , httpMethod = "GET")
    public JSONResult setOrderMakeUp(String token, OrderConstLog orderConstLog){
        orderService.setOrderMakeUp(orderConstLog);
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
     * 获取报价单信息
     */
    @GetMapping("/getModelMuchs")
    @ApiOperation(value="获取报价单信息",notes="token,订单编号" , httpMethod = "GET")
    public JSONResult getModelMuchs(String token,String orderNum){
        RestOrderConst modelMuchs = orderService.getModelMuchs(orderNum);
        return new JSONResult("0","OK",modelMuchs);
    }
    /**
     * 获取财务设置完成的金额
     */
    @GetMapping("/getOrderConstByF")
    @ApiOperation(value="获取财务设置完成的金额",notes="token,订单编号" , httpMethod = "GET")
    public JSONResult getOrderConstByF(String token,String orderNum){
        FaConst faConst = orderService.getOrderConstByF(orderNum);
        return new JSONResult("0","OK",faConst);
    }

    /**
     * 上传数据
     * @param token
     * @return
     */
    @RequestMapping("/uploadSheet")
    @ApiOperation(value="上传票据",notes="token,生产单编号" , httpMethod = "POST")
    public JSONResult uploadSheet(@RequestParam("token") String token, @RequestBody Map<String,String> param){
        orderService.addOrderSheet(param);
        return new JSONResult("0","ok");
    }

    /**
     * 获取上传的图片
     * @param token
     * @return
     */
    @RequestMapping("/getOrderSheetForCW")
    @ApiOperation(value="获取上传的图片",notes="token,生产单编号" , httpMethod = "POST")
    public JSONResult getOrderSheetForCW(String token,String orderNum){
        String text = orderService.getOrderSheetForCW(orderNum);
        return new JSONResult("0","ok",text);
    }

    /**
     * 修改上传的图片
     * @param token
     * @return
     */
    @RequestMapping("/updateSheetForCW")
    @ApiOperation(value="修改上传的图片",notes="token,生产单编号" , httpMethod = "POST")
    public JSONResult updateSheetForCW(String token,String orderNum,String text){
        orderService.updateSheetForCW(orderNum,text);
        return new JSONResult("0","ok");
    }
}
