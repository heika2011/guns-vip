package cn.stylefeng.guns.yinhua.mobile.controller.ScreenPrintingColor;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderPropNote;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderSheet;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestType;
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
 * 丝印调色
 */
@RestController
@RequestMapping("/mobile/ScreenPrintingColor")
public class ScreenPrintingColorController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;
    private static String px = "1234730294012035073";

    /**
     * 获取订单列表
     * @return
     */
    @RequestMapping("/getOrderList")
    @Permission
    @ApiOperation(value="获取订单列表",notes="token,14 打样 15生产单 11催单 17无工艺单", httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status){
        Page<Order> orderList = selectService.getScreenPrintingColorData(page, pageSize, px,status);
        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
    }
    /**
     * 获取数据汇总
     * @return
     */
    @GetMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(4,px);
        return new JSONResult("0","OK",dataCount);
    }
    /**
     * 获取订单详情信息
     * @return
     */
    @GetMapping("/getOrderInfo")
    @Permission
    @ApiOperation(value="生产单编号",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrderInfo(String token, String order_num){
        RestAllOrder restAllOrder = orderService.getOrderInfo(1,order_num,false);
        return new JSONResult("0","OK",restAllOrder);
    }
    /**
     * 更新进度
     * @return
     */
    @GetMapping("/updateOrderProp")
    @ApiOperation(value="更新订单进度",notes="token，生产单编号", httpMethod = "GET")
    public JSONResult updateOrderProp(String token, OrderPropNote orderPropNote){
        /* 检查工艺单是否上传 */
        /*if(orderPropNote.getFlagDo() == 1){
            orderService.checkOrderSheet(orderPropNote.getOrderNum());
        }*/
        orderService.updateOrderProp(px,0,orderPropNote);
        orderService.updateOrderUserForScreen(orderPropNote);
        if(orderPropNote.getFlagDo() == 1){
            orderService.sendMessageForMsg(px,orderPropNote.getOrderNum());
        }
        return new JSONResult("0","ok");
    }
    /**
     * 工艺单信息上传
     * @return
     */
    @GetMapping("/uploadOrderSheet")
    @ApiOperation(value="工艺单信息上传",notes="token，生产单编号", httpMethod = "GET")
    public JSONResult uploadOrderSheet(String token, OrderSheet orderSheet){
        orderService.uploadOrderSheet(orderSheet);
        return new JSONResult("0","ok");
    }
    /**
     * 查看工艺单
     */
    @GetMapping("/getOrderSheet")
    @ApiOperation(value="查看工艺单",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrderSheet(String token, String order_num){
        List<OrderSheet> orderSheet = orderService.getOrderSheet(order_num);
        return new JSONResult("0","OK",orderSheet);
    }
    /**
     *
     * 工作统计
     * @return
     */
    @RequestMapping("/getCount")
    @ApiOperation(value="工作统计",notes="token，月份", httpMethod = "GET")
    public JSONResult getCount(String token, Integer mouth){
        List<RestType> restTypeList = orderService.getCount(mouth,"(20,22)");
        return new JSONResult("0","ok",restTypeList);
    }
}
