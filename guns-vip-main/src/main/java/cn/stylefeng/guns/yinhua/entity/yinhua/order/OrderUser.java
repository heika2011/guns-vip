package cn.stylefeng.guns.yinhua.entity.yinhua.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * 订单与员工关系
 */
@Data
@Accessors(chain = true)
@TableName("order_user")
public class OrderUser {

    //员工id
    private Long userId;
    //订单编号
    private String orderNum;
    //产生时间
    private Date createdTime;

    private Integer type;
}
