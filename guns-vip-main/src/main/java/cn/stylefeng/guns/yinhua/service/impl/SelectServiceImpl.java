package cn.stylefeng.guns.yinhua.service.impl;


import cn.stylefeng.guns.base.auth.context.LoginContext;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.Teams;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderConstLog;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderProp;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestPageAndData;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestType;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.yinhua.mobile.mapper.*;
import cn.stylefeng.guns.yinhua.service.SelectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 搜索接口实体类
 */
@Service
public class SelectServiceImpl implements SelectService {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderPropMapper orderPropMapper;
    @Autowired
    private TeamsMapper teamsMapper;
    @Autowired
    private OrderTeamMapper orderTeamMapper;
    @Autowired
    private OrderConstLogMapper orderConstLogMapper;
    /**
     * 跟单员搜索
     * @param page
     * @param pageSize
     * @param o
     * @param status  0 未下单 1 开发 2 首样 3改版 4翻样 5产前样 6首单 7翻单 8补片 9返工 10重做 50 裁片未到 51 裁片已到
     * @param type
     * @param sort
     * @param keyWord
     * @return
     */
    @Override
    public RestPageAndData getCreateOrderList(Long page, Long pageSize, String px, Integer status, Integer type, Integer sort, String keyWord) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        QueryWrapper<Order>  queryWrapper = new QueryWrapper<Order>();
        if(status != null){
            queryWrapper.ne("status",6);
            //创建搜索条件
            if(status == 60){
                queryWrapper.eq("overFlag",0).eq("order_progress",0);
            }else if(status == 61){
                queryWrapper.eq("overFlag",0).gt("order_progress",0).le("order_progress",4);
            }else if(status == 62){
                queryWrapper.eq("overFlag",0).gt("order_progress",4).le("order_progress",7);
            }else if(status == 63){
                queryWrapper.eq("overFlag",0).gt("order_progress",7).le("order_progress",10);
            }else if(status == 64){
                queryWrapper.eq("overFlag",0);
            }else if(status == 80){
                queryWrapper = new QueryWrapper<Order>();
                queryWrapper.eq("status",6).eq("overFlag",0);
            }else {
                queryWrapper.eq("order_progress",status).eq("overFlag",0);
            }

        }
        if(type != null && type !=0){
            queryWrapper.eq("order_type",type).eq("overFlag",0);
        }
        if(sort!=null && sort!=0){
            switch (sort){
                case 1:
                    //创建时间正序
                    queryWrapper.orderByDesc("created_time").eq("overFlag",0);
                    break;
                case 2:
                    //名字正序
                    queryWrapper.orderByDesc("CONVERT(customer_name USING gbk)").eq("overFlag",0);
                    break;
                case 3:
                    //进度正序
                    queryWrapper.orderByDesc("order_progress").eq("overFlag",0);
                    break;
                case 4:
                    queryWrapper.orderByDesc("over_time").eq("overFlag",0);
                    break;
                case 5:
                    queryWrapper.orderByDesc("allcount").eq("overFlag",0);
                    break;
                case 6:
                    queryWrapper.orderByDesc("urag").eq("overFlag",0);
                    break;
            }
        }
        if(keyWord!=null && keyWord!=""){
            queryWrapper.like("model_id",keyWord)
                    .or().like("customer_name",keyWord)
                    .or().like("order_num",keyWord)
                    .or().like("model_name",keyWord);
        }else {
            queryWrapper.eq("name_id",LoginContextHolder.getContext().getUserId());
        }
        if(StringUtils.isEmpty(sort)){
            queryWrapper.orderByAsc("created_time");
        }
        Page<Order> orderPage = orderMapper.selectMyPage(new Page<Order>(page, pageSize), LoginContextHolder.getContext().getUserId(),keyWord,queryWrapper);
        List<Order> orders = orderMapper.selectList(queryWrapper.eq("name_id",LoginContextHolder.getContext().getUserId()));
        Double j_count = 0.0;
        Double s_count = 0.0;
        Double money = 0.0;
        for(Order order:orders){
            if(order.getUnits().equals("件")){
                j_count = j_count + (order.getAllcount()==null?0:order.getAllcount());
            }else{
                s_count = s_count + (order.getAllcount()==null?0:order.getAllcount());
            }
            money = money + (order.getLastConst()*order.getAllcount());
        }
        List<RestType> restTypes = new ArrayList<>();
        RestType r = new RestType().setNum("数量").setValue(String.format("%.2f",j_count)).setType("4");
        restTypes.add(r);
        r = new RestType().setNum("尺数").setValue(String.format("%.2f",s_count)).setType("3");
        restTypes.add(r);
        r = new RestType().setNum("产值").setValue(String.format("%.2f",money)).setType("2");
        restTypes.add(r);

