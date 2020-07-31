package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.util.word.beetl.IExportWord;
import cn.stylefeng.guns.util.word.beetl.impl.BTLExportWord;
import cn.stylefeng.guns.util.word.beetl.impl.Base64Util;
import cn.stylefeng.guns.yinhua.admin.entity.Orders;
import cn.stylefeng.guns.yinhua.admin.model.params.OrdersParam;
import cn.stylefeng.guns.yinhua.admin.service.OrdersService;
import cn.stylefeng.guns.yinhua.admin.service.PrintLogService;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.Model;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelColor;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderNum;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderRole;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.ModelCut;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestCustomer;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderRole;
import cn.stylefeng.guns.yinhua.mobile.mapper.OrderPropMapper;
import cn.stylefeng.guns.yinhua.service.CustomerService;
import cn.stylefeng.guns.yinhua.service.ModelService;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.util.FileUtils;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.beetl.core.BeetlKit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


/**
 * 款式控制器
 *
 * @author xiexin
 * @Date 2020-03-14 20:44:40
 */
@Controller
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    private String PREFIX = "/admin/orders";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private PrintLogService printLogService;
    @Autowired
    private OrderPropMapper orderPropMapper;
    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/orders.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/orders_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/edit")
    public String edit(Long num,String orderNum, org.springframework.ui.Model modelUtil) {
        Model model = modelService.getModel(num);
        List<RestCustomer> customerList = customerService.getCustomerList();
        List<OrderNum> orderNums = orderService.getOrderNum(orderNum);
        modelUtil.addAttribute("orderNum",orderNums);
        modelUtil.addAttribute("model",model);
        modelUtil.addAttribute("customer",customerList);
        return PREFIX + "/orders_edit.html";
    }
    /**
     * 修改数量表
     *
     * @author xiexin
     * @Date 2020-03-14 TODO 待完善
     */
    @RequestMapping("/updateOrderNum")
    @ResponseBody
    public JSONResult updateOrderNum(OrderNum orderNum, org.springframework.ui.Model modelUtil) {
        orderService.updateOrderNums(orderNum);
        return new JSONResult("0","OK");
    }
    /**
     * 下单界面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/orderDown")
    public String orderDown(org.springframework.ui.Model model,Long num) {
        List<RestOrderRole> orderType = orderService.getOrderType();
        List<ModelColor> modelColor = modelService.getModelColor(num);
        model.addAttribute("role",orderType);
        model.addAttribute("color",modelColor);
        return PREFIX + "/orders_down.html";
    }
    /**
     * 删除生产单
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/deleteOrder")
    @Permission
    @ResponseBody
    public JSONResult deleteOrder(String orderNum) {
        orderService.deleteOrder(orderNum);
        return new JSONResult("0","ok");
    }
    /**
     * 修改订单
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/updateOrder")
    @ResponseBody
    public JSONResult edit(Order order) {
        orderService.updateOrder(order);
        return new JSONResult("0","OK");
    }

    /**
     * 打印点单
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/getOrderOut")
    public String getOrderOut(String num , org.springframework.ui.Model model) {
        RestAllOrder orderInfo = orderService.getOrderInfo(1, num,true);
        String orderType = orderPropMapper.selectOrderType(orderInfo.getRestOrder().getOrderType());
        String s = Base64Util.creatRrCode(ConstantsContext.getWeChatKxUrl()+"/oauth/render/wechat?url="+num+"&type=20", 400, 400);
        model.addAttribute("images",s);
        model.addAttribute("order",orderInfo);
        model.addAttribute("orderType",orderType);
        return PREFIX + "/fileOut.html";
    }
    /**
     * 打印工艺单
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/getGYDOut")
    public String getGYDOut(String num , org.springframework.ui.Model model) {
        RestAllOrder orderInfo = orderService.getOrderInfo(2, num,true);
        Integer orderType = ordersService.getOrderType(num);
        String orderTypeName = orderPropMapper.selectOrderType(orderInfo.getRestOrder().getOrderType());
        model.addAttribute("date",simpleDateFormat.format(new Date()));
        String s = Base64Util.creatRrCode(ConstantsContext.getWeChatKxUrl()+"/oauth/render/wechat?url="+num+"&type=20", 400, 400);
        model.addAttribute("images",s);
        model.addAttribute("order",orderInfo);
        model.addAttribute("orderType",orderTypeName);
        if(orderType == 1){
            return PREFIX + "/gydOut.html";
        }else if(orderType == 2){
            return PREFIX + "/yiguangdan.html";
        }else if(orderType == 3){
            return PREFIX + "/yimodan.html";
        }
        return PREFIX + "/gydOut.html";

    }
    /**
     * 下单
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addorder")
    @ResponseBody
    public JSONResult addorder(@RequestParam("flag")Integer flag, @RequestBody Order order) {
        Order order1 = orderService.addOrder(order, "1234729638731726850");
        if(flag == 1){
            printLogService.print(order1.getOrderNum(), 1,LoginContextHolder.getContext().getUser().getName(),orderService,1);
        }
        return new JSONResult("0","OK");
    }
    /**
     * 催单
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/uragOrder")
    @ResponseBody
    public JSONResult uragOrder(String orderNum) {
        orderService.uragOrder(orderNum);
        return new JSONResult("0","OK");
    }
    /**
     * 请求报价
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/sendOrderBJ")
    @ResponseBody
    public JSONResult sendOrderBJ(String orderNum) {
        orderService.sendOrderOffer(orderNum);
        return new JSONResult("0","OK");
    }
    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(OrdersParam ordersParam) {
        this.ordersService.add(ordersParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(OrdersParam ordersParam) {
        this.ordersService.update(ordersParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(OrdersParam ordersParam) {
        this.ordersService.delete(ordersParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(OrdersParam ordersParam) {
        Orders detail = this.ordersService.getById(ordersParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(OrdersParam ordersParam) {
        return this.ordersService.findPageBySpec(ordersParam);
    }

}


