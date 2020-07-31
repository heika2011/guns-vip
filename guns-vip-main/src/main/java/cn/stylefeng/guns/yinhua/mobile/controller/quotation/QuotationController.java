package cn.stylefeng.guns.yinhua.mobile.controller.quotation;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelInfo;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderConstLog;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderConst;
import cn.stylefeng.guns.yinhua.service.ModelService;
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
 * 报价
 */
@RestController
@RequestMapping("/mobile/quotation")
public class QuotationController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private ModelService modelService;
    @Autowired
    private SelectService selectService;

    private static String px = "1234731728963125250";

    @Permission
    @RequestMapping("")
    @ApiOperation(value="权限检测",notes="token 24 全部", httpMethod = "GET")
    public JSONResult index(){
        return new JSONResult("0","OK");
    }

    /**
     * 获取订单列表
     * @return
     */
    @Permission
    @RequestMapping("/getOrderList")
    @ApiOperation(value="获取订单列表",notes="token 24 全部", httpMethod = "GET")
    public PageResult<Order> getOrderList(String token, Long page, Long pageSize, Integer status,Integer type,Integer sort,String keyWord){
        Page<Order> orderList = selectService.getQuotationData(page, pageSize, px,status,keyWord,type,sort);
        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
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
     * 获取数据汇总
     * @return
     */
    @GetMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = orderService.getDataCount(10,px);
        return new JSONResult("0","OK",dataCount);
    }
    /**
     * 获取所有当前生产单所有裁片信息
     */
    @GetMapping("/getModelCut")
    @ApiOperation(value="获取所有当前生产单所有裁片价格",notes="token,裁片编号" , httpMethod = "GET")
    public JSONResult getModelCut(String token, Long num){
        List<ModelInfo> modelInfo = modelService.getModelCut(num);
        return new JSONResult("0","OK",modelInfo);
    }

    /**
     * 修改单个裁片价格
     */
    @GetMapping("/changeModelInfoMuchs")
    @ApiOperation(value="修改单个裁片价格",notes="token,裁片id" , httpMethod = "GET")
    public JSONResult changeModelInfoMuchs(String token, Integer id,Double muchs){
        modelService.changeModelInfoMuchs(id,muchs);
        return new JSONResult("0","OK");
    }
    /**
     * 发送报价单
     */
    @GetMapping("/sendModelInfoMuchs")
    @ApiOperation(value="发送报价单",notes="token,订单编号，计算价，回扣价，最终报价" , httpMethod = "GET")
    public JSONResult sendModelInfoMuchs(String token, OrderConstLog constLog){
        orderService.sendModelInfoMuchs(constLog,px);
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
     * 修改备注
     */
    @GetMapping("/updateModelMuchsNote")
    @ApiOperation(value="修改备注",notes="token,订单编号,内容" , httpMethod = "GET")
    public JSONResult updateModelMuchsNote(String token,String orderNum,String text){
        orderService.updateModelMuchsNote(orderNum,text);
        return new JSONResult("0","OK");
    }
    /**
     * 修改改价备注
     */
    @GetMapping("/updateModelMuchsChangeNote")
    @ApiOperation(value="修改改价备注",notes="token,订单编号,内容" , httpMethod = "GET")
    public JSONResult updateModelMuchsChangeNote(String token,String orderNum,String text){
        orderService.updateModelMuchsChangeNote(orderNum,text);
        return new JSONResult("0","OK");
    }

    /**
     * 获取上次报价
     * @return
     */
    @GetMapping("/getLastCount")
    @ApiOperation(value="获取上次报价",notes="获取上次报价" , httpMethod = "GET")
    public JSONResult getLastCount(String token,String orderNum){
        Map d = orderService.getLastCount(orderNum);
        return new JSONResult("0","操作成功",d);
    }

    /**
     * 获取款式备注
     * @return
     */
    @GetMapping("/getModelNote")
    @ApiOperation(value="获取款式备注",notes="获取款式备注" , httpMethod = "GET")
    public JSONResult getModelNote(String token,String orderNum){
        String note = modelService.getModelNoteByOrderNum(orderNum);
        return new JSONResult("0","OK",note);
    }
    /**
     * 保存款式备注
     * @return
     */
    @GetMapping("/saveModelNote")
    @ApiOperation(value="保存款式备注",notes="保存款式备注" , httpMethod = "GET")
    public JSONResult saveModelNote(String token,String orderNum,String note){
        modelService.saveModelNote(orderNum,note);
        return new JSONResult("0","OK");
    }

}
