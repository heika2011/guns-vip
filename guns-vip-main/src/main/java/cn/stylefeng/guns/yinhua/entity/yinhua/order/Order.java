package cn.stylefeng.guns.yinhua.entity.yinhua.order;

import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrder;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 生产单表
 */
@Data
@TableName("orders")
@Accessors(chain = true)
public class Order extends RestOrder implements Serializable {

    //生产单ID
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    //款式名字
    private String modelName;
    //客户名字
    private String customerName;
    //生产单当前属于分类
    private Integer orderProg;

    //单位
    private String units;
    //生产数量
    @TableField(exist = false)
    private List<OrderNum> orderNums;
    //生产单催产次数
    private Integer urag;
    //订单状态
    private Integer status;
    //订单异常状态
    private Integer error;
    //是否有工艺单
    private Integer havSheet;
    //跟单员Id
    private Long nameId;
    //实际量
    private Long realyCount;
    //实例完成时间
    private Date reaTime;
    //报价
    private Double lastConst;

    //款式id
    private Long modelId;
    //生产单编号
    private String orderNum;

    //跟单员
    private String name;
    //生产单进度
    private Integer orderProgress;
    //生产单类型
    private Integer orderType;
    //生产单备注
    private String note;
    //下单时间
    private Date createdTime;
    //交货时间
    private Date overTime;

    //生产单数量
    private Double allcount;

    private Integer overFp;
    @TableField(exist = false)
    private String consts;
}
