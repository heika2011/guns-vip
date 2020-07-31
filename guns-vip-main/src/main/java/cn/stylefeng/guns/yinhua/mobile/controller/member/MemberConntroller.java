package cn.stylefeng.guns.yinhua.mobile.controller.member;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderSheet;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderNumCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderNumCountFor;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.service.SelectService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 生产组成员
 */
@RestController
@RequestMapping("/mobile/member")
public class MemberConntroller {
    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;
    private static String px = "1234731382404562946";
    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @Permission
    @ApiOperation(value="获取订单列表",notes="token,20 未准备好 21 已准备好 11催单" , httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status){
        Page<Order> orderList = selectService.getMemberList(page, pageSize, "1234731218520522753",status);
        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
    }
    /**
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(9,"1");
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
     * 查看生产单
     */
    @RequestMapping("/getOrderSheet")
    @ApiOperation(value="查看生产单",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrderSheet(String token, String order_num){
        List<OrderSheet> orderSheet = orderService.getOrderSheet(order_num);
        return new JSONResult("0","OK",orderSheet);
    }
    /**
     * 员工参与
     */
    @RequestMapping("/joinOrder")
    @ApiOperation(value="员工参与",notes="token,生产单编号,类型" , httpMethod = "GET")
    public JSONResult joinOrder(String token, String order_num,Integer... type){
        orderService.joinOrder(order_num,type);
        return new JSONResult("0","OK");
    }
    /**
     * 查看岗位确定情况
     */
    @RequestMapping("/getMemberJoinInfo")
    @ApiOperation(value="查看岗位确定情况",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getMemberJoinInfo(String token, String order_num){
        List<OrderTeam> list = orderService.getMemberJoinInfo(order_num);
        return new JSONResult("0","OK",list);
    }
    /**
     * 网板位置填写
     */
    @RequestMapping("/updateSceenLocal")
    @ApiOperation(value="网板位置填写",notes="token,生产单编号,类型" , httpMethod = "GET")
    public JSONResult updateSceenLocal(String token, String order_num,String local){
        orderService.updateSceenLocal(order_num,local);
        return new JSONResult("0","OK");
    }
    /**
     * 网板位置获取
     */
    @RequestMapping("/getSceenLocal")
    @ApiOperation(value="网板位置获取",notes="token,生产单编号,类型" , httpMethod = "GET")
    public JSONResult getSceenLocal(String token, String order_num){
        String o = orderService.getSceenLocal(order_num);
        return new JSONResult("0","OK",o);
    }
    /**
     * 获取今天的工作数据
     */
    @RequestMapping("/getTodayCount")
    @ApiOperation(value="获取今天的工作数据",notes="token,生产单编号,类型" , httpMethod = "GET")
    public JSONResult getTodayCount(String token){
        List<RestOrderNumCount> restOrderNumCounts = orderService.getTodayCount();
        return new JSONResult("0","OK",restOrderNumCounts);
    }
    /**
     * 获取区间数据
     */
    @RequestMapping("/getOtherTodayCount")
    @ApiOperation(value="获取区间数据",notes="token,生产单编号,类型" , httpMethod = "GET")
    public JSONResult getOtherTodayCount(String token, Date startTime, Date overTime){
        List<RestOrderNumCount> restOrderNumCounts = orderService.getOtherTodayCount(startTime,overTime);
        String moeny = orderService.getOrderMoney(startTime,overTime);
        RestOrderNumCountFor restOrderNumCountFor = new RestOrderNumCountFor();
        restOrderNumCountFor.setRestOrderNumCounts(restOrderNumCounts);
        restOrderNumCountFor.setMoney(moeny);
        return new JSONResult("0","OK",restOrderNumCountFor);
    }
}
