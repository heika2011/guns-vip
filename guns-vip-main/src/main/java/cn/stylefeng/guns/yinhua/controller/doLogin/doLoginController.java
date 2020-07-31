package cn.stylefeng.guns.yinhua.controller.doLogin;

import cn.stylefeng.guns.base.auth.service.AuthService;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelInfo;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.ModelCut;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllModel;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderConst;
import cn.stylefeng.guns.yinhua.service.LoginService;
import cn.stylefeng.guns.sys.modular.third.service.WeChatForTokenUtil;
import cn.stylefeng.guns.yinhua.service.ModelService;
import cn.stylefeng.guns.yinhua.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 前端用户登录业务
 */
@RestController
@Api(value="",description="用户微信登录业务")
@RequestMapping("/mobile")
public class doLoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private AuthService authService;
    @Autowired
    private SessionManager sessionManager;
    @Autowired
    private ModelService modelService;
    @Autowired
    private OrderService orderService;
    /**
     * 用户登录
     * @param code 微信第三方ID
     * @return
     */
    @GetMapping("/doLogin")
    @ApiOperation(value="用户微信登录",notes="需要微信code,登录成功后自动返回token", httpMethod = "GET")
    public JSONResult doLogin(String code){
        loginService.doLoginUser(code);
        String login = authService.login(code);
        WeChatForTokenUtil.saveUser(login,code,authService,sessionManager);
        return new JSONResult("0","登录成功", login);
    }
    @GetMapping("/share")
    @ApiOperation(value="分享",notes="分享", httpMethod = "GET")
    public JSONResult share(String order_num){
        RestAllOrder orderInfo = orderService.getOrderInfo(3, order_num,false);
        return new JSONResult("0","ok", orderInfo);
    }

    /**
     * 获取报价单信息
     */
    @GetMapping("/getModelMuchs")
    @ApiOperation(value="获取报价单信息",notes="token,订单编号" , httpMethod = "GET")
    public JSONResult getModelMuchs(String orderNum){
        RestOrderConst modelMuchs = orderService.getModelMuchs(orderNum);
        return new JSONResult("0","OK",modelMuchs);
    }

    @GetMapping("/getModelInfoList")
    @ApiOperation(value="获取裁片列表",notes="token,订单编号" , httpMethod = "GET")
    public JSONResult getModelInfoList(Long num){
        List<ModelCut> modelInfo = modelService.getModelInfo(num);
        return new JSONResult("0","OK",modelInfo);
    }

    @GetMapping("/getAllModel")
    @ApiOperation(value="获取裁片列表",notes="token,订单编号" , httpMethod = "GET")
    public JSONResult getAllModel(Long num){
        RestAllModel restAllModel = modelService.getAllModel(num);
        return new JSONResult("0","OK",restAllModel);
    }

    @GetMapping("/getModelInfo")
    @ApiOperation(value="获取裁片列表",notes="token,订单编号" , httpMethod = "GET")
    public JSONResult getModelInfo(Integer id){
        ModelCut modelCut = modelService.getModelInfoById(id);
        return new JSONResult("0","OK",modelCut);
    }
}
