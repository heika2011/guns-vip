package cn.stylefeng.guns.yinhua.service;

import cn.stylefeng.guns.yinhua.entity.yinhua.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelNote;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeamU;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderService {
    Order addOrder(Order order,String px);

    List<RestOrderRole> getOrderType();

    RestOrder getOrder(String num);

    RestOrderCount getDataCount(Integer type,String px);

    void uragOrder(String order_num);

    void copyOrder(String order_num);

    void errorOrder(String order_num,Integer errorCode,String px,String text);

    void updateOrderProp(String px,Integer type, OrderPropNote orderPropNote);

    void uploadOrderSheet(OrderSheet orderSheet);

    void deleteOrderSheet(Long id);

    List<UserTeam> getTeam(String orderNum);

    void changeTeam(String px, OrderTeam orderTeam);

    RestAllOrder getOrderInfo(Integer type,String order_num,Boolean isPrint);

    List<OrderSheet> getOrderSheet(String order_num);

    List<OrderTeam> getOrderForTeam(String order_num);

    void changeOrderForTeam(String order_num, Long user_id,Integer flag,Integer type);

    List<UserTeamU> getOrderForTeamMember(String orderNum);

    void joinOrder(String order_num, Integer... type);

    void changeOrderCreate(String order_num, Long user_id);

    List<Map<String, String>> getCreater();

    void sendModelInfoMuchs(OrderConstLog constLog,String px);

    void updateOrderUser(OrderPropNote orderPropNote,Integer type);

    void updateOrderUserForScreen(OrderPropNote orderPropNote);

    void updateOrderUserForDC(OrderPropNote orderPropNote);

    List<RestType> getTeamData();

    void updateOrderNum(String order_num, Double num, Integer type,Integer id, String color);

    void updateOrderCount(String orderNum, Double overCount,String units);

    void updateOrderUserForTeam(String orderNum, Double overCount);

    void setOrderStatus(Integer type,String order_num,String px,Double money,String num,String url);

    void setOrderMakeUp(OrderConstLog orderConstLog);

    List<TeamsData> getTeamsData(Date startTime, Date overTime);

    List<OrderRole> getOrderRole();

    void sendOrderOffer(String order_num);

    void deliveryOrderOffer(String order_num);

    void sendJHOrderOffer(String order_num,Express express);

    List<ModelNote> getOrderNote(String order_num);

    void setOrderScreen(String order_num, Integer num);

    List<OrderNumLog> getOutForOrder(String order_num);

    RestOrderCount getQualityMonitoringData(Date startTime, Date overTime);

    List<RestType> getCount(Integer mouth,String type);

    void updateOrder(Order order);

    void deleteOrder(String orderNum);

    List<OrderNum> getOrderNum(String num);

    void updateOrderNums(OrderNum orderNum);

    void sendPickUpOffer(String order_num,String addr,String phone);

    void deleteAllOrder(Long num);

    void sendMessageForMsg(String px,String orderNum);

    RestOrderConst getModelMuchs(String orderNum);

    void updateModelMuchsNote(String orderNum, String text);

    void updateModelMuchsChangeNote(String orderNum, String text);

    void uploadTheReceipt(String px,String order_num);

    void updateSceenLocal(String order_num, String local);

    String getSceenLocal(String order_num);

    void selectOrderForTeam(String order_num, Long user_id, Integer type,String name);

    List<OrderNum> getOrderNumList(String order_num);

    String getOrderAllCount(String order_num);

    List<RestOrderNumCount> getTodayCount();

    List<RestOrderNumCount> getOtherTodayCount(Date startTime, Date overTime);

    Express getExpressInfo(String order_num);

    FaConst getOrderConstByF(String orderNum);

    void overOrder(String order_num);

    String getOrderMoney(Date startTime, Date overTime);

    List<OrderPropNote> getOrderPropNote(String orderNum);

    List<OrderTeam> getMemberJoinInfo(String order_num);

    void changeOrderStatus(Order order);

    List<UserTeam> getPattenUser(String orderNum);

    void changeOrderPatten(String px, OrderTeam orderTeam);

    void updateOrderPropNote(String orderNum,String text,Integer doOVER,Date date,String name);

    void checkAllModelInfoForPlace(String orderNum);

    void checkOrderSheet(String orderNum);

    Map getLastCount(String orderNum);

    List<OrderPropNote> getUserOrRoleProp(OrderProp orderProp);

    void toUrag(OrderPropNote orderPropNote);

    Integer checkOrderByModelNum(Long num);

    void printOrder(String order_num);

    /**
     * 分派审核结束
     * @param orderNum
     */
    void overFp(String orderNum);

    /**
     * 司机添加票据信息
     * @param param
     */
    void addOrderSheet(Map<String, String> param);

    /**
     * 司机获取当前生产单的票据图片
     * @param orderNum
     * @return
     */
    OrderSheet getOrderSheetByOrderNum(String orderNum);

    List<RestType> getPMDate();

    void addTeamForUser(OrderTeam orderteam);

    Integer getMyPrintId();

    void updatePrintId(Integer printId);

    boolean checkOrderConsolt(String orderNum);

    String getOrderSheetForCW(String orderNum);

    void updateSheetForCW(String orderNum, String text);

    //获取所有动态数据
    List<Map> deleteGetAllMenu();
}
