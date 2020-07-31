package cn.stylefeng.guns.yinhua.service;

import cn.stylefeng.guns.sys.modular.system.model.entity.SysMessage;
import cn.stylefeng.guns.yinhua.entity.yinhua.Express;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;

import java.util.List;

public interface SendMessageUtilService {
    void sendMessageForWe(String openId,String url, String care, List<WxMpTemplateData> data);
    void sendMessage(String order_num, Boolean isR, String menuId, SysMessage sysMessage, String m, String url,String name);
    void sendAddOrderMsg(Order order);
    void sendTiHuo(String order_num, String addr, String phone);
    void sendJH(String order_num, Express express);
    void autoSelectTeam(Order order,String px,OrderService orderService);
    void sendMessageToAccount(String account,String m,Order order,String name);
    /* 根据编号或者权限id推送*/
    void sendMessageByPx(String px, String orderNum,String name);
    /* 自定义推送*/
    void sendMessages(String account,String temp,Order order,String name);
    /* 推送根据角色id 或者直接id 推送*/
    void sendMessageByRoleIdOrUserId(Integer type,String[] ids,String temp,String orderNum,String name);
}