        return new RestPageAndData(new PageResult(page,pageSize,orderPage.getTotal(),orderPage.getRecords().size(),orderPage.getRecords()),restTypes);
    }

    /**
     * 制版搜索
     * @param page
     * @param pageSize
     * @param px
     * @param status
     */
    @Override
    public Page<Order> getPlateMakingData(Long page, Long pageSize, String px, Integer status) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }

        QueryWrapper<OrderProp> orderPropQueryWrapper = new QueryWrapper<OrderProp>().eq("type",px).eq("do_over",0);
        //取出未制版的单子
        List<OrderProp> orderProps = orderPropMapper.selectList(orderPropQueryWrapper);
        if(orderProps == null || orderProps.size() == 0){
            //没有则返回空
            return null;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();

        for(OrderProp orderProp:orderProps){
            queryWrapper.or().eq("order_num",orderProp.getOrderNum()).eq("overFlag",0);
            if(status != null && status == 1){
                queryWrapper.gt("urag",0);
            }
        }
        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize),  LoginContextHolder.getContext().getUserId(),null,queryWrapper.orderByDesc("created_time"));
        return urag;
    }

    /**
     * 晒版搜索
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getPrintingDownData(Long page, Long pageSize, String px, Integer status) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        QueryWrapper<OrderProp> orderPropQueryWrapper = new QueryWrapper<OrderProp>().eq("type",px);
        //取出未制版的单子
        if(status != null){
            if(status == 12){
                orderPropQueryWrapper.eq("do_over",1);
            }
            if(status == 11){
                orderPropQueryWrapper.eq("do_over",0);
            }
        }else {
            orderPropQueryWrapper.eq("do_over",0);
        }
        List<OrderProp> orderProps = orderPropMapper.selectList(orderPropQueryWrapper);
        if(orderProps == null || orderProps.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        for(OrderProp orderProp:orderProps){
            queryWrapper.or().eq("order_num",orderProp.getOrderNum()).eq("overFlag",0);
        }
        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),null, queryWrapper.orderByDesc("created_time"));
        return urag;
    }

    /**
     * 丝印调色
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getScreenPrintingColorData(Long page, Long pageSize, String px, Integer status) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        QueryWrapper<OrderProp> orderPropQueryWrapper = new QueryWrapper<OrderProp>().eq("type",px).eq("do_over",0);
        List<OrderProp> orderProps = orderPropMapper.selectList(orderPropQueryWrapper);
        if(orderProps == null || orderProps.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        for(OrderProp orderProp:orderProps){
            queryWrapper.or().eq("order_num",orderProp.getOrderNum()).eq("overFlag",0);
            if(status != null){
                if(status == 14){ //打样单
                    queryWrapper.lt("order_progress",5);
                }
                if(status == 15){ //生产单
                    queryWrapper.eq("order_prog",2).or().eq("order_prog",3);
                }
                if(status == 16){
                    queryWrapper.gt("urag",0);
                }
                if(status == 17){
                    queryWrapper.eq("hav_sheet",0);
                }
            }
        }

        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId() ,null,queryWrapper.orderByDesc("created_time"));
        return urag;
    }

    /**
     * 数码调色
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getDigitalColorData(Long page, Long pageSize, String px, Integer status) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        QueryWrapper<OrderProp> orderPropQueryWrapper = new QueryWrapper<OrderProp>().eq("type",px).eq("do_over",0);
        List<OrderProp> orderProps = orderPropMapper.selectList(orderPropQueryWrapper);
        if(orderProps == null || orderProps.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        for(OrderProp orderProp:orderProps){
            if(status != null){
                if(status == 16){
                    queryWrapper.or().eq("order_num",orderProp.getOrderNum()).gt("urag",0);
                }
            }else {
                queryWrapper.or().eq("order_num",orderProp.getOrderNum());
            }
        }
        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId() ,null,queryWrapper.eq("overFlag",0).orderByDesc("created_time"));
        return urag;
    }

    /**
     * 分配 打样
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getDispatchOrderData(Long page, Long pageSize, String px, Integer status) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        List<String> strings = null;
        if(status != null){
            if(status == 20 ){
                strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(), px, "0");
            }
            if(status == 21){
                strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(),px,"1");
            }
        }else{
            strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(), px, "0");
        }
        if(strings == null || strings.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        for(String orderProp:strings){
            queryWrapper.or().eq("order_num",orderProp).eq("overFlag",0);
        }
        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),null ,queryWrapper.orderByDesc("created_time"));
        return urag;

    }

    /**
     * 打样
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getPattenData(Long page, Long pageSize, String px, Integer status) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        List<String> strings = orderMapper.selectForDY(LoginContextHolder.getContext().getUserId().toString(), px,"0");
        if(strings == null || strings.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if(status != null){
            int i = 0;
            if(status == 22){
                for(String orderProp:strings){
                    if(orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",orderProp).eq("type","1234729858706194434").eq("do_over","1"))!=null){
                        if(orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",orderProp).eq("type","1234730078953291778").eq("do_over","1"))!=null){
                            if(orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",orderProp).apply("((type = 1234730294012035073 and do_over = 1) or (type = 1234733172114079747) and do_over =1 )"))!=null){
                                queryWrapper.or().eq("order_num",orderProp).eq("overFlag",0);
                                i++;
                            }
                        }
                    }
                }
                if(i == 0){
                    queryWrapper.eq("order_num",0);
                }
            }
            if(status == 23){
                for(String orderProp:strings){
                    if(orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",orderProp).eq("type","1234729858706194434").eq("do_over","1"))==null
                            ||orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",orderProp).eq("type","1234730078953291778").eq("do_over","1"))==null
                            ||orderPropMapper.selectOne(new QueryWrapper<OrderProp>().eq("order_num",orderProp).apply("((type = 1234730294012035073 and do_over = 1) or (type = 1234733172114079747) and do_over =1 )"))==null){
                        queryWrapper.or().eq("order_num",orderProp).eq("overFlag",0);
                        i++;
                    }
                }
                if(i == 0){
                    queryWrapper.eq("order_num",0);
                }
            }
            if(status == 11){
                queryWrapper.in("order_num",strings).gt("urag",0);
            }
            if(status == 24){
                queryWrapper.in("order_num",strings);
            }
        }

        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId() ,null,queryWrapper.orderByDesc("created_time"));
        return urag;

    }

    /**
     * 生产分配
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getPDispatchOrderData(Long page, Long pageSize, String px, Integer status) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if(status > 1999){
            List<String> strings = null;
            if(status != null){
                if(status == 2000){
                    strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(), px, "0");
                }
                if(status == 2001){
                    strings = orderMapper.selectForFPDY(LoginContextHolder.getContext().getUserId().toString(), px, "1");
                }
            }
            if(strings == null || strings.size() == 0){
                //没有则返回空
                Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
                return urag;
            }
            //根据单号获取订单
            for(String orderProp:strings){
                queryWrapper.or().eq("order_num",orderProp).eq("overFlag",0);
            }
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), queryWrapper.orderByDesc("created_time"));
            return urag;
        }
        List<String> strings = orderMapper.selectTeamData(status);
        if(strings == null || strings.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        queryWrapper.in("order_num",strings);
        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(), null,queryWrapper.orderByDesc("created_time"));
        return urag;
    }

    /**
     * 生产小组组长
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getTeamLeaderData(Long page, Long pageSize, String px, Integer status,String keyWord) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        /* 获取我小组的订单 或者 分派给我的订单 */
        List<String> strings = null;
        if(status !=null && status == 30){
            strings = orderMapper.selectMyOrderByUserIdForUn(LoginContextHolder.getContext().getUserId().toString(), "0");
        }else {
            strings = orderMapper.selectMyOrderByUserId(LoginContextHolder.getContext().getUserId().toString(), "0");
        }
        if(status !=null && status == 32){
            strings = orderMapper.selectMyOrderByUserIdForToday(LoginContextHolder.getContext().getUserId().toString(), "0");
        }
        if(strings == null || strings.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        if(keyWord!=null && keyWord!=""){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("model_id",keyWord)
                    .or().like("customer_name",keyWord)
                    .or().like("order_num",keyWord)
                    .or().like("model_name",keyWord);
        }else{
            for(String orderProp:strings){
                queryWrapper.or().eq("order_num",orderProp);
                if(status != null && status !=0){
                    if(status == 30){
                        queryWrapper.eq("over_fp",0);
                    }
                    if(status == 11 ){
                        //queryWrapper = new QueryWrapper<>();
                        queryWrapper.gt("urag",0);
                    }
                    if(status == 32){
                        queryWrapper.in("order_num",strings);
                    }
                }
            }
            if(status !=null && status == 30){
                Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),null, queryWrapper.orderByDesc("created_time"));
                return urag;
            }
        }
        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),keyWord, queryWrapper.eq("overFlag",0).orderByDesc("created_time"));

        return urag;

    }

    /**
     * 小组成员搜索
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getMemberList(Long page, Long pageSize, String px, Integer status) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        /* 获取成员的订单 */
        List<String> strings = orderMapper.selectMyOrderByUserId(LoginContextHolder.getContext().getUserId().toString(), "1");
        if(status !=null && status == 32){
            strings = orderMapper.selectMyOrderByUserIdForToday(LoginContextHolder.getContext().getUserId().toString(), "1");
        }
        if(strings == null || strings.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        for(String orderProp:strings){
            queryWrapper.or().eq("order_num",orderProp);
            if(status != null && status !=0){
                if(status == 30){
                    queryWrapper.eq("order_progress",50);
                }
                if(status == 31 ){
                    queryWrapper.eq("order_progress",51);
                }
                if(status == 11 ){
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.gt("urag",0);
                }
                if(status == 32){
                    queryWrapper.in("order_num",strings);
                }
            }
        }
        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),null, queryWrapper.eq("overFlag",0).orderByDesc("created_time"));

        return urag;
    }

    /**
     * 报价
     * @param page
     * @param pageSize
     * @param px
     * @param status
     * @return
     */
    @Override
    public Page<Order> getQuotationData(Long page, Long pageSize, String px, Integer status,String keyWord,Integer type,Integer sort) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        QueryWrapper<Order> queryWrapper = null;
        if(keyWord!=null && keyWord!=""){
            queryWrapper = new QueryWrapper<>();
            queryWrapper.like("model_id",keyWord)
                    .or().like("customer_name",keyWord)
                    .or().like("order_num",keyWord)
                    .or().like("model_name",keyWord);
        }else {
            List<OrderProp> orderProps = null;
            if(status != null){
                if(status == 33){
                    orderProps = orderPropMapper.selectList(new QueryWrapper<OrderProp>().eq("type",px).eq("do_over", 0));
                }else
                if(status == 34 ){
                    queryWrapper = new QueryWrapper<>();
                    queryWrapper.apply("last_const != 0 and (select to_days(created_time) from order_const_log where order_num like concat('%',orders.model_id,'%') order by created_time desc limit 1) - to_days(now()) < 7");
                    Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId() ,keyWord,queryWrapper.orderByDesc("created_time").groupBy("model_id"));
                    return urag;
                }else
                if(status == 35 ){
                    orderProps =
                            orderPropMapper.selectList(new QueryWrapper<OrderProp>()
                            .eq("type",px).eq("do_over", 0)
                            .apply("(select do_over from order_prop as c where order_num = order_prop.order_num and type = '1234731218520522753' ) = 1"));
                }else if (status == 36 ){
                    orderProps =
                            orderPropMapper.selectList(new QueryWrapper<OrderProp>()
                            .eq("type",px).eq("do_over", 0)
                            .apply("(select do_over from order_prop as c where order_num = order_prop.order_num and type = '1234731218520522753' ) = 0"));
                }else if(status == 37){
                    orderProps = orderConstLogMapper.selectOrderPropList();
                }else if(status == 38){
                    orderProps = orderConstLogMapper.selectOrderPropListByOrders(6);
                }else if(status == 39){
                    orderProps = orderConstLogMapper.selectOrderPropListByOrders(5);
                }else if(status == 40){
                    orderProps = orderConstLogMapper.selectOrderPropListByOrdersConst();
                }
            }else {
                orderProps = orderPropMapper.selectList(new QueryWrapper<OrderProp>().eq("type",px).eq("do_over", 0));
            }
            if(orderProps == null || orderProps.size() == 0){
                //没有则返回空
                Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
                return urag;
            }
            //根据单号获取订单
            queryWrapper = new QueryWrapper<>();
            for(OrderProp orderProp : orderProps){
                queryWrapper.or().eq("order_num",orderProp.getOrderNum());
                if(type != null && type !=0){
                    queryWrapper.apply("order_type = "+type);
                }
            }
        }

        if(sort!=null && sort!=0){
            switch (sort){
                case 1:
                    //创建时间正序
                    queryWrapper.orderByDesc("created_time");
                    break;
                case 2:
                    //名字正序
                    queryWrapper.orderByDesc("CONVERT(customer_name USING gbk)");
                    break;
                case 3:
                    //进度正序
                    queryWrapper.orderByDesc("order_progress");
                    break;
                case 4:
                    queryWrapper.orderByDesc("over_time");
                    break;
                case 5:
                    queryWrapper.orderByDesc("allcount");
                    break;
                case 6:
                    queryWrapper.orderByDesc("urag");
                    break;
                case 7:
                    queryWrapper.groupBy("model_id");
                    break;
            }
        }else {
            queryWrapper.orderByAsc("created_time");
        }
        Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId() ,keyWord,queryWrapper);
        return urag;
    }

    /**
     * 财务
     * @param page
     * @param pageSize
     * @param px
     * @return
     */
    @Override
    public Page<Order> getFinanceData(Long page, Long pageSize, String px, Integer type,Integer sort,String keyWord) {
        if(page == null || pageSize == null){
            throw new OutExcetion("分页参数不能为空");
        }
        QueryWrapper<OrderProp> orderPropQueryWrapper = new QueryWrapper<OrderProp>().eq("type",px);
        List<OrderProp> orderProps = orderPropMapper.selectList(orderPropQueryWrapper);
        if(orderProps == null || orderProps.size() == 0){
            //没有则返回空
            Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
            return urag;
        }
        //根据单号获取订单
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        for(OrderProp orderProp:orderProps){
            queryWrapper.or().eq("order_num",orderProp.getOrderNum()).eq("overFlag",0);
            if(type != null){
                if(type == 32){
                    queryWrapper.apply("last_const >= 0").lt("status",4);
                }else
                if(type == 33){
                    queryWrapper.apply("(select consts from order_const_log where order_num like concat('%',orders.model_id,'%') order by created_time desc limit 1) is null").lt("status",4);
                }else
                if(type == 34 ){
                    queryWrapper.apply("(select consts from order_const_log where order_num like concat('%',orders.model_id,'%') order by created_time desc limit 1) >= 0").lt("status",4);
                }else
                if(type == 35){
                    queryWrapper.eq("status",6);
                }else
                if(type == 36){
                    queryWrapper.eq("status",5);
                }
            }
            if(sort != null){
                if(sort == 1){
                    queryWrapper.orderByDesc("created_time");
                }else if(sort == 2){
                    queryWrapper.orderByDesc("customer_name");
                }else if(sort == 3){
                    queryWrapper.orderByAsc("customer_name");
                }
            }
        }
            Page<Order> urag = orderMapper.selectCWMyPage(new Page<>(page, pageSize),LoginContextHolder.getContext().getUserId(),keyWord, queryWrapper.orderByDesc("created_time"));
            return urag;
        }

        /**
         * 送货
         * @param page
         * @param pageSize
         * @param px
         * @param status
         * @return
         */
        @Override
        public Page<Order> getDeliveryData(Long page, Long pageSize, String px, Integer status) {
            if(page == null || pageSize == null){
                throw new OutExcetion("分页参数不能为空");
            }
            QueryWrapper<OrderProp> orderPropQueryWrapper = new QueryWrapper<OrderProp>().eq("type",px).eq("do_over",0);
            List<OrderProp> orderProps = orderPropMapper.selectList(orderPropQueryWrapper);
            if(orderProps == null || orderProps.size() == 0){
                //没有则返回空
                Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
                return urag;
            }
            //根据单号获取订单
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            for(OrderProp orderProp:orderProps){
                queryWrapper.or().eq("order_num",orderProp.getOrderNum());
                if(status != null){
                    if(status == 33){
                        queryWrapper.lt("status",3);
                    }
                    if(status == 34 ){
                        queryWrapper.ge("status",3);
                    }
                }
            }
            Page<Order> urag = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),null, queryWrapper.eq("overFlag",0).orderByDesc("created_time"));
            return urag;
        }

        /**
         * 质量监控列表 1 返工列表 2补片列表 3重做列表 4赔偿列表
         * @param page
         * @param pageSize
         * @param status
         * @return
         */
        @Override
        public Page<Order> getQualityMonitoringOrderData(Long page, Long pageSize, Integer status,Date startTime,Date overTime) {
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            if(status == 1){
                queryWrapper.eq("order_progress",10).or().eq("order_progress",11).between("created_time",startTime,overTime);
            }else if(status == 2){
                queryWrapper.eq("order_progress",8).or().eq("order_progress",9).between("created_time",startTime,overTime);
            }else if(status == 3){
                queryWrapper.eq("order_progress",12).or().eq("order_progress",13).between("created_time",startTime,overTime);
            }else if(status == 4){
                List<String> strings = orderMapper.selectHaveMakeUp(startTime,overTime);
                if(strings == null || strings.size() == 0){
                    throw new OutExcetion("没有赔偿订单");
                }
                queryWrapper.in("order_num",strings);
            }
            Page<Order> orderPage = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),null, queryWrapper.between("created_time",startTime,overTime).orderByDesc("created_time"));
            return orderPage;
        }

        /**
         * 生产监控列表
         * @param page
         * @param pageSize
         * @return
         */
        @Override
        public Page<Order> getProductionMonitoringData(Long page, Long pageSize, Integer type,String value) {
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            /*判断是跟单员查询*/
            if(type == 1){
                /* 根据订单跟单员Id查询*/
                queryWrapper.eq("name_id",value).ne("status","6");
            }else if(type == 2){
                /* 判断生产组查询*/
                List<String> strings = orderMapper.selectTeamData(Integer.valueOf(value));
                if(strings == null || strings.size() == 0){
                    //没有则返回空
                    Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
                    return urag;
                }
                //根据单号获取订单
                queryWrapper.in("order_num",strings);
            }else if(type == 3){
                /* value 1制版  2晒版 3调色 */
                List<String> strings = null;
                if("1".equals(value)){
                    strings = orderMapper.selectForPMC("1234729858706194434");
                }else if("2".equals(value)){
                    strings = orderMapper.selectForPMC("1234730078953291778");
                }else if("3".equals(value)){
                    strings = orderMapper.selectForPMC("1234730294012035073");
                }else if("4".equals(value)){
                    strings = orderMapper.selectForPMC("1234733172114079747");
                }else if("5".equals(value)){
                    strings = orderMapper.selectForPMC("1234731728963125250");
                }else if("6".equals(value)){
                    strings = orderMapper.selectForPMC("1234731875721822209");
                }else if("7".equals(value)){
                    strings = orderMapper.selectForPMC("1234731593336111106");
                }else if("8".equals(value)){
                    strings = orderMapper.selectForPMC("1237267345014022146");
                }
                if(strings == null || strings.size() == 0){
                    throw new OutExcetion("没有相关订单");
                }
                queryWrapper.in("order_num",strings);
                if("7".equals(value)){
                    queryWrapper.between("status",0,3);
                }
            }
            Page<Order> orderPage = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),null, queryWrapper.eq("overFlag",0).orderByAsc("created_time"));
            return orderPage;
        }

        /**
         * 总检列表
         */
        @Override
        public Page<Order> getCheckData(Long page, Long pageSize, Object o, Integer status) {
            //获取未完成得订单编号
            List<String> strings = orderMapper.selectZJData();
            QueryWrapper<Order> queryWrapper = null;
            //如果订单编号为空
            if(strings == null || strings.size() == 0){
                //没有则返回空
                Page<Order> urag = orderMapper.selectPage(new Page<>(page, pageSize), new QueryWrapper<Order>().eq("order_num",0));
                return urag;
            }
            if(status != null){
                queryWrapper = new QueryWrapper();
                for(String s:strings){
                    if(status == 30){
                        queryWrapper.or().eq("order_num",s).eq("order_progress",50);
                    }else if(status == 31){
                        queryWrapper.or().eq("order_num",s).eq("order_progress",51);
                    }else if(status == 11){
                        queryWrapper.or().eq("order_num",s).gt("urag",0);
                    }else if(status == 32){
                        queryWrapper.or().eq("order_num",s).apply("to_days(rea_time) = to_days(now())");
                    }
                }
                if(status == 0){
                    queryWrapper.in("order_num",strings);
                }
            }
            Page<Order> orderPage = orderMapper.selectMyPage(new Page<>(page, pageSize), LoginContextHolder.getContext().getUserId(),null, queryWrapper.orderByDesc("created_time"));
            return orderPage;
        }
    }
