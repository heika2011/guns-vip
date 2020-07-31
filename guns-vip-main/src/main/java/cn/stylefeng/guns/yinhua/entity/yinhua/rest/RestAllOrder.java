package cn.stylefeng.guns.yinhua.entity.yinhua.rest;


import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelColor;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderNum;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderProp;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 生产单详情整合
 */
@Data
@Accessors(chain = true)
public class RestAllOrder{

    //model信息
    private RestAllModel restAllModel;
    //订单信息
    private RestOrder restOrder;
    //订单数量表
    private List<OrderNum> orderNums;
    //配色表组件
    private List<ModelColor> modelColors;
    //生产进度
    private List<OrderProp> orderProps;
}
