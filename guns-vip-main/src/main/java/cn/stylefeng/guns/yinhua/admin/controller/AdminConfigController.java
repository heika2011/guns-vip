package cn.stylefeng.guns.yinhua.admin.controller;


import cn.stylefeng.guns.base.auth.context.LoginContext;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.jwt.JwtTokenUtil;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.MenuNode;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrder;
import cn.stylefeng.guns.yinhua.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 后台常见设置控制器
 */
@Controller
@RequestMapping("/WeChatMange")
public class AdminConfigController {

    @Autowired
    private UserService userService;

    @Autowired
    @Lazy
    private OrderService orderService;

    private String PREFIX = "/admin/config";

    @RequestMapping("")
    public String index(Model model){
        String weChatKxUrl = ConstantsContext.getWeChatKxUrl();
        String weChatClientId = ConstantsContext.getWeChatClientId();
        String weChatSerect = ConstantsContext.getWeChatSerect();
        Integer weChatAgentId = ConstantsContext.getWeChatAgentId();
        model.addAttribute("url",weChatKxUrl);
        model.addAttribute("id",weChatClientId);
        model.addAttribute("serect",weChatSerect);
        model.addAttribute("agentId",weChatAgentId);
        model.addAttribute("gzhId",ConstantsContext.getGZHID());
        model.addAttribute("gzhSecret",ConstantsContext.getGZHSecret());
        model.addAttribute("jumpUrl",ConstantsContext.getJumpUrl());
        return PREFIX + "/weChatConfig.html";
    }

    @RequestMapping("jump")
    public String jump(String orderNum, HttpServletResponse response, Model model){
        //Map<String, Object> userIndexInfo = userService.getUserIndexInfo();
        List<MenuNode> userMenu = userService.getUserMenu();
        RestOrder order = orderService.getOrder(orderNum);
        String webUrl = ConstantsContext.getWebUrl();
        String token = LoginContextHolder.getContext().getToken();
        Iterator<MenuNode> iterator = userMenu.iterator();
        while (iterator.hasNext()){
            MenuNode next = iterator.next();
            if(StringUtils.isEmpty(next.getIcon())) {
                iterator.remove();
            }
        }
        if(null != order){
            if(userMenu.size() == 1){
                String s = webUrl + "/#/?jumpOrderNum="+ orderNum +
                        "&type="+ userMenu.get(0).getIcon()+
                        "&token=" + token;
                try {
                    response.sendRedirect(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            model.addAttribute("user",userMenu);
        }else {
            model.addAttribute("user",null);
        }
        model.addAttribute("token",token);
        model.addAttribute("url", webUrl);
        model.addAttribute("orderNum",orderNum);
        return PREFIX + "/jump.html";
    }


    @RequestMapping("updateConfig")
    @ResponseBody
    public JSONResult updateConfig(@RequestParam(required = false) Map param){
        ConstantsContext.putConstant("WECHAT_KX_BACK",param.get("url"));
        ConstantsContext.putConstant("WECHAT_CLIENTID",param.get("id"));
        ConstantsContext.putConstant("WECHAT_SERECT",param.get("Serect"));
        ConstantsContext.putConstant("WECHAT_AGENTID",param.get("agentId"));
        ConstantsContext.putConstant("WECHAT_GZH_ID",param.get("gzhId"));
        ConstantsContext.putConstant("WECHAT_GZH_SECRET",param.get("gzhSecret"));
        ConstantsContext.putConstant("MOBILE_JUMPURL",param.get("jumpUrl"));
        return new JSONResult("0","OK");
    }

}
