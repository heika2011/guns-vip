package cn.stylefeng.guns.yinhua.entity.yinhua.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 订单编号生成规则
 */
@Data
public class OrderFrom implements Serializable {
    //款式编号
    private Long modelId;
    //生产单顺序
    private Integer orderNum;
}
