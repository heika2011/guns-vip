package cn.stylefeng.guns.yinhua.controller.wechatShare;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.service.ModelService;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.service.WeXinService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 *
 * 企业微信分享 配置
 * @Date 2020/4/4
 * @Auth xiexin
 */
@Controller
@RequestMapping("/weixin")
public class WeiXinShareController {

    @Autowired
    private WeXinService weiXinService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ModelService modelService;
    /**
     * 初始化微信JSSDK配置信息
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/initWXJSSDKConfigInfo")
    @ResponseBody
    public String initWXJSConfig (HttpServletRequest request, HttpServletResponse response) throws Exception{
        String shareUrl = request.getParameter("shareUrl");//分享的URL
        Map map = weiXinService.initJSSDKConfigInfo(shareUrl);
        String json = JSONObject.toJSONString(map);
        return json;
    }

    @RequestMapping("/doShare")
    public String logingShare(String orderNum, Integer type,Model model){
        if(null!=type && type ==23){
            cn.stylefeng.guns.yinhua.entity.yinhua.model.Model model1 = modelService.getModel(Long.valueOf(orderNum));
            model.addAttribute("model",model1);
            model.addAttribute("url", ConstantsContext.getWebUrl());
            return "/admin/orders/share_model.html";
        }else {
            RestAllOrder orderInfo = orderService.getOrderInfo(1, orderNum,false);
            model.addAttribute("order",orderInfo);
            if(type !=null && type==22){
                model.addAttribute("url", ConstantsContext.getWebUrl());
                return "/admin/orders/share_bj.html";
            }
            model.addAttribute("url", ConstantsContext.getWeChatKxUrl());
            return "/admin/orders/share.html";
        }
    }

}
