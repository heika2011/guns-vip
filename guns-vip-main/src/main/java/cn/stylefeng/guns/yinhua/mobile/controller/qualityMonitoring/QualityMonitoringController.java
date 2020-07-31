package cn.stylefeng.guns.yinhua.mobile.controller.qualityMonitoring;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.TeamsData;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
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
 * 质量监控
 */
@RestController
@RequestMapping("/mobile/qualityMonitoring")
public class QualityMonitoringController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;
    //待前端测试
    /**
     * 质量监控
     * @return
     */
    @RequestMapping("/getQualityMonitoringData")
    @ApiOperation(value="小组数据统计",notes="token" , httpMethod = "GET")
    public JSONResult getQualityMonitoringData(String token, Date startTime, Date overTime){
        RestOrderCount teamsDataList = orderService.getQualityMonitoringData(startTime,overTime);
        return new JSONResult("0","OK",teamsDataList);
    }
    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @Permission
    @ApiOperation(value="获取订单列表 1 返工列表 2补片列表 3重做列表 4赔偿列表",notes="token", httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status,Date startTime,Date overTime){
        Page<Order> orderList = selectService.getQualityMonitoringOrderData(page, pageSize,status,startTime,overTime);
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
}
