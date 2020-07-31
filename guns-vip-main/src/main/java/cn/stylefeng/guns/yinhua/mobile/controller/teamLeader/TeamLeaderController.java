package cn.stylefeng.guns.yinhua.mobile.controller.teamLeader;

import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderNumCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderNumCountFor;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeamU;
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
 * 生产组长
 */
@RestController
@RequestMapping("/mobile/teamLeader")
public class TeamLeaderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private SelectService selectService;
    private static String px = "1234731218520522753";
    /**
     * 获取订单列表
     * @return
     */
    @GetMapping("/getOrderList")
    @ApiOperation(value="获取订单列表",notes="token,20 未准备好 21 已准备好 11催单" , httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status,String keyWord){
        Page<Order> orderList = selectService.getTeamLeaderData(page, pageSize, px,status,keyWord);
        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
    }
    /**
     * 获取数据汇总
     * @return
     */
    @GetMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(9,"0");
        return new JSONResult("0","OK",dataCount);
    }
    /**
     * 获取订单详情信息
     * @return
     */
    @GetMapping("/getOrderInfo")
    @ApiOperation(value="生产单编号",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrderInfo(String token, String order_num){
        RestAllOrder restAllOrder = orderService.getOrderInfo(1,order_num,false);
        return new JSONResult("0","OK",restAllOrder);
    }

    /**
     * 查看生产单
     */
    @GetMapping("/getOrderSheet")
    @ApiOperation(value="查看生产单",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrderSheet(String token, String order_num){
        List<OrderSheet> orderSheet = orderService.getOrderSheet(order_num);
        return new JSONResult("0","OK",orderSheet);
    }
    /**
     * 获取参与情况
     */
    @GetMapping("/getOrderForTeam")
    @ApiOperation(value="获取参与情况",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult getOrderForTeam(String token, String order_num){
        List<OrderTeam> teams = orderService.getOrderForTeam(order_num);
        return new JSONResult("0","OK",teams);
    }
    /**
     * 同意与不同意修改
     */
    @GetMapping("/changeOrderForTeam")
    @ApiOperation(value="同意与不同意修改",notes="token,生产单编号 1表示同意 0表示不同意" , httpMethod = "GET")
    public JSONResult changeOrderForTeam(String token, String order_num,Long user_id,Integer flag,Integer type){
        orderService.changeOrderForTeam(order_num,user_id,flag,type);
        return new JSONResult("0","OK");
    }
    /**
     * 选择员工
     */
    @GetMapping("/selectOrderForTeam")
    @ApiOperation(value="选择员工",notes="token,生产单编号 1表示同意 0表示不同意" , httpMethod = "GET")
    public JSONResult selectOrderForTeam(String token, String order_num,Long user_id,Integer type,String name){
        orderService.selectOrderForTeam(order_num,user_id,type,name);
        return new JSONResult("0","OK");
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
    /**
     * 获取订单总数
     * @return
     */
    @RequestMapping("/getOrderAllCount")
    @ApiOperation(value="获取订单总数",notes="token，生产单编号", httpMethod = "GET")
    public JSONResult getOrderAllCount(String token, String order_num){
        String o = orderService.getOrderAllCount(order_num);
        return new JSONResult("0","ok",o);
    }
    /**
     * 获取小组成员
     */
    @GetMapping("/getOrderForTeamMember")
    @ApiOperation(value="获取小组成员",notes="token" , httpMethod = "GET")
    public JSONResult getOrderForTeamMember(String token,String orderNum){
        List<UserTeamU> teams = orderService.getOrderForTeamMember(orderNum);

        return new JSONResult("0","OK",teams);
    }
    /**
     * 选择员工对应岗位
     */
    @GetMapping("/addTeamForUser")
    @ApiOperation(value="选择员工对应岗位",notes="token" , httpMethod = "GET")
    public JSONResult addTeamForUser(String token,OrderTeam orderteam){
        orderService.addTeamForUser(orderteam);
        return new JSONResult("0","OK");
    }
    /**
     *
     * 完成订单
     * @return
     */
    @GetMapping("/updateOrderProp")
    @ApiOperation(value="更新订单进度",notes="token，生产单编号", httpMethod = "GET")
    public JSONResult updateOrderProp(String token, OrderPropNote orderPropNote,Double overCount,String units){
        orderService.updateOrderCount(orderPropNote.getOrderNum(),overCount,units);
        orderPropNote.setOverTime(new Date());
        orderService.updateOrderProp(px,2,orderPropNote);
        orderService.updateOrderUserForTeam(orderPropNote.getOrderNum(),overCount);
        if(orderPropNote.getFlagDo()==1){
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
     * 出库
     * @return
     */
    @GetMapping("/updateOrderNum")
    @ApiOperation(value="出库",notes="token，生产单编号", httpMethod = "GET")
    public JSONResult updateOrderNum(String token,String order_num, Double num,Integer type,Integer id,String color){
        orderService.updateOrderNum(order_num,num,type,id,color);
        return new JSONResult("0","ok");
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
     * 获取数量表
     */
    @RequestMapping("/getOrderNumList")
    @ApiOperation(value="获取数量表",notes="token,生产单编号,类型" , httpMethod = "GET")
    public JSONResult getOrderNumList(String token, String order_num){
        List<OrderNum> o = orderService.getOrderNumList(order_num);
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
     * 结束分派
     */
    @RequestMapping("/overFP")
    @ApiOperation(value="结束分派",notes="token,生产单编号" , httpMethod = "GET")
    public JSONResult overFP(String token,String orderNum){
        orderService.overFp(orderNum);
        return new JSONResult("0","OK");
    }

    /**
     * 获取区间数据
     */
    @RequestMapping("/getOtherTodayCount")
    @ApiOperation(value="获取区间数据",notes="token,生产单编号,类型" , httpMethod = "GET")
    public JSONResult getOtherTodayCount(String token, Date startTime,Date overTime){
        List<RestOrderNumCount> restOrderNumCounts = orderService.getOtherTodayCount(startTime,overTime);
        String moeny = orderService.getOrderMoney(startTime,overTime);
        RestOrderNumCountFor restOrderNumCountFor = new RestOrderNumCountFor();
        restOrderNumCountFor.setRestOrderNumCounts(restOrderNumCounts);
        restOrderNumCountFor.setMoney(moeny);
        return new JSONResult("0","OK",restOrderNumCountFor);
    }
}
