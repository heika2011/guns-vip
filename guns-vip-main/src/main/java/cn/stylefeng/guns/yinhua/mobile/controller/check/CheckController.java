package cn.stylefeng.guns.yinhua.mobile.controller.check;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
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

/**
 * 总检
 */
@RestController
@RequestMapping("/mobile/check")
public class CheckController {

    @Autowired
    private SelectService selectService;

    @Autowired
    private OrderService orderService;
    private static String px = "1234731218520522753";
    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @Permission
    @ApiOperation(value="获取订单列表",notes="token,20 未准备好 21 已准备好 11催单" , httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status){
        Page<Order> orderList = selectService.getCheckData(page, pageSize, null,status);
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
        RestAllOrder restAllOrder = orderService.getOrderInfo(2,order_num,false);
        return new JSONResult("0","OK",restAllOrder);
    }
    /**
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(21,null);
        return new JSONResult("0","OK",dataCount);
    }
}
