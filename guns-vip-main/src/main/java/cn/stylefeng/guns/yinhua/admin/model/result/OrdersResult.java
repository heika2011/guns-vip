package cn.stylefeng.guns.yinhua.admin.model.result;

import cn.stylefeng.guns.yinhua.admin.entity.Model;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 款式
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@Data
public class OrdersResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 生产单id
     */
    private Integer id;

    /**
     * 款式id
     */
    private Long modelId;

    /**
     * 客户名字
     */
    private String customerName;

    private ModelResult model;
    /**
     * 款式名字
     */
    private String modelName;

    /**
     * 生产单编号
     */
    private String orderNum;

    /**
     * 跟单员
     */
    private String name;

    /**
     * 订单进度1,打样 2，产前样，3，首单 4，翻单
     */
    private Integer orderProgress;

    /**
     * 订单类型 id
     */
    private Integer orderType;

    /**
     * 备注
     */
    private String note;

    /**
     * 催产次数
     */
    private Integer urag;

    /**
     * 订单状态 小于 4 代表报价 大于4代表财务
     */
    private Integer status;

    /**
     * 下单时间
     */
    private Date createdTime;

    /**
     * 交货时间
     */
    private Date overTime;

    /**
     * 生产单问题情况
     */
    private Integer error;

    /**
     * 0 未下单， 1，打样，2，大货，3，问题单
     */
    private Integer orderProg;

    /**
     * 单位
     */
    private String units;

    /**
     * 数量
     */
    private Double allcount;

    /**
     * 是否有工艺单
     */
    private Integer havSheet;

    /**
     * 跟单员Id
     */
    private Long nameId;

    /**
     * 实际量
     */
    private Long realyCount;

    /**
     * 实例完成时间
     */
    private Date reaTime;

    /**
     * 最后报价
     */
    private Long lastConst;

}
