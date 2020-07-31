package cn.stylefeng.guns.yinhua.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContext;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysMessage;
import cn.stylefeng.guns.sys.modular.system.model.entity.User;
import cn.stylefeng.guns.sys.modular.system.mapper.UserMapper;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.guns.yinhua.admin.service.PrintLogService;
import cn.stylefeng.guns.yinhua.entity.yinhua.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.Model;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelColor;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelInfo;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelNote;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.*;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeamU;
import cn.stylefeng.guns.yinhua.mobile.mapper.*;
import cn.stylefeng.guns.yinhua.service.ModelService;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.service.SendMessageUtilService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 订单业务类
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderMapper orderMapper; //生产单信息
    @Autowired
    private OrderNumMapper orderNumMapper; //生产单数量表
    @Autowired
    private OrderFromMapper orderFromMapper; //生产单对应编号
    @Autowired
    private OrderRoleMapper orderRoleMapper; //生产单流程
    @Autowired
    private OrderPropMapper orderPropMapper; //生产单进度
    @Autowired
    private ModelMapper modelMapper; //款式信息
    @Autowired
    private OrderPropNoteMapper orderPropNoteMapper; //生产单进度记录
    @Autowired
    private OrderSheetMapper orderSheetMapper; //生产单工艺单
    @Autowired
    private OrderTeamMapper orderTeamMapper;//生产单与小组关系
    @Autowired
    private UserTeamMapper userTeamMapper;  //小组
    @Autowired
    private UserTeamUMapper userTeamUMapper;  //小组
    @Autowired
    private ModelService modelService; //款式
    @Autowired
    private UserService userService;  //用户
    @Autowired
    private UserMapper userMapper; //用户
    @Autowired
    private OrderConstLogMapper orderConstLogMapper; //价格记录
    @Autowired
    private OrderUserMapper orderUserMapper; //价格与用户的关系表
    @Autowired
    private ModelInfoMapper modelInfoMapper; //款式信息
    @Autowired
    private OrderNumLogMapper orderNumLogMapper; //出库记录
    @Autowired
    private OrderRoleChildMapper orderRoleChildMapper; //出库记录
    @Autowired
    private SendMessageUtilService sendMessageUtilService; //消息推送
    @Autowired
    private PrintLogService printService; //打印业务
    /**
     * 下单
     * @param order
     */
    @Override
    @Transactional
    public synchronized Order addOrder(Order order,String px) {
        /* 编号不能为空 */
        if(order.getModelId()==null){
            throw new OutExcetion("请输入款式编号");
        }
        OrderRole orderRoleType = orderRoleMapper.selectByIdAndProp(order.getOrderType(),order.getOrderProgress());
        if(order.getOrderProgress()!=0 && orderRoleType == null){
            throw new OutExcetion("该进度类型尚未配置");
        }
        //设置当前订单大类型 如打样 大货
        /*if("1".equals(orderRoleType.getType())){
            order.setOrderProg(1);
            modelMapper.updateStatus("1",order.getModelId().toString());
        }else if("2".equals(orderRoleType.getType())){
            order.setOrderProg(2);
            modelMapper.updateStatus("3",order.getModelId().toString());
        }
        if(order.getOrderProgress()>=9&&order.getOrderProgress()<11){
            order.setOrderProg(3);
        }*/
        /* 判断是什么类型的订单 */
        Integer orderProgress = order.getOrderProgress();
        if(orderProgress>0 &&orderProgress<5 || orderProgress == 14){
            order.setOrderProg(1);
            modelMapper.updateStatus("1",order.getModelId().toString());
        }else if(orderProgress>=5&&orderProgress<8){
            order.setOrderProg(2);
            if(order.getOrderProgress() == 5){
                modelMapper.updateStatus("2",order.getModelId().toString());
            }else{
                modelMapper.updateStatus("3",order.getModelId().toString());
            }
        }else if(orderProgress == 9 ||orderProgress == 11 ||orderProgress == 13 ||  orderProgress == 8 || orderProgress== 10 || orderProgress== 12){
            order.setOrderProg(3);
            if(orderProgress==8||orderProgress==10||orderProgress==12){
                modelMapper.updateStatus("2",order.getModelId().toString());
            }else {
                modelMapper.updateStatus("3",order.getModelId().toString());
            }
        }
        //设置当前跟单人员名字
        order.setName(LoginContextHolder.getContext().getUser().getName());
        order.setNameId(LoginContextHolder.getContext().getUserId());
        //根据款式id获取当前生产单顺序
        QueryWrapper<OrderFrom> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("model_id",order.getModelId());
        OrderFrom orderFrom = orderFromMapper.selectOne(queryWrapper);
        //如果为空 说明是第一次产生 将款式id带入数据库 初始化0
        if(orderFrom==null){
            orderFrom = new OrderFrom();
            orderFrom.setModelId(order.getModelId());
            orderFrom.setOrderNum(0);
            orderFromMapper.insert(orderFrom);
        }
        //获取编号
        String orderNumText =  order.getModelId()+"-"+orderFrom.getOrderNum();
        //设置订单产生时间
        order.setCreatedTime(new Date()).setOrderNum(orderNumText);
        //设置当前订单的生产进度
        if(order.getOrderProgress()!=0){
            List<OrderRoleChild> parentId = orderRoleChildMapper.findAllRoleChild(orderRoleType.getId().toString());
            for(OrderRoleChild orderRole:parentId){
                //该款式已报价就不走报价进度
                if(orderRole.getType().equals("1234731728963125250")){
                    int i = orderConstLogMapper.selectCount(new QueryWrapper<OrderConstLog>().like("order_num",order.getModelId()));
                    if(i>0){
                        order.setStatus(3);
                        continue;
                    }
                }
                //将规则提出来
                OrderProp orderProp = new OrderProp();
                orderProp.setTypeName(orderRole.getTypeName())
                        .setType(orderRole.getType())
                        .setName(orderRole.getNum())
                        .setOrderRoleId(orderRole.getId())
                        .setOrderNum(orderNumText)
                        .setSx(orderRole.getName());
                orderPropMapper.insert(orderProp);
            }
        }
        //对数量表进行保存
        if(order.getOrderNums()!=null){
            if(order.getOrderNums().size()!=0){
                for(OrderNum orderNum:order.getOrderNums()){
                    orderNum.setOrderNum(orderNumText).setNum(order.getModelId());
                    orderNumMapper.insert(orderNum);
                }
            }
        }
        //对编号进行保存
        QueryWrapper<OrderFrom> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("model_id",orderFrom.getModelId());
        Integer s = orderFrom.getOrderNum() +1;
        orderFrom.setOrderNum(s);
        orderFromMapper.update(orderFrom,queryWrapper);
        Model num = modelMapper.selectOne(new QueryWrapper<Model>().eq("num", order.getModelId()));
        //order.setCustomerName();
        order.setCustomerName(num.getCustomer()).setModelName(num.getName());
        OrderTeam orderTeam = new OrderTeam().setOrderNum(orderNumText);
        /* 保存小组 */
        orderTeamMapper.insert(orderTeam);
        /* 查看改订单下是否有工艺单 */
        List<OrderSheet> orderSheet = getOrderSheet(order.getOrderNum());
        if(orderSheet.size()>0){
            /* 有则给标志位 */
            order.setHavSheet(1);
        }
        /* 查看该款式是否报价 */
        Double consts = orderConstLogMapper.selectNewConst(order.getModelId().toString());
        /* 如果报价不为Null 则设置到订单信息种 */
        if(consts!=null){
            order.setLastConst(consts);
        }
        /* 保存订单 */
        orderMapper.insert(order);
        /* 更新进度 */
        //sendMessageUtilService.autoSelectTeam(order,px,this);
        sendMessageUtilService.sendAddOrderMsg(order);
        return order;
    }

    /**
     * 制版检查是否有未填写大小的裁片
     * @param orderNum
     */
    @Override
    public void checkAllModelInfoForPlace(String orderNum) {
        /* 获取当前的裁片信息 */
        List<ModelCut> modelInfo = modelService.getModelInfo(Long.valueOf(orderNum.split("-")[0]));
        /* 遍历循环查看是否完善了裁片大小*/
        for(ModelCut modelCut:modelInfo){
            if(modelCut.getModelInfo().getSizes() == null || modelCut.getModelInfo().getSizes().equals("")){
                throw new OutExcetion("请检查裁片大小是否完善！");
            }
        }
    }

    /**
     * 丝印调色检查订单的工艺单是否上传
     * @param orderNum
     */
    @Override
    public void checkOrderSheet(String orderNum) {
        /* 根据订单号获取工艺单信息 */
        List<OrderSheet> orderSheet = getOrderSheet(orderNum);
        if(orderSheet.size() == 0){
            throw new OutExcetion("请检查工艺单是否上传");
        }
    }

    /**
     * 获取上次报价
     * @param orderNum
     * @return
     */
    @Override
    public Map getLastCount(String orderNum) {
        /* 获取该款式上次报价的信息 */
        Map s = orderConstLogMapper.selectLastCountByNum(orderNum.split("-")[0]);
        return s;
    }

    /**
     * 获取上传的图片
     * @param orderNum
     * @return
     */
    @Override
    public String getOrderSheetForCW(String orderNum) {
        OrderPropNote orderPropNote = orderPropNoteMapper.selectOne(new QueryWrapper<OrderPropNote>().eq("order_num", orderNum).eq("user_name", "U" + LoginContextHolder.getContext().getUserId()).like("text","|金额"));
        if(orderPropNote == null){
            return null;
        }
        if(orderPropNote.getText().indexOf("|金额")>-1){
            return orderPropNote.getText();
        }
        return null;
    }

    /**
     * 修改上传的图片
     * @param orderNum
     * @param text
     */
    @Override
    @Transactional
    public void updateSheetForCW(String orderNum, String text) {
        OrderPropNote orderPropNote = new OrderPropNote();
        orderPropNote.setText(text);
        int update = orderPropNoteMapper.update(orderPropNote, new QueryWrapper<OrderPropNote>().eq("order_num", orderNum).eq("user_name", "U" + LoginContextHolder.getContext().getUserId()).like("text","|金额"));
        if(update == 0){
            throw new OutExcetion("修改失败");
        }
    }

    /**
     * 消息推送
     */
    @Override
    public void sendMessageForMsg(String px,String orderNum) {
        /* 根据菜单id 和 订单编号获取推送id 或者角色*/
        sendMessageUtilService.sendMessageByPx(px,orderNum,LoginContextHolder.getContext().getUser().getName());
    }

    /**
     * 获取小组今天的数据
     * @return
     */
    @Override
    public List<RestOrderNumCount> getTodayCount() {
        /* 根据id查找当前用户的小组信息*/
        String teamId = userTeamMapper.selectTeamId(LoginContextHolder.getContext().getUserId().toString());
        List<RestOrderNumCount> restOrderNumCounts = orderMapper.selectOrderNumCount(teamId);
        return restOrderNumCounts;
    }

    /**
     * 获取小姐其他时间数据
     * @param startTime
     * @param overTime
     * @return
     */
    @Override
    public List<RestOrderNumCount> getOtherTodayCount(Date startTime, Date overTime) {
        /* 根据id获取小组的信息*/
        String teamId = userTeamMapper.selectTeamId(LoginContextHolder.getContext().getUserId().toString());
        List<RestOrderNumCount> restOrderNumCounts = orderMapper.selectOrderNumCountByTime(teamId, startTime, overTime);
        return restOrderNumCounts;
    }

    /**
     * 数据统计
     * @param startTime
     * @param overTime
     * @return
     */
    @Override
    public String getOrderMoney(Date startTime, Date overTime) {
        List<UserTeamU> user = userTeamUMapper.selectList(new QueryWrapper<UserTeamU>().eq("user_id", LoginContextHolder.getContext().getUserId()));
        String teamId = userTeamMapper.selectTeamId(LoginContextHolder.getContext().getUserId().toString());
        /* 取小组的统计数据 */
        List<String> orderConstByTime = userTeamMapper.findOrderConstByTime(teamId, startTime, overTime);
        /* 获取该成员的岗位信息 */
        String orderTypeForUserId = userTeamMapper.findOrderTypeForUserId(startTime, overTime,teamId, LoginContextHolder.getContext().getUserId().toString());
/*        if(orderConstByTime == null
                || orderConstByTime.size() == 0
                || orderTypeForUserId==null){
        }*/
        double v = 0.0;
        double c = 0.0;
        Double temp = 0.0;
        for(UserTeamU u:user){
            for(String p:orderConstByTime){
                c = Double.parseDouble(p.split(",")[1]);
                if(orderTypeForUserId!=null && orderTypeForUserId.indexOf("3")>-1){
                    v = Math.round(c * ConstantsContext.getDouble("type9")/100);
                }else{
                    v = Math.round(c * ConstantsContext.getDouble("type10")/100);
                }
                temp = temp + v;
                if(u.getJob() == 1 || u.getJob() == 2){
                    v = Math.round(c * ConstantsContext.getDouble("type8")/100);
                    temp = temp + v;
                }
            }
        }
        return temp.toString();
    }

    /**
     * 订单信息修改
     * @param order
     */
    @Override
    @Transactional
    public void updateOrder(Order order) {
        Order order_num = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order.getOrderNum()));
        order.setId(order_num.getId());
        if(order_num.getOrderProgress() != 0){
            order.setOrderProgress(order_num.getOrderProgress());
            order.setId(order_num.getId());
            orderMapper.updateById(order);
            /*throw new OutExcetion("进度仅支持修改未下单");*/
        }
        if(order_num.getOrderProgress() == 0){
            if(order.getOrderProgress() ==0){
                order.setOrderProgress(order_num.getOrderProgress());
                order.setId(order_num.getId());
                orderMapper.updateById(order);
                return;
            }
            /* 删除订单进度 */
            orderPropMapper.delete(new QueryWrapper<OrderProp>().eq("order_num",order.getOrderNum()));
            orderPropNoteMapper.delete(new QueryWrapper<OrderPropNote>().eq("order_num",order.getOrderNum()));
            /* 重新写入 */
            OrderRole orderRoleType = orderRoleMapper.selectByIdAndProp(order_num.getOrderType(),order.getOrderProgress());
            if(orderRoleType == null){
                throw new OutExcetion("该进度类型尚未配置");
            }
            //设置当前订单的生产进度
            List<OrderRoleChild> parentId = orderRoleChildMapper.findAllRoleChild(orderRoleType.getId().toString());
            for(OrderRoleChild orderRole:parentId) {
                //该款式已报价就不走报价进度
                if (orderRole.getType().equals("1234731728963125250")) {
                    int i = orderConstLogMapper.selectCount(new QueryWrapper<OrderConstLog>().like("order_num", order.getModelId()));
                    if (i > 0) {
                        order.setStatus(3);
                        continue;
                    }
                }
                //将规则提出来
                OrderProp orderProp = new OrderProp();
                orderProp.setTypeName(orderRole.getTypeName())
                        .setType(orderRole.getType())
                        .setName(orderRole.getNum())
                        .setOrderNum(order.getOrderNum())
                        .setSx(orderRole.getName());
                orderPropMapper.insert(orderProp);
            }

            orderMapper.updateById(order);
        }
    }

    /**
     * 获取所有的订单类型
     * @return
     */
    @Override
    public List<RestOrderRole> getOrderType() {
        QueryWrapper<OrderRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isname",1).groupBy("name");
        List<OrderRole> orderRoles = orderRoleMapper.selectList(queryWrapper);
        List<RestOrderRole> restOrderRoles = new ArrayList<>();
        for(OrderRole orderRole:orderRoles){
            RestOrderRole restOrderRole = new RestOrderRole();
            restOrderRole.setName(orderRole.getName()).setId(orderRole.getId());
            restOrderRoles.add(restOrderRole);
        }
        return restOrderRoles;
    }

    /**
     * 根据款式删除生产单
     * @param orderNum
     */
    @Override
    @Transactional
    public void deleteOrder(String orderNum) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_num",orderNum);
        /* 删除生产单信息 */
        orderMapper.delete(queryWrapper);
        /* 删除数量表 */
        orderNumMapper.delete(queryWrapper);
        /* 删除出货日志*/
        orderNumLogMapper.delete(queryWrapper);
        /* 删除进度信息 */
        orderPropMapper.delete(queryWrapper);
        /* 删除进度日志 */
        orderPropNoteMapper.delete(queryWrapper);
        /* 删除工艺单数据 */
        orderSheetMapper.delete(queryWrapper);
        /* 删除生产单与生产组的关联*/
        orderTeamMapper.delete(queryWrapper);
        /* 删除生产单与用户的关林 */
        orderUserMapper.delete(queryWrapper);
        /* 删除生产单的排序信息 */
        orderFromMapper.delete(queryWrapper);
        /* 删除生产单的报价信息 */
        orderConstLogMapper.delete(queryWrapper);
    }

    /**
     * 直接删除生产单
     * @param num
     */
    @Override
    public void deleteAllOrder(Long num) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.like("order_num",num);
        /* 删除生产单数据 */
        orderMapper.delete(queryWrapper);
        orderNumMapper.delete(queryWrapper);
        orderNumLogMapper.delete(queryWrapper);
        orderPropMapper.delete(queryWrapper);
        orderPropNoteMapper.delete(queryWrapper);
        orderSheetMapper.delete(queryWrapper);
        orderTeamMapper.delete(queryWrapper);
        orderUserMapper.delete(queryWrapper);
        orderFromMapper.delete(queryWrapper);
    }

    /**
     * 获取订单类型
     * @return
     */
    @Override
    public List<OrderRole> getOrderRole() {
        List<OrderRole> orderRoles = orderRoleMapper.selectList(new QueryWrapper<OrderRole>().isNull("type"));
        return orderRoles;
    }

    /**
     * 获取数量表信息
     * @param num
     * @return
     */
    @Override
    public List<OrderNum> getOrderNum(String num) {
        List<OrderNum> orderNums = orderNumMapper.selectList(new QueryWrapper<OrderNum>().eq("order_num", num));
        return orderNums;
    }

    /**
     * 获取订单数量表
     * @param order_num
     * @return
     */
    @Override
    public List<OrderNum> getOrderNumList(String order_num) {
        List<OrderNum> orderNums = orderNumMapper.selectList(new QueryWrapper<OrderNum>().eq("order_num", order_num));
        return orderNums;
    }

    /**
     * 获取订单总数
     * @param order_num
     * @return
     */
    @Override
    public String getOrderAllCount(String order_num) {
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order_num));
        return order.getAllcount().toString();
    }

    /**
     * 获取单个订单详情
     * @return
     */
    @Override
    public RestOrder getOrder(String num) {
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_num",num);
        Order order = orderMapper.selectOne(queryWrapper);
        return order;
    }

    /**
     * 返回数据汇总
     * @return
     */
    @Override
    public RestOrderCount getDataCount(Integer type,String px) {
        if(type == 1){
            //跟单
            RestOrderCount createData = orderMapper.getCreateData(px,LoginContextHolder.getContext().getUserId());
            return createData;
        }
        if(type == 2){
            //制版
            RestOrderCount pmData = orderMapper.getPMData(LoginContextHolder.getContext().getUserId().toString(),px);
            return pmData;
        }
        if(type == 3){
            //晒版
            RestOrderCount pdData = orderMapper.getPDData(px);
            return pdData;
        }
        if(type == 4){
            //丝印
            //获取数据统计
            RestOrderCount pdData = orderMapper.getSPata(LoginContextHolder.getContext().getUserId().toString(),px);
            //构建查询器
            List<String> strings = orderMapper.selectMyOrderForSP("1", px);
            pdData.setTypeF(String.valueOf(strings.size()));
            strings = orderMapper.selectMyOrderForSP("2", px);
            pdData.setTypeH(String.valueOf(strings.size()));
            return pdData;
        }
        if(type==5){
            //数码
            RestOrderCount pdData = orderMapper.getDCDate(LoginContextHolder.getContext().getUserId().toString(),px);
            List<Order> order_prog = orderMapper.selectList(new QueryWrapper<Order>().eq("order_prog", 1).eq("overFlag",0));
            QueryWrapper<OrderProp> queryWrapper = new QueryWrapper<>();
            for(Order order:order_prog){
                queryWrapper.or().eq("order_num",order.getOrderNum()).eq("do_over", "0").eq("type", px);
            }
            if(order_prog.size() != 0) {
                Integer integer = orderPropMapper.selectCount(queryWrapper);
                pdData.setTypeC(integer.toString());
            }else {
                pdData.setTypeC("0");
            }
            return pdData;
        }
        if(type == 6){
            //分配 打样
            RestOrderCount restOrderCount = new RestOrderCount();
            //查找未分组
            List<String> strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(),px, "0");
            restOrderCount.setTypeA(String.valueOf(strings.size()));
            //查找已分组
            strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(),px, "1");
            restOrderCount.setTypeB(String.valueOf(strings.size()));
            //查找未完成的打样数
            strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(),"1234730790143672321", "0");
            restOrderCount.setTypeC(String.valueOf(strings.size()));
            //查找未完成的裁片数
            QueryWrapper<ModelInfo> queryWrapper1 = new QueryWrapper<>();
            for(String orderProp:strings){
                queryWrapper1.or().eq("num",orderProp.split("-")[0]);
            }
            if(strings.size() != 0){
                restOrderCount.setTypeD(modelInfoMapper.selectCount(queryWrapper1).toString());
            }else {
                restOrderCount.setTypeD("0");
            }
            return restOrderCount;
        }
        if(type==7){
            //打样
            List<String> strings = orderMapper.selectForDY(LoginContextHolder.getContext().getUserId().toString(),px,"0");
            RestOrderCount restOrderCount = orderMapper.getPCDate(LoginContextHolder.getContext().getUserId().toString(),px);
            Integer integer = strings.size();
            restOrderCount.setTypeA(String.valueOf(integer));
            int i = 0;
            for(String s:strings){
                if(orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",s).eq("type","1234729858706194434").eq("do_over","1"))==null
                        ||orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",s).eq("type","1234730078953291778").eq("do_over","1"))==null
                        ||orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",s).apply("((type = 1234730294012035073 and do_over = 1) or (type = 1234733172114079747) and do_over =1 )"))==null){
                    i++;
                }
            }
            restOrderCount.setTypeB(String.valueOf(integer-i));
            restOrderCount.setTypeC(String.valueOf(i));
            return restOrderCount;
        }
        if(type == 8){
            //分配订单（生产）
            RestOrderCount pdcDate = orderMapper.getPDCDate(LoginContextHolder.getContext().getUserId().toString(),px);
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            List<String> strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(), px, "0");
            for(String orderProp:strings){
                queryWrapper.or().eq("order_num",orderProp);
            }
            List<Order> orders = orderMapper.selectList(queryWrapper.eq("realy_count",0));
            Double s = 0.0;
            Double i = 0.0;
            for(Order order:orders){
                if (order.getUnits().equals("件")){
                    s = s +order.getAllcount();
                }else if(order.getUnits().equals("SF")){
                    i = i +order.getAllcount();
                }
            }
            pdcDate.setTypeD(String.format("%.2f",s));
            pdcDate.setTypeE(String.format("%.2f",i));
            return pdcDate;
        }
        if(type == 9){
            //组长的总数
            RestOrderCount restOrderCount = orderMapper.selectTeamLearData(LoginContextHolder.getContext().getUserId().toString());
            //获取自己小组的订单的总数
            List<String> strings = orderMapper.selectMyOrderByUserId(LoginContextHolder.getContext().getUserId().toString(), px);
            if(null == strings && strings.size() == 0){
                throw new OutExcetion("您未加入小组");
            }
            //如果没有即为0的总数
            Integer integer = 0;
            QueryWrapper<Order> orderzz = new QueryWrapper<>();
            if(strings == null || strings.size() ==0){
                restOrderCount.setTypeA("0");
                restOrderCount.setTypeD("0");
                restOrderCount.setTypeB("0");
                restOrderCount.setTypeC("0");
            }else{
                //获取自己小组没有完成的单子的总数
                //获取全部没有完成的单的总数
                restOrderCount.setTypeA(String.valueOf(strings.size()));
                //催单总数
                integer = orderMapper.selectCount(orderzz.in("order_num",strings).gt("urag", 0));
                restOrderCount.setTypeD(integer.toString());
                //裁片已到单数
                orderzz = new QueryWrapper<>();
                integer = orderMapper.selectCount(orderzz.in("order_num",strings).eq("order_progress", 51));
                restOrderCount.setTypeC(integer.toString());
                //未统计
                strings = orderMapper.selectMyOrderByUserIdForUn(LoginContextHolder.getContext().getUserId().toString(), px);
                orderzz = new QueryWrapper<>();
                integer = orderMapper.selectCount(orderzz.in("order_num",strings).eq("over_fp", 0));
                restOrderCount.setTypeB(integer.toString());
            }
            //未生产单数
            restOrderCount.setTypeE(restOrderCount.getTypeF());
            //TODO 当天完成单？
            //今天完成单
            /*List<OrderProp> orderProps = orderPropMapper.selectList(new QueryWrapper<OrderProp>().eq("type", px).eq("do_over", 1));
            QueryWrapper<Order> queryWrapper = null;
            if(orderProps==null || orderProps.size() == 0){
                restOrderCount.setTypeE("0");
                return restOrderCount;
            }else{
                queryWrapper = new QueryWrapper<>();
                for(OrderProp orderProp:orderProps){
                    queryWrapper.or().eq("order_num",orderProp.getOrderNum());
                }
            }
            integer = orderMapper.selectTodayCount(queryWrapper);
            restOrderCount.setTypeE(integer.toString());*/
            return restOrderCount;
        }
        if(type==10){
            /* 报价 */
            RestOrderCount bjDate = orderMapper.getBJDate(px);
            return bjDate;
        }
        if(type==11){
            /* 财务 */
            RestOrderCount cwDate = orderMapper.getCWDate(px);
            return cwDate;
        }
        if(type== 21){
            /* 总检 */
            RestOrderCount restOrderCount = orderMapper.selectZJDataForMsg();
            return restOrderCount;
        }
        if(type== 22){
            /* 司机 */
            RestOrderCount restOrderCount = orderMapper.selectDriver(px);
            return restOrderCount;
        }
        return null;
    }

    /**
     * 获取组员是否确认
     * @param order_num
     * @return
     */
    @Override
    public List<OrderTeam> getMemberJoinInfo(String order_num) {
        List<OrderTeam> list = orderTeamMapper.selectList(new QueryWrapper<OrderTeam>().eq("order_num", order_num).eq("user_id", LoginContextHolder.getContext().getUserId()));
        return list;
    }

    /**
     * 获取进度日志
     * @param orderNum
     * @return
     */
    @Override
    public List<OrderPropNote> getOrderPropNote(String orderNum) {
        List<OrderPropNote> orderPropNotes = orderPropNoteMapper.selectList(new QueryWrapper<OrderPropNote>().eq("order_num", orderNum));
        return orderPropNotes;
    }

    /**
     * 获取报价单
     * @param orderNum
     */
    @Override
    public RestOrderConst getModelMuchs(String orderNum) {
        String num = orderNum.split("-")[0];
        RestOrderConst restOrderConst = modelMapper.findModelMuchs(num);
        if(restOrderConst == null){
            throw new OutExcetion("该订单不存在报价信息");
        }
        restOrderConst.setConstHistory(modelMapper.findModelMuchsLog(num));
        restOrderConst.setModelInfos(modelService.getModelInfo(Long.valueOf(num)));
        restOrderConst.setModel(modelService.getModel(Long.valueOf(num)));
        return restOrderConst;
    }

    /**
     * 出货记录
     * @param order_num
     * @return
     */
    @Override
    public List<OrderNumLog> getOutForOrder(String order_num) {
        List<OrderNumLog> orderNumLogs = orderNumLogMapper.selectList(new QueryWrapper<OrderNumLog>().eq("order_num", order_num));
        return orderNumLogs;
    }

    /**
     * 财务设置订单状态
     * @param type
     */
    @Override
    @Transactional
    public void setOrderStatus(Integer type,String order_num,String px,Double money,String num,String url) {
        if(type == 4){
            if(money == null || money == 0.0){
                throw new OutExcetion("金额参数异常");
            }
            if(StringUtils.isEmpty(num)){
                throw new OutExcetion("单号参数异常");
            }
            OrderSheet orderSheet = new OrderSheet();
            if(!StringUtils.isEmpty(url)){
                orderSheet.setOrderNum(order_num);
                orderSheet.setUrl(url);
                orderSheetMapper.insert(orderSheet);
            }
            /* 保存财务设置的订单信息 */
            saveOrderConst(order_num,money,num);
        }
        /* 获取当前订单是否是该状态 */
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order_num));
        if(order!=null){
            if(order.getStatus().equals(type)){
                throw new OutExcetion("该状态已是当前订单状态");
            }
        }
        OrderProp orderProp = new OrderProp();
        if(type == 6){
            orderProp.setDoOver("0");
        }else {
            orderProp.setDoOver("1");
        }
        orderProp.setName(LoginContextHolder.getContext().getUser().getName()).setSx("U"+LoginContextHolder.getContext().getUserId());
        /* 获取款式日志 */
        OrderPropNote orderPropNote = new OrderPropNote();
        orderPropNote.setUserName("U"+LoginContextHolder.getContext().getUserId()).setOverTime(new Date()).setOrderNum(order_num);
        if(type == 4){
            orderPropNote.setText("完成订单");
            orderPropNoteMapper.insert(orderPropNote);
            /* 查看是否有销售单 */
            String text = orderMapper.getOrderSheet(order_num);
            orderPropNote.setText((text==null?"":text) +"|"+"金额:"+money);
        }else if(type == 5){
            orderPropNote.setText("不开单");
        }else if(type == 6){
            orderPropNote.setText("暂不开单");
        }
        orderPropMapper.update(orderProp,new QueryWrapper<OrderProp>().eq("order_num",orderPropNote.getOrderNum()).eq("type",px));
        orderPropNoteMapper.insert(orderPropNote);
        orderMapper.setOverFlag(type,order_num);
    }
    /**
     * 储存财务设置的订单价格
     */
    @Async("taskExecutor")
    @Transactional
    public void saveOrderConst(String orderNum,Double money,String num){
        //保存订单的财务信息
        orderConstLogMapper.saveFaConst(money,num,orderNum);
        /* 搜索订单是打样则不计算*/
        Order order_num = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderNum));
        if(order_num.getOrderProg()==1){
            return;
        }
        //查询订单的小组Id
        OrderTeam orderTeam = orderTeamMapper.selectOne(new QueryWrapper<OrderTeam>().eq("order_num", orderNum).isNotNull("team_id").groupBy("team_id"));
        if(null == orderTeam){
            return ;
        }
        //取出当天的基数
        String s1 = userTeamMapper.findOrderConstByOrderNum(orderTeam.getTeamId(), orderNum);
        if(s1==null){
            /*throw new OutExcetion("订单生产尚未完成！");*/
            return ;
        }
        Double v = Double.valueOf(s1.split(",")[0]);
        v = v + money;
        String s  = "";
        double i = 0;
        if(v > ConstantsContext.getDouble("type6")){
            i = Math.round((ConstantsContext.getDouble("type2") * (ConstantsContext.getDouble("type3")/100))
                    + Math.round(ConstantsContext.getDouble("type4")) * (ConstantsContext.getDouble("type5")/100)
                    + Math.round((v- ConstantsContext.getDouble("type6")) *ConstantsContext.getDouble("type7")/100)
            );
            i = Math.round(i*100)/100;
            s = v + "," +i;
            userTeamMapper.updateOrderTeamLog(s,orderTeam.getTeamId(),orderNum);
            return;
        }
        if(v > ConstantsContext.getDouble("type4") && v < ConstantsContext.getDouble("type6")){
            i = Math.round((ConstantsContext.getDouble("type2") * (ConstantsContext.getDouble("type3")/100))
                    + Math.round((v - ConstantsContext.getDouble("type4")) * (ConstantsContext.getDouble("type5")/100))
            );
            i = Math.round(i*100)/100;
            s = v + "," +i;
            userTeamMapper.updateOrderTeamLog(s,orderTeam.getTeamId(),orderNum);
            return;
        }
        if(v > ConstantsContext.getDouble("type2") && v < ConstantsContext.getDouble("type4")){
            //计算出提额部分
            i = Math.round((v - ConstantsContext.getDouble("type2")) * (ConstantsContext.getDouble("type3")/100));
            i = Math.round(i*100)/100;
            s = v +"," +i;
            userTeamMapper.updateOrderTeamLog(s,orderTeam.getTeamId(),orderNum);
            return;
        }
        s = v + ","+i;
        userTeamMapper.updateOrderTeamLog(s,orderTeam.getTeamId(),orderNum);
    }

    /**
     * 获取财务所设置的金额
     * @param orderNum
     * @return
     */
    @Override
    public FaConst getOrderConstByF(String orderNum) {
        /* 获取设置的金额信息 */
        FaConst orderFaConst = orderConstLogMapper.getOrderFaConst(orderNum);
        return orderFaConst;
    }

    /**
     * 设置订单完成
     * @param order_num
     */
    @Override
    @Transactional
    public void overOrder(String order_num) {
        List<OrderProp> orderProps = orderPropMapper.selectList(new QueryWrapper<OrderProp>().eq("order_num", order_num).eq("do_over", 0).ne("type","1234729638731726850"));
        if(orderProps.size()>0){
            throw new OutExcetion("该订单流转尚未结束");
        }
        /* 设置裁片为老版 */
        modelInfoMapper.updateScreenModel(order_num.split("-")[0]);
        OrderProp orderProp = new OrderProp();
        orderProp.setDoOver("1").setOrderNum(order_num).setName(LoginContextHolder.getContext().getUser().getName());
        orderPropMapper.update(orderProp,new QueryWrapper<OrderProp>().eq("order_num",order_num).eq("type","1234729638731726850"));
        updateOrderPropNote(order_num,"订单完成",1,new Date(),"U"+LoginContextHolder.getContext().getUserId());
        orderTeamMapper.delete(new QueryWrapper<OrderTeam>().eq("order_num",order_num).isNull("team_id"));
        orderMapper.overOrder(order_num);
    }

    /**
     * 财务设置赔偿
     * @param orderConstLog
     */
    @Override
    public void setOrderMakeUp(OrderConstLog orderConstLog) {
        ModelNote modelNote =new ModelNote();
        modelNote.setText(orderConstLog.getOrderNum()+"赔偿金额："+orderConstLog.getMakeUp());
        modelNote.setCreatedTime(new Date());
        modelNote.setName(LoginContextHolder.getContext().getUser().getName());
        modelNote.setNum(orderConstLog.getOrderNum().split("-")[0]);
        modelService.addModelNote(modelNote);
        int back = orderConstLogMapper.update(orderConstLog, new QueryWrapper<OrderConstLog>().eq("order_num", orderConstLog.getOrderNum()));
        if(back == 0){
            orderConstLog.setCreatedTime(new Date());
            orderConstLogMapper.insert(orderConstLog);
        }
    }
    /**
     * 催单业务
     * @param order_num
     */
    @Override
    public void uragOrder(String order_num) {
        Order order_num1 = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order_num));
        order_num1.setUrag(order_num1.getUrag()+1);
        orderMapper.updateById(order_num1);
        List<String> strings = orderMapper.uragForPeople(order_num);
        for(String s:strings){
            sendMessageUtilService.sendMessage(order_num,true,s,null,"催单通知",ConstantsContext.getJumpUrl().replace("{ORDERNUM}",order_num).replace("{TYPE}","20"),LoginContextHolder.getContext().getUser().getName());
        }
    }

    /**
     * 订单复制
     * @param order_num
     */
    @Override
    public void copyOrder(String order_num) {
        //搜索订单的信息
        Order order_num1 = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order_num));
        //设置编号为空
        order_num1.setOrderNum("");
        //
        addOrder(order_num1,"1234729638731726850");
    }

    /**
     * 获取款式日志
     * @param order_num
     * @return
     */
    @Override
    public List<ModelNote> getOrderNote(String order_num) {
        List<ModelNote> modelNote = modelService.getModelNote(Long.valueOf(order_num.split("-")[0]));
        return modelNote;
    }

    /**
     * 生产单异常添加
     * @param order_num
     */
    @Override
    public void errorOrder(String order_num,Integer errorCode,String px,String text) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("order_num", order_num);
        Order order_num1 = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", order_num));
        if(order_num1.getOrderProgress() >=8 && order_num1.getOrderProgress() <=10){
            throw new OutExcetion("该订单已经是异常订单，无法重复添加。");
        }
        Order order = orderMapper.selectOne(queryWrapper);
        List list = orderNumMapper.selectList(queryWrapper);
        order_num1.setError(errorCode);
        orderMapper.updateById(order_num1);
        order.setOrderNums(list);
        ModelNote modelNote = new ModelNote();
        modelNote.setName(LoginContextHolder.getContext().getUser().getName())
                .setCreatedTime(new Date()).setNum(order_num.split("-")[0]);
        if(errorCode == 1){
            order.setOrderProgress(8);
            modelNote.setText("补片理由:"+text);
        }else
        if(errorCode == 2){
            order.setOrderProgress(9);
            modelNote.setText("返工理由:"+text);
        }else
        if(errorCode == 3){
            order.setOrderProgress(10);
            modelNote.setText("重做理由:"+text);
        }
        modelService.addModelNote(modelNote);
        order.setUrag(0);
        addOrder(order,px);
    }

    /**
     * 修改订单进度
     */
    @Override
    public void changeOrderStatus(Order order) {
        orderMapper.update(order,new QueryWrapper<Order>().eq("order_num",order.getOrderNum()));
    }

    /**
     * 生产单转让
     * @param order_num
     * @param user_id
     */
    @Override
    public void changeOrderCreate(String order_num, Long user_id) {
        Map<String, Object> userInfo = userService.getUserInfo(user_id);
        Order order = new Order();
        order.setNameId(user_id);
        order.setName((String)userInfo.get("name"));
        orderMapper.update(order,new QueryWrapper<Order>().eq("order_num",order_num));
    }

    /**
     * 获取所有跟单员
     * @return
     */
    @Override
    public List<Map<String, String>> getCreater() {
        List<User> role_id = userMapper.selectList(new QueryWrapper<User>().like("role_id", "1234732887962566658"));
        List<Map<String, String>> l = new ArrayList<>();
        for(User u :role_id){
            if(!LoginContextHolder.getContext().getUser().getName().equals(u.getName())){
                Map<String,String> m = new HashMap<>();
                m.put("name",u.getName());
                m.put("user_id",u.getUserId().toString());
                l.add(m);
            }
        }
        return l;
    }

    /**
     * 请求报价微信推送
     * @param order_num
     */
    @Override
    public void sendOrderOffer(String order_num) {
        sendMessageUtilService.sendMessage(order_num,true,"1234731728963125250",null,"请求报价",ConstantsContext.getJumpUrl().replace("{ORDERNUM}",order_num).replace("{TYPE}","20"),LoginContextHolder.getContext().getUser().getName());
    }

    /**
     * 送货通知
     * @param order_num
     */
    @Override
    public void deliveryOrderOffer(String order_num) {
        sendMessageUtilService.sendMessage(order_num,true,"1234731875721822209",null,"送货通知",ConstantsContext.getJumpUrl().replace("{ORDERNUM}",order_num).replace("{TYPE}","20"),LoginContextHolder.getContext().getUser().getName());
        if(orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",order_num).eq("type","1234731875721822209"))==null){
            updateOrderPropNote(order_num,"跟单员发起送货通知，请尽快送货。",1,new Date(),"系统管理员");
            OrderProp orderProp = new OrderProp();
            orderProp.setName("送货人员");
            orderProp.setOrderNum(order_num);
            orderProp.setDoOver("0");
            orderProp.setType("1234731875721822209");
            orderProp.setTypeName("送货");
            orderPropMapper.insert(orderProp);
        }

    }

    /**
     * 寄货通知
     * @param order_num
     */
    @Override
    public void sendJHOrderOffer(String order_num,Express express) {
        express.setOrderNum(order_num);
        orderMapper.updateExpress(express);
        sendMessageUtilService.sendJH(order_num,express);
    }

    /**
     * 获取快递信息
     * @param order_num
     * @return
     */
    @Override
    public Express getExpressInfo(String order_num) {
        Express expressInfo = orderMapper.getExpressInfo(order_num);
        return expressInfo;
    }

    /**
     * 报价单备注修改
     * @param orderNum
     * @param text
     */
    @Override
    public void updateModelMuchsNote(String orderNum, String text) {
        modelMapper.updateModelMuchsNote(text,orderNum);
    }
    /**
     * 报价单改价备注修改
     * @param orderNum
     * @param text
     */
    @Override
    public void updateModelMuchsChangeNote(String orderNum, String text) {
        modelMapper.updateModelMuchsChangeNote(text,orderNum);
    }

    /**
     * 回单上传
     * @param order_num
     */
    @Override
    public void uploadTheReceipt(String px,String order_num) {
        OrderPropNote orderPropNote = new OrderPropNote();
        orderPropNote.setText("司机完成送货").setFlagDo(1).setOrderNum(order_num).setOverTime(new Date()).setUserName("U"+LoginContextHolder.getContext().getUserId());
        updateOrderProp(px,0,orderPropNote);
    }

    /**
     * 网板位置填写
     * @param order_num
     * @param local
     */
    @Override
    public void updateSceenLocal(String order_num, String local) {
        modelMapper.updateSceenLocal(order_num.split("-")[0],local);
    }

    /**
     * 获取网板位置
     * @param order_num
     * @return
     */
    @Override
    public String getSceenLocal(String order_num) {
        String sceenLocal = modelMapper.getSceenLocal(order_num.split("-")[0]);
        return sceenLocal;
    }

    /**
     * 更新订单进度
     * @param px
     */
    @Override
    @Transactional
    public void updateOrderProp(String px, Integer type, OrderPropNote orderPropNote) {
        OrderProp orderPropForOther= orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num", orderPropNote.getOrderNum()).eq("type", px));
        if(null == orderPropForOther){
            return;
        }
        if(orderPropForOther.getDoOver().equals("1")){
            if("1234731728963125250".equals(orderPropForOther.getType())){
                return;
            }
            throw new OutExcetion("该订单已完成，请勿重复提交");
        }
        //流程
        OrderProp orderProp = new OrderProp();
        //设置
        orderProp.setDoOver(orderPropNote.getFlagDo().toString());
        //设置完成者的名字
        orderProp.setName(LoginContextHolder.getContext().getUser().getName());
        //Id 设置成我的Id
        orderProp.setSx("U"+LoginContextHolder.getContext().getUser().getId().toString());
        //ID 设置成我的ID
        orderPropNote.setUserName("U"+LoginContextHolder.getContext().getUser().getId().toString());
        /* 如果为空 则添加默认*/
        if(StringUtils.isEmpty(orderPropNote.getText())){
            if(orderPropNote.getFlagDo() == 0){
                orderPropNote.setText("阶段性完成");
                orderPropNote.setOverTime(new Date());
            }else {
                orderPropNote.setText("任务完成");
                orderPropNote.setOverTime(new Date());
            }
        }
        orderPropMapper.update(orderProp,new QueryWrapper<OrderProp>().eq("order_num",orderPropNote.getOrderNum()).eq("type",px));
        orderPropNoteMapper.insert(orderPropNote);
        if(type!=0){
            Order o = new Order();
            if(type == 1){
                o.setReaTime(new Date());
            }
            o.setStatus(type);
            orderMapper.update(o,new QueryWrapper<Order>().eq("order_num",orderPropNote.getOrderNum()));
        }
    }

    /**
     * 生产单工艺单上传
     * @param orderSheet
     */
    @Override
    @Transactional
    public void uploadOrderSheet(OrderSheet orderSheet) {
        orderSheet.setOrderNum(orderSheet.getOrderNum().split("-")[0]);
        orderSheetMapper.insert(orderSheet);
        Order s= new Order().setHavSheet(1);
        orderMapper.update(s,new QueryWrapper<Order>().eq("order_num",orderSheet.getOrderNum()));
    }

    /**
     * 生产单工艺单删除
     */
    @Override
    @Transactional
    public void deleteOrderSheet(Long id) {
        orderSheetMapper.deleteById(id);
    }
    /**
     * 生产单工艺单查看
     */
    @Override
    @Transactional
    public List<OrderSheet> getOrderSheet(String order_num) {
        String s = order_num.split("-")[0];
        List<OrderSheet> order_num1 = orderSheetMapper.selectList(new QueryWrapper<OrderSheet>().eq("order_num", s));
        return order_num1;
    }

    /**
     * 获取小组信息
     * @return
     */
    @Override
    public List<UserTeam> getTeam(String orderNum) {
        Integer temp = orderMapper.selectTypeNumByOrderNum(orderNum);
        List<UserTeam> userTeams = userTeamMapper.selectList(new QueryWrapper<UserTeam>().eq("type",temp));
        return userTeams;
    }

    /**
     * 获取打样工
     * @return
     */
    @Override
    public List<UserTeam> getPattenUser(String orderNum) {
        Integer temp = orderMapper.selectTypeNumByOrderNum(orderNum);
        List<UserTeam> userTeams = userTeamMapper.selectList(new QueryWrapper<UserTeam>().eq("type",temp));
        List<UserTeam> user = new ArrayList<>();
        for(UserTeam userTeam:userTeams){
            List<UserTeamU> team_id = userTeamUMapper.selectList(new QueryWrapper<UserTeamU>().eq("team_id", userTeam.getId()));
            userTeam.setUsers(team_id);
            user.add(userTeam);
        }
        return user;
    }

    /**
     * 更改小组
     */
    @Override
    @Transactional
    public void changeTeam(String px,OrderTeam orderTeam) {
        orderTeam.setType(0);
        orderTeamMapper.update(orderTeam,new QueryWrapper<OrderTeam>().eq("order_num",orderTeam.getOrderNum()));
        UserTeam userTeam = userTeamMapper.selectById(orderTeam.getTeamId());
        try{
            updateOrderPropNote(orderTeam.getOrderNum(),"订单已分配给"+userTeam.getName()+"组",1,new Date(),"U"+LoginContextHolder.getContext().getUserId());
        }catch (Exception e){

        }finally {
            OrderProp orderProp = new OrderProp().setDoOver("1").setSx("U"+LoginContextHolder.getContext().getUserId()).setName(LoginContextHolder.getContext().getUser().getName());
            orderPropMapper.update(orderProp,new QueryWrapper<OrderProp>().eq("order_num",orderTeam.getOrderNum()).eq("type",px));
        }
    }

    /**
     * 获取单个人或者单个角色的
     * @param orderProp
     * @return
     */
    @Override
    public List<OrderPropNote> getUserOrRoleProp(OrderProp orderProp) {
        /* 将获取到的ID进行判断是否*/
        String sx = orderProp.getSx();
        List<OrderPropNote> orderPropNotes = null;
        /* 是用户id */
        QueryWrapper<OrderPropNote> eq = new QueryWrapper<OrderPropNote>().eq("order_num", orderProp.getOrderNum()).eq("user_name", sx);
        if("U".equals(sx.substring(0,1))){
            /* 获取到前进度的信息 */
            String s = userService.find(LoginContextHolder.getContext().getUserId());
            Order order_num = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderProp.getOrderNum()));
            if(StringUtils.isEmpty(s) && !order_num.getNameId().equals(LoginContextHolder.getContext().getUserId())){
                eq.notLike("text","|金额");
            }
            orderPropNotes = orderPropNoteMapper.selectList(eq);
            return orderPropNotes;
        }else {
            orderPropNotes = orderPropNoteMapper.selectList(eq);
            return orderPropNotes;
        }
    }

    /**
     * 对单个进度进行催单
     * @param orderPropNote
     */
    @Override
    public void toUrag(OrderPropNote orderPropNote) {
        /* 判断催办内容是否为空 */
        if(orderPropNote.getText() == null || "".equals(orderPropNote.getText())){
            throw new OutExcetion("催办内容不能为空");
        }
        String text = orderPropNote.getTypeName()+"催单({PROP}):{MODELNUM}  {NAME}  {SUM}   {UNITS} \n" +
                "操作人：{USER}\n" +
                "操作时间： {TIME}\n"+
                "{USER}："+orderPropNote.getText();
        String userName = orderPropNote.getUserName();
        orderPropNote.setText(LoginContextHolder.getContext().getUser().getName()+":"+orderPropNote.getText());
        Order order = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderPropNote.getOrderNum()));
        /* 判断是否是用户id */
        if(userName.startsWith("U")){
            /* 根据id获取用户 */
            Map<String, Object> userInfo = userService.getUserInfo(Long.valueOf(userName.substring(1, userName.length())));
            /* 个人催办提醒 */
            sendMessageUtilService.sendMessages((String)userInfo.get("account"),text,order,LoginContextHolder.getContext().getUser().getName());
            //sendMessageUtilService.sendMessageToAccount((String)userInfo.get("account"),"催办提醒",order,LoginContextHolder.getContext().getUser().getName());
            orderPropNote.setOverTime(new Date());
            orderPropNoteMapper.insert(orderPropNote);
            return ;
        }else {
            /* 催办角色 */
            List<String> strings = userService.getAccountByRoleId(userName);
            /* 对前缀进行处理 */
            if(strings == null || strings.size() ==0){
                throw new OutExcetion("该角色下尚未人员");
            }
            String temp = "";
            for(String s : strings){
                if(s.indexOf("oauth2")>-1){
                    s = s.split("oauth2_")[1];
                    temp = temp + "|" + s;
                }
            }
            if(temp!=null && temp !=""){
                temp = temp.substring(1,temp.length());
            }else{
                return;
            }
            orderPropNote.setOverTime(new Date());
            orderPropNoteMapper.insert(orderPropNote);
            sendMessageUtilService.sendMessages(temp,text,order,LoginContextHolder.getContext().getUser().getName());
        }
    }

    /**
     * 检查订单是否存在
     * @param num
     * @return
     */
    @Override
    public Integer checkOrderByModelNum(Long num) {
        Integer order_num = orderMapper.selectCount(new QueryWrapper<Order>().like("order_num", num));
        return order_num;
    }

    /**
     * 打印请求
     * @param order_num
     */
    @Override
    public void printOrder(String order_num) {
        printService.print(order_num,1,LoginContextHolder.getContext().getUser().getName(),this,1);
    }

    /**
     * 司机上传票据信息
     * @param param
     */
    @Override
    public void addOrderSheet(Map<String, String> param) {
        OrderSheet orderSheet = orderSheetMapper.selectOne(new QueryWrapper<OrderSheet>().eq("order_num", param.get("orderNum")));
        if(orderSheet == null){
            orderSheet = new OrderSheet();
            orderSheet.setOrderNum(param.get("orderNum"));
            orderSheet.setUrl(param.get("url"));
            orderSheetMapper.insert(orderSheet);
            return ;
        }
        orderSheet.setUrl(param.get("url"));
        orderSheetMapper.update(orderSheet,new QueryWrapper<OrderSheet>().eq("order_num",orderSheet.getOrderNum()));
    }

    /**
     * 司机获取票据信息
     * @param orderNum
     * @return
     */
    @Override
    public OrderSheet getOrderSheetByOrderNum(String orderNum) {
        OrderSheet orderSheet = orderSheetMapper.selectOne(new QueryWrapper<OrderSheet>().eq("order_num", orderNum));
        return orderSheet;
    }

    /**
     * 获取生产监控统计内容
     * @return
     */
    @Override
    public List<RestType> getPMDate() {
        /*获取系统的跟单员*/
        List<Map> user = userMapper.selectUserIdByMenuId("1234729638731726850");
        /* 根据id 获取他的未完成订单数量*/
        List<RestType> types = new ArrayList<>();
        RestType restType = null;
        for (Map map:user){
            /* 获取订单数量 */
            Integer integer = orderMapper.selectCount(new QueryWrapper<Order>().eq("name_id", map.get("userId")).eq("overFlag", 0).ne("status",6));
            restType = new RestType();
            restType.setType("1,"+map.get("userId"));
            restType.setNum("跟单员"+map.get("name"));
            restType.setValue(integer.toString());
            if(integer == 0) continue;
            types.add(restType);
        }
        /* 获取所有小组 */
        List<UserTeam> userTeams = userTeamMapper.selectList(null);
        for(UserTeam t:userTeams){
            restType = new RestType();
            Integer integer = userTeamMapper.selectTeamData(t.getId()).size();
            restType.setNum(t.getName()).setValue(String.valueOf(integer)).setType("2,"+t.getId());
            if(integer == 0) continue;
            types.add(restType);
        }
        /* 制版晒版调色财务*/
        Map m = orderMapper.selectDataFor();
        for(int s = 1;s < 9;s++){
            restType = new RestType();
            restType.setValue(m.get("type"+s).toString());
            if(restType.getValue().equals("0")) continue;
            restType.setType("3,"+s);
            if(s== 1){
                restType.setNum("制版");
            }else if(s == 2){
                restType.setNum("晒版");
            }else if (s==3){
                restType.setNum("丝印调色");
            }else if (s==4){
                restType.setNum("数码调色");
            }else if (s==5){
                restType.setNum("报价");
            }else if (s==6){
                restType.setNum("送货");
            }else if (s==7){
                restType.setNum("财务");
            }else if (s==8){
                restType.setNum("未分派订单");
            }
            types.add(restType);
        }
        return types;
    }

    /**
     * 分派结束
     * @param orderNum
     */
    @Override
    public void overFp(String orderNum) {
        orderMapper.overFp(orderNum);
        List<OrderTeam> orderTeams = orderTeamMapper.selectList(new QueryWrapper<OrderTeam>().eq("order_num", orderNum).eq("sure_flag", 1).isNotNull("team_id"));
        String temp = "";
        for(OrderTeam orderTeam:orderTeams){
            temp = temp + orderTeam.getUserName() + "  " ;
            if(orderTeam.getType() == 1){
                temp = temp + "定位";
            }else if(orderTeam.getType() == 2){
                temp = temp + "点数";
            }else if(orderTeam.getType() == 3){
                temp = temp + "刮板";
            }else if(orderTeam.getType() == 4){
                temp = temp + "总检（台上）";
            }else if(orderTeam.getType() == 5){
                temp = temp + "总检（台道）";
            }
            temp = temp + "<br/>";
        }
        OrderPropNote orderPropNote = new OrderPropNote();
        /* 设置结束时间 */
        orderPropNote.setOverTime(new Date());
        /* 完成标志 */
        orderPropNote.setFlagDo(1);
        /* 订单编号 */
        orderPropNote.setOrderNum(orderNum);
        /* 文字*/
        orderPropNote.setText(temp);
        /* 名字 */
        orderPropNote.setUserName("U"+LoginContextHolder.getContext().getUserId());
        orderPropNoteMapper.insert(orderPropNote);
    }

    /**
     * 选择打样工
     * @param px
     * @param orderTeam
     */
    @Override
    @Transactional
    public void changeOrderPatten(String px, OrderTeam orderTeam) {
        /* 分派到人 直接锁定参与功能 */
        orderMapper.overFp(orderTeam.getOrderNum());
        /* 循环添加 */
        updateOrderPropNote(orderTeam.getOrderNum(),"完成分派，分派到："+orderTeam.getUserName(),1,new Date(),"U"+LoginContextHolder.getContext().getUserId());
        /* 判断是否分派 */
        if(orderTeamMapper.selectList(new QueryWrapper<OrderTeam>().eq("order_num",orderTeam.getOrderNum()).isNotNull("team_id")).size()!=0){
            throw new OutExcetion("当前订单已完成分派");
        }
        /* 删除空白项 */
        orderTeamMapper.delete(new QueryWrapper<OrderTeam>().eq("order_num", orderTeam.getOrderNum()));
        String[] id = orderTeam.getUser().split(",");
        String[] name = orderTeam.getUserName().split(",");
        for(int i = 0;i<id.length;i++){
            orderTeam.setUserId(Long.valueOf(id[i]));
            orderTeam.setUserName(name[i]);
            orderTeam.setSureFlag(1);
            orderTeamMapper.insert(orderTeam);
        }
        OrderProp orderProp = new OrderProp().setDoOver("1").setSx("U"+LoginContextHolder.getContext().getUserId()).setName(LoginContextHolder.getContext().getUser().getName());
        orderPropMapper.update(orderProp, new QueryWrapper<OrderProp>().eq("order_num", orderTeam.getOrderNum()).eq("type", px));
    }

    /**
     * 更新个人进度
     *
     * */
    @Override
    public void updateOrderPropNote(String orderNum,String text, Integer doOVER,Date date,String name) {
        OrderPropNote orderProp = new OrderPropNote();
        orderProp.setUserName(name);
        orderProp.setOrderNum(orderNum);
        orderProp.setFlagDo(doOVER);
        orderProp.setText(text);
        orderProp.setOverTime(date);
        orderPropNoteMapper.insert(orderProp);
    }

    /**
     * 获取订单详情
     * @param order_num
     * @return
     */
    @Override
    public RestAllOrder getOrderInfo(Integer type,String order_num,Boolean isPrint) {
        LoginContext context = LoginContextHolder.getContext();
        String s = "";
        if(context.hasLogin()== false || isPrint){
            s = "0";
        }else {
            s = context.getUserId().toString();
        }
        Order order_num1 = orderMapper.selectOrderByNum(s,order_num);
        List<OrderNum> order_num2 = orderNumMapper.selectList(new QueryWrapper<OrderNum>().eq("order_num", order_num));
        List<ModelColor> modelColor = modelService.getModelColor(Long.valueOf(order_num.split("-")[0]));
        List<OrderProp> orderProps = null;
        RestAllModel allModel = modelService.getAllModel(Long.valueOf(order_num.split("-")[0]));
        if(type != 3){
            orderProps = orderPropMapper.selectList(new QueryWrapper<OrderProp>().eq("order_num", order_num));
            allModel.setModelNote(null);
        }
        RestAllOrder restAllOrder = new RestAllOrder()
                .setRestOrder(order_num1)
                .setOrderProps(orderProps)
                .setRestAllModel(allModel);
        if(type ==1 ){
            restAllOrder.setOrderNums(order_num2);
        }
        restAllOrder.setModelColors(modelColor);
        return restAllOrder;
    }

    /**
     * 获取订单参与情况
     * @param order_num
     * @return
     */
    @Override
    public List<OrderTeam> getOrderForTeam(String order_num) {
        List<OrderTeam> teams = orderTeamMapper.selectList(new QueryWrapper<OrderTeam>().eq("order_num", order_num).isNotNull("user_name"));
        return teams;
    }

    /**
     * 修改员工得参与状态
     * @param order_num
     * @param user_id
     */
    @Override
    public void changeOrderForTeam(String order_num, Long user_id,Integer flag,Integer type) {
        OrderTeam orderTeam = new OrderTeam().setSureFlag(flag);
        orderTeamMapper.update(orderTeam,new QueryWrapper<OrderTeam>().eq("order_num",order_num).eq("user_id",user_id).eq("type",type));
    }

    /**
     * 选择员工状态
     * @param order_num
     * @param user_id
     * @param type
     */
    @Override
    public void selectOrderForTeam(String order_num, Long user_id, Integer type,String name) {
        UserTeamU user = userTeamUMapper.selectOne(new QueryWrapper<UserTeamU>().eq("user_id", user_id));
        OrderTeam orderTeam = new OrderTeam()
                .setSureFlag(1)
                .setUserId(user_id)
                .setOrderNum(order_num)
                .setUserName(name)
                .setType(type)
                .setTeamId(user.getTeamId());
        OrderTeam orderTeam1 = orderTeamMapper.selectOne(new QueryWrapper<OrderTeam>().eq("order_num",order_num).eq("type", orderTeam.getType()).eq("user_id", orderTeam.getUserId()));
        if(orderTeam1 != null){
            throw new OutExcetion("请不要重复勾选");
        }
        orderTeamMapper.insert(orderTeam);

    }

    /**
     * 获取员工
     * @return
     */
    @Override
    public List<UserTeamU> getOrderForTeamMember(String orderNum) {
        /* 搜索该订单分派给谁的 */
        OrderTeam orderTeam = orderTeamMapper.selectOne(new QueryWrapper<OrderTeam>().eq("order_num", orderNum).eq("type", 0).isNotNull("team_id"));
        if(null == orderTeam){
            throw new OutExcetion("该订单尚未分派或者已确认");
        }
        List<UserTeamU> team = userTeamUMapper.selectList(new QueryWrapper<UserTeamU>().eq("team_id", orderTeam.getTeamId()));
        return team;
    }

    @Override
    public Integer getMyPrintId() {
        Integer id = orderMapper.getMyPrintId(LoginContextHolder.getContext().getUserId());
        return id;
    }

    @Override
    public void updatePrintId(Integer printId) {
        orderMapper.updatePrintId(printId,LoginContextHolder.getContext().getUserId());
    }

    @Override
    public boolean checkOrderConsolt(String orderNum) {
        List<OrderConstLog> order_num = orderConstLogMapper.selectList(new QueryWrapper<OrderConstLog>().like("order_num", orderNum.split("-")[0]));
        if(order_num.size() == 0 ){
            OrderConstLog orderConstLog = new OrderConstLog();
            orderConstLog.setConsts(0.0).
                    setCreatedTime(new Date())
                    .setKickback(0.0)
                    .setOrderNum(orderNum)
                    .setSceenConst(0.0);
            this.sendModelInfoMuchs(orderConstLog,"1234731728963125250");
        }
        return false;
    }

    /**
     * 对应员工对应岗位
     * @param orderteam
     */
    @Override
    public void addTeamForUser(OrderTeam orderteam) {
        Integer z = orderMapper.findOverFp(orderteam.getOrderNum());
        if(z == 1){
            throw new OutExcetion("该订单组长已结束申请阶段");
        }
        orderteam.setSureFlag(1);
        OrderTeam orderTeam = orderTeamMapper.selectOne(new QueryWrapper<OrderTeam>().eq("order_num",orderteam.getOrderNum()).eq("type", orderteam.getType()).eq("user_id", orderteam.getUserId()).ne("sure_flag", 2));
        if(null != orderTeam){
            throw new OutExcetion("该成员在列表中");
        }
        orderTeamMapper.insert(orderteam);
    }

    /**
     * 员工参与
     * @param order_num
     * @param type
     */
    @Override
    public void joinOrder(String order_num, Integer... type) {
        Integer z = orderMapper.findOverFp(order_num);
        if(z == 1){
            throw new OutExcetion("该订单组长已结束申请阶段");
        }
        List<UserTeamU> user_id = userTeamUMapper.selectList(new QueryWrapper<UserTeamU>().eq("user_id", LoginContextHolder.getContext().getUserId()));
        Integer teamId = orderTeamMapper.selectOrderTeamId(order_num);
        for(UserTeamU user:user_id){
            if(user.getTeamId() == teamId){
                List<Integer> integers = Arrays.asList(type);
                for(Integer i:integers){
                    OrderTeam orderTeam = new OrderTeam()
                            .setSureFlag(0)
                            .setUserId(LoginContextHolder.getContext().getUserId())
                            .setOrderNum(order_num)
                            .setUserName(LoginContextHolder.getContext().getUser().getName())
                            .setType(i)
                            .setTeamId(user.getTeamId());
                    OrderTeam orderTeam1 = orderTeamMapper.selectOne(new QueryWrapper<OrderTeam>().eq("order_num",order_num).eq("type", orderTeam.getType()).eq("user_id", orderTeam.getUserId()));
                    if(orderTeam1 != null){
                        throw new OutExcetion("请不要重复勾选");
                    }
                    orderTeamMapper.insert(orderTeam);
                }
                return;
            }
        }
    }

    /**
     * 发送报价单
     * @param constLog
     */
    @Override
    @Transactional(rollbackFor=Exception.class)
    public void sendModelInfoMuchs(OrderConstLog constLog,String px) {
        if(StringUtils.isEmpty(constLog.getUnits())){
            constLog.setUnits("件");
        }
        Order orders = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", constLog.getOrderNum()));
        if(orders!=null){
            if(orders.getStatus() < 4){
                orderMapper.setOverFlag(3,constLog.getOrderNum());
            }
        }
        constLog.setCreatedTime(new Date());
        /* 设置上次报价 */
        orderConstLogMapper.insert(constLog);
        /* 如果数据库该款式已经出现过一次 则通知财务改价通知 */
        if(orderConstLogMapper.selectCount(new QueryWrapper<OrderConstLog>().like("order_num",constLog.getOrderNum().split("-")[0]))>0){
            sendMessageUtilService.sendMessageByPx(px,constLog.getOrderNum(),LoginContextHolder.getContext().getUser().getName());
            sendMessageUtilService.sendMessage(constLog.getOrderNum(),true,"1234731593336111106",null,"改价通知",ConstantsContext.getJumpUrl().replace("{ORDERNUM}",constLog.getOrderNum()).replace("{TYPE}","20"),LoginContextHolder.getContext().getUser().getName());
        }
        Order order = new Order();
        order.setLastConst(constLog.getConsts());
        orderMapper.update(order,new QueryWrapper<Order>().eq("model_id",constLog.getOrderNum().split("-")[0]));
        //设置为已报价
        //orderConstLogMapper.selectCount(new QueryWrapper<OrderConstLog>().eq("order_num",constLog.getOrderNum()))
        OrderPropNote orderPropNote = new OrderPropNote();
        orderPropNote.setOverTime(new Date()).setOrderNum(constLog.getOrderNum()).setFlagDo(1).setUserName("U"+LoginContextHolder.getContext().getUserId());
        if(constLog.getConsts() == 0){
            orderPropNote.setText("暂不报价");
        }else {
            orderPropNote.setText("发送报价单成功");
        }
        updateOrderProp(px,0,orderPropNote);
        orderPropMapper.updateAll(constLog.getOrderNum().split("-")[0],px);
    }

    /**
     * 员工和单号记录
     * @param orderPropNote
     */
    @Override
    public void updateOrderUser(OrderPropNote orderPropNote,Integer type) {
        if(orderPropNote.getFlagDo() == 1){
            OrderUser orderUser1 = orderUserMapper.selectOne(new QueryWrapper<OrderUser>().eq("order_num", orderPropNote.getOrderNum()).eq("type", type));
            if (orderUser1 != null) {
                throw new OutExcetion("请勿重复提交");
            }
            OrderUser orderUser = new OrderUser();
            orderUser.setCreatedTime(new Date()).setOrderNum(orderPropNote.getOrderNum())
                    .setUserId(LoginContextHolder.getContext().getUserId())
                    .setType(type);
            orderUserMapper.insert(orderUser);
        }
    }

    /**
     * 丝印调色调色记录
     * @param orderPropNote
     */
    @Override
    public void updateOrderUserForScreen(OrderPropNote orderPropNote) {
        Order order_num = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderPropNote.getOrderNum()));
        if(orderPropNote.getFlagDo() == 1){
            OrderUser orderUser = new OrderUser();
            orderUser.setCreatedTime(new Date()).setOrderNum(orderPropNote.getOrderNum())
                    .setUserId(LoginContextHolder.getContext().getUserId());
            if(order_num.getOrderProg()==1){
                orderUser.setType(20);
            }else if(order_num.getOrderProg()==2||order_num.getOrderProg()==3){
                orderUser.setType(22);
            }
            orderUserMapper.insert(orderUser);
        }
    }

    /**
     * 数码调色调色记录
     * @param orderPropNote
     */
    @Override
    public void updateOrderUserForDC(OrderPropNote orderPropNote) {
        Order order_num = orderMapper.selectOne(new QueryWrapper<Order>().eq("order_num", orderPropNote.getOrderNum()));
        if(orderPropNote.getFlagDo() == 1){
            OrderUser orderUser = new OrderUser();
            orderUser.setCreatedTime(new Date()).setOrderNum(orderPropNote.getOrderNum())
                    .setUserId(LoginContextHolder.getContext().getUserId());
            if(order_num.getOrderProg()==1){
                orderUser.setType(30);
            }else if(order_num.getOrderProg()==2){
                orderUser.setType(40);
            }
            orderUserMapper.insert(orderUser);
        }
    }

    /**
     * 修改数量表
     * @param orderNum
     */
    @Override
    public void updateOrderNums(OrderNum orderNum) {
        orderNumMapper.update(orderNum,new QueryWrapper<OrderNum>().eq("order_num",orderNum.getOrderNum()));
    }
    //提货通知
    @Override
    public void sendPickUpOffer(String order_num,String addr,String phone) {
        sendMessageUtilService.sendTiHuo(order_num,addr,phone);
    }

    /**
     * 出库记录
     * @param num
     * @param type
     * @param color
     */
    @Override
    @Transactional
    public void updateOrderNum(String order_num, Double num, Integer type,Integer id ,String color) {
        if(type == 1){
            //如果类型为1 即 出库
            OrderNumLog orderNumLog = new OrderNumLog()
                    .setOrderNum(order_num)
                    .setColor(color)
                    .setCreatedTime(new Date())
                    .setName(LoginContextHolder.getContext().getUser().getName())
                    .setOutSum(num)
                    .setType(1);
            orderNumLogMapper.insert(orderNumLog);
            OrderNum orderNum = orderNumMapper.selectOne(new QueryWrapper<OrderNum>().eq("order_num", order_num).eq("color", color).eq("id",id));
            orderNum.setRealyCut(num.longValue());
            orderNumMapper.update(orderNum,new QueryWrapper<OrderNum>().eq("order_num",order_num).eq("id",id));
            return;
        }
        if(type == 2){
            //如果类型为2 即 实点数
            OrderNumLog orderNumLog = new OrderNumLog()
                    .setOrderNum(order_num)
                    .setColor(color)
                    .setCreatedTime(new Date())
                    .setName(LoginContextHolder.getContext().getUser().getName())
                    .setOutSum(num)
                    .setType(2);
            orderNumLogMapper.insert(orderNumLog);
            OrderNum orderNum = orderNumMapper.selectOne(new QueryWrapper<OrderNum>().eq("order_num", order_num).eq("color", color).eq("id",id));
            orderNum.setRealyPoint(num.longValue());
            orderNumMapper.update(orderNum,new QueryWrapper<OrderNum>().eq("order_num",order_num).eq("id",id));
            return;
        }
        if(type == 4){
            //如果类型为4 即 印坏
            OrderNumLog orderNumLog = new OrderNumLog()
                    .setOrderNum(order_num)
                    .setColor(color)
                    .setCreatedTime(new Date())
                    .setName(LoginContextHolder.getContext().getUser().getName())
                    .setOutSum(num)
                    .setType(4);
            orderNumLogMapper.insert(orderNumLog);
            OrderNum orderNum = orderNumMapper.selectOne(new QueryWrapper<OrderNum>().eq("order_num", order_num).eq("color", color).eq("id",id));
            orderNum.setBadLinling(num.longValue());
            orderNumMapper.update(orderNum,new QueryWrapper<OrderNum>().eq("order_num",order_num).eq("color",color).eq("id",id));
            return;
        }
        if(type == 3){
            //如果类型为3 即 坏布
            OrderNumLog orderNumLog = new OrderNumLog()
                    .setOrderNum(order_num)
                    .setColor(color)
                    .setCreatedTime(new Date())
                    .setName(LoginContextHolder.getContext().getUser().getName())
                    .setOutSum(num)
                    .setType(3);
            orderNumLogMapper.insert(orderNumLog);
            OrderNum orderNum = orderNumMapper.selectOne(new QueryWrapper<OrderNum>().eq("order_num", order_num).eq("color", color).eq("id",id));
            orderNum.setBadF(num.longValue());
            orderNumMapper.update(orderNum,new QueryWrapper<OrderNum>().eq("order_num",order_num).eq("id",id));
            return;
        }
        List<UserTeamU> users = userTeamUMapper.selectList(new QueryWrapper<UserTeamU>().eq("user_id", LoginContextHolder.getContext().getUserId()));
        Integer teamId = orderTeamMapper.selectOrderTeamId(order_num);
        for(UserTeamU user :users){
            if(user.getTeamId() == teamId){
                if(type == 5){
                    //如果类型为5 即 出库
                    OrderNumLog orderNumLog = new OrderNumLog()
                            .setOrderNum(order_num)
                            .setColor(color)
                            .setCreatedTime(new Date())
                            .setName(LoginContextHolder.getContext().getUser().getName())
                            .setOutSum(num)
                            .setType(5)
                            .setTeamId(user.getTeamId());
                    orderNumLogMapper.insert(orderNumLog);
                    OrderNum orderNum = orderNumMapper.selectOne(new QueryWrapper<OrderNum>().eq("order_num", order_num).eq("color", color).eq("id",id));
                    if(null == orderNum){
                        throw new OutExcetion("订单没有数量表，请直接完成");
                    }
                    orderNum.setOutTime(new Date());
                    orderNumMapper.update(orderNum,new QueryWrapper<OrderNum>().eq("order_num",order_num).eq("id",id));
                }
                return;
            }
        }
        throw new OutExcetion("您可能没有权限");
    }

    /**
     * 获取小组数据
     * @return
     */
    @Override
    public List<RestType> getTeamData() {
        /* 获取所有小组 */
        List<UserTeam> userTeams = userTeamMapper.selectList(null);
        List<RestType> restTypes =new ArrayList<>();
        for(UserTeam t:userTeams){
            RestType restType = new RestType();
            Integer integer = userTeamMapper.selectTeamData(t.getId()).size();
            restType.setNum(t.getName()).setValue(String.valueOf(integer)).setType(String.valueOf(t.getId()));
            restTypes.add(restType);
        }
        return restTypes;

    }

    /**
     * 更新实际完成量
     * @param orderNum
     * @param overCount
     */
    @Override
    @Transactional
    public void updateOrderCount(String orderNum, Double overCount,String units) {
        /* 判断这个订单是否分配到我的组 */
        List<UserTeamU> users = userTeamUMapper.selectList(new QueryWrapper<UserTeamU>().eq("user_id", LoginContextHolder.getContext().getUserId()));
        Integer teamId = orderTeamMapper.selectOrderTeamId(orderNum);
        if(teamId == null){
            throw new OutExcetion("该订单未分派");
        }
        for(UserTeamU teamU:users){
            if(teamU.getTeamId() == teamId){
                Order o = new Order();

                if(overCount==null){
                    throw new OutExcetion("出货总数不能为空");
                }
                List<OrderNumLog> orderNumLogs = orderNumLogMapper.selectList(new QueryWrapper<OrderNumLog>().eq("order_num", orderNum));
                if(orderNumLogs.size() == 0){
                    OrderNumLog orderNumLog = new OrderNumLog();
                    orderNumLog.setOrderNum(orderNum);
                    orderNumLog.setOutSum(overCount);
                    orderNumLog.setCreatedTime(new Date());
                    List<UserTeamU> user = userTeamUMapper.selectList(new QueryWrapper<UserTeamU>().eq("user_id", LoginContextHolder.getContext().getUserId()));
                    Integer id = orderTeamMapper.selectOrderTeamId(orderNum);
                    for(UserTeamU userTeamU :user){
                        if(userTeamU.getTeamId() == id){
                            orderNumLog.setTeamId(id);
                            orderNumLog.setColor("个人订单");
                            orderNumLog.setName(LoginContextHolder.getContext().getUser().getName());
                            orderNumLog.setType(5);
                            orderNumLogMapper.insert(orderNumLog);
                        }
                    }

                }
                o.setRealyCount(overCount.longValue());
                o.setAllcount(Double.valueOf(overCount));
                o.setUnits(units);
                o.setReaTime(new Date());
                orderMapper.update(o,new QueryWrapper<Order>().eq("order_num",orderNum));
                return;
            }
        }
        throw new OutExcetion("该订单没有分派给您");
    }

    /**
     * 小组数据保存
     * @param orderNum
     * @param overCount
     */
    @Override
    @Transactional
    public void updateOrderUserForTeam(String orderNum, Double overCount) {
        /* 获取我的组信息 */
        List<UserTeamU> user = userTeamUMapper.selectList(new QueryWrapper<UserTeamU>().eq("user_id", LoginContextHolder.getContext().getUserId()));
        Integer id = orderTeamMapper.selectOrderTeamId(orderNum);
        for(UserTeamU u:user){
            if(u.getTeamId() == id){
                OrderUser orderUser = new OrderUser()
                        .setUserId(Long.valueOf(LoginContextHolder.getContext().getUserId()))
                        .setType(null)
                        .setCreatedTime(new Date());
                orderUserMapper.insert(orderUser);
                if(userTeamMapper.findOrderConst(id) == null){
                    OrderTeamLog orderTeamLog = new OrderTeamLog();
                    orderTeamLog.setTeam(id).setOverTime(new Date());
                    userTeamMapper.insertTeamLog(orderTeamLog);
                }
                return;
            }
        }
    }

    /**
     * 小组数据统计
     * @param startTime
     * @param overTime
     * @return
     */
    @Override
    @Transactional
    public List<TeamsData> getTeamsData(Date startTime, Date overTime) {
        //获取所有小组
        List<UserTeam> teams = userTeamMapper.selectList(null);
        //小组的条件
        List<TeamsData> teamsData = new ArrayList<>();
        for(UserTeam userTeam:teams){
            //根据小组Id查询对应订单
            TeamsData teamsData1 = new TeamsData();
            List<String> strings = userTeamMapper.ourTeamOrder(userTeam.getId(),startTime,overTime);
            if(strings ==null || strings.size() == 0){
                continue;
            }
            //取出对应小组对应订单集合
            QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
            QueryWrapper<OrderConstLog> orderConstLogQueryWrapper = new QueryWrapper<>();
            //取出订单编号
            orderQueryWrapper.in("order_num",strings);
            orderConstLogQueryWrapper.in("order_num",strings);
            List<Order> orders = orderMapper.selectList(orderQueryWrapper);
            List<OrderConstLog> orderConstLogs = orderConstLogMapper.selectList(orderConstLogQueryWrapper);
            //遍历进行相加
            Long s = 0L;
            Long c = 0L;
            Double b = 0.0;
            for(Order order:orders){
                if(order.getUnits().equals("件")){
                    s = s + order.getRealyCount();
                }
                if(order.getUnits().equals("SF")){
                    c = c + order.getRealyCount();
                }
                b = b + order.getAllcount()*order.getLastConst();
            }
            String z = "";
            if(s != 0){
                z = z + s + "件";
            }else if(c != 0){
                z = z +"|"+c +"SF";
            }else if(s==0 && c==0){
                z = "0";
            }
            teamsData1.setTeamName(userTeam.getName()).setTeamCL(z).setTeamCZ(Double.valueOf(String.format("%.2f",b)));
            teamsData.add(teamsData1);
        }
        return teamsData;
    }

    /**
     * 更新网板数
     * @param order_num
     * @param num
     */
    @Override
    public void setOrderScreen(String order_num, Integer num) {
        modelMapper.setSceenNum(num,Long.valueOf(order_num.split("-")[0]));
    }

    /**
     * 质量监控
     * @param startTime
     * @param overTime
     * @return
     */
    @Override
    public RestOrderCount getQualityMonitoringData(Date startTime, Date overTime) {
        RestOrderCount restOrderCount = orderMapper.selectQMData(startTime,overTime);
        return restOrderCount;
    }

    /**
     * 工作统计
     * @param mouth
     * @return
     */
    @Override
    public List<RestType> getCount(Integer mouth,String type) {
        List<RestType> restTypes = new ArrayList<>();
        Long aLong = orderMapper.selectZT(LoginContextHolder.getContext().getUserId(),type);
        RestType restType = new RestType();
        restType.setNum("昨天").setValue(aLong.toString()).setType("1");
        restTypes.add(restType);
        if(mouth!=null){
            List<OrderUser> orderUsers = orderMapper.selectMonth(mouth, LoginContextHolder.getContext().getUserId(),type);
            restType = new RestType();
            restType.setNum(mouth+"月数").setValue(String.valueOf(orderUsers.size())).setType("2");
            restTypes.add(restType);
            QueryWrapper<OrderConstLog> queryWrapper = null;
            for(OrderUser orderUser:orderUsers){
                queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("order_num",orderUser.getOrderNum());
            }
            restType = new RestType();
            restType.setNum("产值").setType("3");
            if(queryWrapper == null){
                restType.setValue("0");
                restTypes.add(restType);
                return restTypes;
            }
            List<OrderConstLog> orderConstLogs = orderConstLogMapper.selectList(queryWrapper);
            Double consts = 0.0;
            for(OrderConstLog orderConstLog:orderConstLogs){
                consts = consts + orderConstLog.getLastConst();
            }
            restType.setValue(consts.toString());
            restTypes.add(restType);
        }
        return restTypes;
    }


    /**
     * 得到胴体菜单栏
     * @param
     * @return list
     */
    @Override
    public List<Map>   deleteGetAllMenu() {

        List<Map> list =  orderMapper.deleteGetAllMenu(LoginContextHolder.getContext().getUserId().toString());

        return list;
    }

}
