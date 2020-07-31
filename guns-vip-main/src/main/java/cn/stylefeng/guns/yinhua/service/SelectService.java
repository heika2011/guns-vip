package cn.stylefeng.guns.yinhua.service;

import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestPageAndData;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.Date;

/**
 * 搜索接口
 */
public interface SelectService {
    /**
     * 生产单列表
     */
    RestPageAndData getCreateOrderList(Long page, Long pageSize, String px, Integer status, Integer type, Integer sort, String keyWord);

    /**
     * 制版列表
     */
    Page<Order> getPlateMakingData(Long page, Long pageSize, String px, Integer status);

    /**
     * 晒版列表
     */
    Page<Order> getPrintingDownData(Long page, Long pageSize, String px, Integer status);

    /**
     * 丝印调色列表
     */
    Page<Order> getScreenPrintingColorData(Long page, Long pageSize, String px, Integer status);

    /**
     * 数码调色列表
     */
    Page<Order> getDigitalColorData(Long page, Long pageSize, String px, Integer status);

    /**
     * 分配打样订单列表
     */
    Page<Order> getDispatchOrderData(Long page, Long pageSize, String px, Integer status);

    /**
     * 打样列表
     */
    Page<Order> getPattenData(Long page, Long pageSize, String px, Integer status);

    /**
     * 分配生产列表
     */
    Page<Order> getPDispatchOrderData(Long page, Long pageSize, String px, Integer status);

    /**
     * 生产组长列表
     */
    Page<Order> getTeamLeaderData(Long page, Long pageSize, String px, Integer status,String keyWord);

    /**
     * 报价列表
     */
    Page<Order> getQuotationData(Long page, Long pageSize, String px, Integer status,String keyWord,Integer type,Integer sort);

    /**
     * 财务列表
     */
    Page<Order> getFinanceData(Long page, Long pageSize, String px, Integer type,Integer sort,String keyWord);

    /**
     * 司机列表
     */
    Page<Order> getDeliveryData(Long page, Long pageSize, String px, Integer status);

    /**
     * 生产组员列表
     */
    Page<Order> getMemberList(Long page, Long pageSize, String px, Integer status);

    /**
     * 质量监控列表
     */
    Page<Order> getQualityMonitoringOrderData(Long page, Long pageSize, Integer status, Date startTime, Date overTime);

    /**
     * 生产监控列表
     */
    Page<Order> getProductionMonitoringData(Long page, Long pageSize, Integer type,String value);

    /**
     * 总检列表
     */
    Page<Order> getCheckData(Long page, Long pageSize, Object o, Integer status);
}
