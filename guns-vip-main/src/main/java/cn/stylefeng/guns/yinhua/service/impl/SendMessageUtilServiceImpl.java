package cn.stylefeng.guns.yinhua.service.impl;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysMessage;
import cn.stylefeng.guns.sys.modular.third.entity.WeiXinParamesUtil;
import cn.stylefeng.guns.sys.modular.third.service.SendMessageUtil;
import cn.stylefeng.guns.yinhua.admin.entity.Customer;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminCustomerMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.AdminUserParam;
import cn.stylefeng.guns.yinhua.entity.yinhua.Express;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.Model;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderProp;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.mobile.mapper.*;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.service.SendMessageUtilService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 微信消息推送类
 * @Date 2020/4/3
 * @Auth xiexin
 */
@Service
public class SendMessageUtilServiceImpl implements SendMessageUtilService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderPropMapper orderPropMapper;
    @Autowired
    private AdminCustomerMapper adminCustomerMapper;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private UserTeamMapper userTeamMapper;
    @Autowired
    private OrderTeamMapper orderTeamMapper;

    private static final SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 企业微信消息推送
     * @param order_num 生产单编号
     * @param isR   是否有message参数
     * @param menuId 菜单Id
     * @param sysMessage  message参数
     * @param m  内容
     * @param url  链接
     */
    @Override
    @Async("taskExecutor")
    public void sendMessage(String order_num, Boolean isR, String menuId, SysMessage sysMessage, String m, String url,String name) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order_num));
        if(order == null){
            throw new OutExcetion("订单不存在");
        }
        String temp = "";
        if(isR){
            List<AdminUserParam> strings = orderMapper.selectRoleIdByMenuId(menuId);
            url = ConstantsContext.getJumpUrl().replace("{ORDERNUM}",order_num).replace("{TYPE}","3");
            if(strings == null){
                return;
            }
            for(AdminUserParam a :strings){
                for(String s : a.getUserIds()){
                    if(temp.indexOf(s)>-1){
                        continue;
                    }else if(s.indexOf("oauth2")>-1){
                        s = s.split("oauth2_")[1];
                        temp = temp + "|"  + s;
                    }
                }
            }
        }else{
            String[] split = sysMessage.getToJob().split(",");
            for(int i =0 ;i<split.length;i++){
                List<String> strings = orderMapper.selectUserId(split[i]);
                if(strings == null){
                    return;
                }
                for(String s : strings){
                    if(s.indexOf("oauth2")>-1){
                        s = s.split("oauth2_")[1];
                        temp = temp + "|" + s;
                    }
                }
            }
        }
        if(temp!=null && temp !=""){
            temp = temp.substring(1,temp.length());
        }else{
            return;
        }
        sendMessageToAccount(temp,m,order,name);
    }

    /**
     * 向单个人推送消息
     */
    @Override
    @Async("taskExecutor")
    public void sendMessageToAccount(String account,String m,Order order,String name){
        if(account.indexOf("oauth2_")>-1){
            account = account.split("oauth2_")[1];
        }
        String modelNum = modelMapper.selectModelNumByNum(order.getModelId());
        String title = "订单"+m+":"+order.getModelName()+" "+(null==modelNum?"":modelNum)+" "+order.getAllcount()+order.getUnits()+" "+(order.getOrderProg() ==1?"打样":"大货");
        String desc = WeiXinParamesUtil.messages.replace("{name}",name).replace("{time}", simple.format(new Date()));
        SendMessageUtil.sendWeChatMsg(account,title,desc,ConstantsContext.getWeChatKxUrl()+"/oauth/render/wechat?url="+order.getOrderNum()+"&type=20");
    }
    /**
     * 公众号推送
     * @param openId
     * @param url
     * @param care
     * @param data
     */
    @Override
    @Async("taskExecutor")
    public void sendMessageForWe(String openId,String url, String care, List<WxMpTemplateData> data) {
        //2,推送消息
        WxMpTemplateMessage templateMessage = WxMpTemplateMessage.builder()
                .toUser(openId)//要推送的用户openid
                .templateId(care)//模版id
                .url(url)//点击模版消息要访问的网址
                .build();
        for(WxMpTemplateData wxMpTemplateData:data){
            templateMessage.addData(wxMpTemplateData);
        }
        try {
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        } catch (Exception e) {
            System.out.println("推送失败：" + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 下单推送
     * @param order
     */
    @Override
    @Async("taskExecutor")
    public void sendAddOrderMsg(Order order) {
        Customer customer = adminCustomerMapper.selectOne(new QueryWrapper<Customer>().eq("num", order.getCustomerName()));
        Model model = modelMapper.selectOne(new QueryWrapper<Model>().eq("num", order.getModelId()));
        if(customer == null) return;
        if(customer.getOpenId()==null || customer.getOpenId()=="") return;
        String[] split = customer.getOpenId().split(",");
        for(String s:split){
            List<WxMpTemplateData> wxMpTemplateData = new ArrayList<>();
            wxMpTemplateData.add(new WxMpTemplateData("first","您好，您的订单已下单。"));
            wxMpTemplateData.add(new WxMpTemplateData("keyword1",order.getModelId().toString()));
            wxMpTemplateData.add(new WxMpTemplateData("keyword2",simple.format(new Date())));
            wxMpTemplateData.add(new WxMpTemplateData("remark","款式名称:"+model.getName()+",款号:"+model.getModelNum()+",数量:"+order.getAllcount()+order.getUnits()));
            sendMessageForWe(s,ConstantsContext.getJumpUrl().replace("{ORDERNUM}",order.getOrderNum()).replace("{TYPE}","21"),WeiXinParamesUtil.addOrder,wxMpTemplateData);
        }
    }

    /**
     * 提货提醒
     * @param order_num
     * @param addr
     * @param phone
     */
    @Override
    @Async("taskExecutor")
    public void sendTiHuo(String order_num, String addr, String phone) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order_num));
        Customer customer = adminCustomerMapper.selectOne(new QueryWrapper<Customer>().eq("num", order.getCustomerName()));
        Model model = modelMapper.selectOne(new QueryWrapper<Model>().eq("num", order.getModelId()));
        if(customer == null) return;
        if(customer.getOpenId()==null || customer.getOpenId() == "") return;
        String[] split = customer.getOpenId().split(",");
        for(String s:split){
            List<WxMpTemplateData> wxMpTemplateData = new ArrayList<>();
            wxMpTemplateData.add(new WxMpTemplateData("first","您的订单已完成,请到提货点及时提货。"));
            wxMpTemplateData.add(new WxMpTemplateData("keyword1",addr));
            wxMpTemplateData.add(new WxMpTemplateData("keyword2",phone));
            wxMpTemplateData.add(new WxMpTemplateData("remark","款式名称:"+model.getName()+",款号:"+model.getModelNum()+",数量:"+order.getAllcount()+order.getUnits()));
            sendMessageForWe(s,ConstantsContext.getJumpUrl().replace("{ORDERNUM}",order_num).replace("{TYPE}","21"),WeiXinParamesUtil.tihuo,wxMpTemplateData);
        }
    }

    /**
     * 寄货推送
     * @param order_num
     * @param express
     */
    @Override
    @Async("taskExecutor")
    public void sendJH(String order_num, Express express) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order_num));
        Model model = modelMapper.selectOne(new QueryWrapper<Model>().eq("num", order.getModelId()));
        Customer customer = adminCustomerMapper.selectOne(new QueryWrapper<Customer>().eq("num", order.getCustomerName()));
        if(customer == null) return;
        if(customer.getOpenId()==null || customer.getOpenId() == "") return;
        String[] split = customer.getOpenId().split(",");
        for(String s:split){
            List<WxMpTemplateData> wxMpTemplateData = new ArrayList<>();
            wxMpTemplateData.add(new WxMpTemplateData("first","您的订单已发货，请关注。"));
            wxMpTemplateData.add(new WxMpTemplateData("keyword1",express.getExpressName()));
            wxMpTemplateData.add(new WxMpTemplateData("keyword2",express.getExpressNum()));
            wxMpTemplateData.add(new WxMpTemplateData("remark","款式名称:"+model.getName()+",款号:"+model.getModelNum()+",数量:"+order.getAllcount()+order.getUnits()));
            sendMessageForWe(s,ConstantsContext.getJumpUrl().replace("{ORDERNUM}",order_num).replace("{TYPE}","21"),WeiXinParamesUtil.fahuo,wxMpTemplateData);
        }
    }

    /**
     * 根据id获取角色推送
     * @param px
     * @param orderNum
     */
    @Override
    @Async("taskExecutor")
    public void sendMessageByPx(String px, String orderNum,String name) {
        /* 搜索对应的推送内容与id*/
        String text = orderPropMapper.selectText(px,orderNum);
        /* 如果为空也不操作*/
        if(StringUtils.isEmpty(text)){
            return;
        }
        String account = "";
        /* 如果有推送角色 */
        if(text.split("\\|")[0].indexOf("R")>-1){
            /* 取有关角色相关得 */
            String patten = "R[0-9]{0,30}";
            Pattern compile = Pattern.compile(patten);
            Matcher matcher = compile.matcher(text.split("\\|")[0]);
            /* 取推送内容 */
            while (matcher.find()){
                if(matcher.group().equals("R1234733485147570177")||matcher.group().equals("R1234733550264139778")){
                    Integer team = orderTeamMapper.findOrderForTeamId(orderNum);
                    List<String> strings = null;
                    /* 如果为空 则说明不是分派到小组*/
                    if(null == team || team == 0){
                        strings = orderTeamMapper.selectUserIdForOrderNum(orderNum);
                    }else{
                        /* 推送小组 */
                        strings = orderTeamMapper.selectAllUserTeamId(team);
                    }
                    if(strings == null){
                        return;
                    }
                    for(String s : strings){
                        String z = orderTeamMapper.selectAccountByUserId(s);
                        account = account + "|" + z;
                    }
                    account = account.replace("oauth2_","");
                }else {
                    List<String> strings = orderMapper.selectUserId(matcher.group().substring(1,matcher.group().length()));
                    if(strings == null){
                        return;
                    }
                    for(String s : strings){
                        if(s.indexOf("oauth2")>-1){
                            s = s.split("oauth2_")[1];
                            account = account + "|" + s;
                        }
                    }
                }
            }
        }
        if(text.split("\\|")[0].indexOf("M")>-1){
            /* 取有关角色相关得 */
            String patten = "M[0-9]{0,30}";
            Pattern compile = Pattern.compile(patten);
            Matcher matcher = compile.matcher(text.split("\\|")[0]);
            /* 先取出Id */
            while (matcher.find()){
                if(matcher.group().equals("M1234731218520522753")||matcher.group().equals("M1234731382404562946")){
                    Integer team = orderTeamMapper.findOrderForTeamId(orderNum);
                    List<String> strings = null;
                    /* 如果为空 则说明不是分派到小组*/
                    if(null == team || team == 0){
                        strings = orderTeamMapper.selectUserIdForOrderNum(orderNum);
                    }else{
                        /* 推送小组 */
                        strings = orderTeamMapper.selectAllUserTeamId(team);
                    }
                    if(strings == null){
                        return;
                    }
                    for(String s : strings){
                        String z = orderTeamMapper.selectAccountByUserId(s);
                        account = account + "|" + z;
                    }
                    account = account.replace("oauth2_","");
                }else if(matcher.group().equals("M1234729638731726850")){
                    Order orders = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderNum));
                    account =account +"|" + orderTeamMapper.selectAccountByUserId(orders.getNameId().toString());
                    account = account.replace("oauth2_","");
                }else {
                    List<AdminUserParam> adminUserParams = orderMapper.selectRoleIdByMenuId(matcher.group().substring(1,matcher.group().length()));
                    for (AdminUserParam a: adminUserParams){
                        if(a.getUserIds() == null){
                            return;
                        }
                        for(String s : a.getUserIds()){
                            if(s.indexOf("oauth2")>-1){
                                s = s.split("oauth2_")[1];
                                account = account + "|" + s;
                            }
                        }
                    }
                }
            }
        }
        if(account!=null && account !=""){
            if(account.startsWith("|")){
                account = account.substring(1,account.length());
            }
        }else{
            return;
        }
        text = text.split("\\|")[1];
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderNum));
        try{
            sendMessages(account,text,order,name);
        }catch (OutExcetion excetion){
            throw new OutExcetion("出现推送异常");
        }
    }

    /**
     * 根据角色id或者用户id
     */
    @Override
    @Async("taskExecutor")
    public void sendMessageByRoleIdOrUserId(Integer type,String[] ids,String text,String orderNum,String name) {
        /* 如果为角色推送 */
        String temp = "";
        if(type == 1){
            for(int i =0 ;i<ids.length;i++){
                List<AdminUserParam> adminUserParams = orderMapper.selectRoleIdByMenuId(ids[i]);
                for (AdminUserParam a: adminUserParams){
                    if(a.getUserIds() == null){
                        return;
                    }
                    for(String s : a.getUserIds()){
                        if(s.indexOf("oauth2")>-1){
                            s = s.split("oauth2_")[1];
                            temp = temp + "|" + s;
                        }
                    }
                }

            }/* 如果是用户直接推送 */
        }else if(type == 2){
            for(int i =0 ;i<ids.length;i++){
                List<String> strings = orderMapper.selectByUserId(ids[i]);
                if(strings == null){
                    return;
                }
                for(String s : strings){
                    if(s.indexOf("oauth2")>-1){
                        s = s.split("oauth2_")[1];
                        temp = temp + "|" + s;
                    }
                }
            }/* 如果是推送小组*/
        }/*else if(type == 3){
         *//* 推送小组 *//*
            Integer team = orderTeamMapper.findOrderForTeamId(orderNum);
            List<String> strings = null;
            *//* 如果为空 则说明不是分派到小组*//*
            if(null == team || team == 0){
                strings = orderTeamMapper.selectUserIdForOrderNum(orderNum);
            }else{
                *//* 推送小组 *//*
                strings = orderTeamMapper.selectAllUserTeamId(team);
            }
            if(strings == null){
                return;
            }
            for(String s : strings){
                String account = orderTeamMapper.selectAccountByUserId(s);
                temp = temp + "|" + account;
            }
            temp = temp.replace("oauth2_","");
        }*/
        if(temp!=null && temp !=""){
            temp = temp.substring(1,temp.length());
        }else{
            return;
        }
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderNum));
        sendMessages(temp,text,order,name);
    }
    /**
     * 新推送消息
     */
    @Override
    @Async("taskExecutor")
    public void sendMessages(String account,String temp,Order order,String name){
        if(account.indexOf("oauth2_")>-1){
            account = account.split("oauth2_")[1];
        }
        String modelNum = modelMapper.selectModelNumByNum(order.getModelId());
        temp =
                temp.replace("{MODELNUM}",modelNum==null?"":modelNum)
                        .replace("{NUM}",order.getModelId().toString())
                        .replace("{NAME}",order.getModelName())
                        .replace("{TYPE}",orderPropMapper.selectOrderType(order.getOrderType()))
                        .replace("{SUM}",order.getAllcount().toString())
                        .replace("{UNITS}",order.getUnits())
                        .replace("{USER}",name)
                        .replace("{TIME}",simple.format(new Date()))
                        .replace("{PROP}",orderProgress(order.getOrderProgress()));
        String[] split = temp.split("\\n");
        String desc = "";
        for(int i=1;i<split.length;i++){
            desc = desc + "<div class=\"normal\">"+split[i] + "</div>";
        }
        SendMessageUtil.sendWeChatMsg(account,split[0],desc,ConstantsContext.getWeChatKxUrl()+"/oauth/render/wechat?url="+order.getOrderNum()+"&type=20");
    }

    /**
     * 判断是哪个进度
     * @param progress
     * @return
     */
    private String orderProgress(Integer progress){
        switch (progress){
            case 0:
                return "未下单";
            case 1:
                return "开发";
            case 14:
                return "同款首样";
            case 2:
                return "首样";
            case 3:
                return "改版";
            case 4:
                return "翻样";
            case 5:
                return "产前样";
            case 6:
                return "首单";
            case 7:
                return "翻单";
            case 8:
                return "补片（打样）";
            case 9:
                return "补片（大货）";
            case 10:
                return "返工（打样）";
            case 11:
                return "返工（大货）";
            case 12:
                return "重做（打样）";
            case 13:
                return "重做（大货）";
            default:
                return "未配置";
        }
    }
    /**
     * 自动选择小组
     * @param order
     */
    @Override
    @Transactional
    @Async("taskExecutor")
    public void autoSelectTeam(Order order,String px,OrderService orderService) {
        /* 首先获取小组的类型是否是激光单 或者移模单*/
        Integer s = 0;
        if(userTeamMapper.selectOrderPropByName(order.getOrderProg(),order.getOrderType(),"激光单")!=null){
            s=1;
        }else if(userTeamMapper.selectOrderPropByName(order.getOrderProg(),order.getOrderType(),"移膜单")!=null){
            s=2;
        }
        if(s!=0){
            /* 查看有多少个激光组*/
            List<UserTeam> type = userTeamMapper.selectList(new QueryWrapper<UserTeam>().eq("type", s==1?3:4));
            /* 数量大于1取消自动 */
            if(type.size()>1){
                orderService.updateOrderPropNote(order.getOrderNum(),"检测到系统中存在多个"+(s==1?"激光组":"移模组")+"，取消自动分派，等待人工分派",0,new Date(),"U1");
                return;
            }
            if(type.size()<1){
                orderService.updateOrderPropNote(order.getOrderNum(),"检测到系统中没有"+(s==1?"激光组":"移模组")+"，调用正常生产分派",0,new Date(),"U1");
                return;
            }
            /* 如果是打样 并且在进度中有打样 则更新为完成 */
            OrderProp orderProp = new OrderProp();
            orderProp.setDoOver("1");
            orderProp.setName("平台系统");
            orderPropMapper.update(orderProp,new QueryWrapper<OrderProp>().eq("order_num",order.getOrderNum()).eq("type","1234730554360872962"));
            orderPropMapper.update(orderProp,new QueryWrapper<OrderProp>().eq("order_num",order.getOrderNum()).eq("type","1237267345014022146"));
            /* 创建订单小组对象*/
            OrderTeam orderTeam = new OrderTeam();
            /* 设置订单编号 */
            orderTeam.setOrderNum(order.getOrderNum());
            /* 设置小组编号 */
            orderTeam.setTeamId(type.get(0).getId());
            /* 保存 */
            orderService.changeTeam(null,orderTeam);
            /* 更新进度 */
            orderService.updateOrderPropNote(order.getOrderNum(),"系统自动分派完成，分派到小组:"+type.get(0).getName(),1,new Date(),"U1");
        }

    }
}

