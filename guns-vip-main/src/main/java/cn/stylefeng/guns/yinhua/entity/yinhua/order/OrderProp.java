package cn.stylefeng.guns.yinhua.entity.yinhua.order;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单生产进度
 */
@Data
@Accessors(chain = true)
@TableName("order_prop")
public class OrderProp implements Serializable {

    //订单编号
    private String orderNum;
    //角色对应id
    private String type;
    //角色名字
    private String typeName;
    //操作人名字
    private String name;
    //操作是否完成
    private String doOver;
    //顺序
    private String sx;
    //对应推送人员的id
    private Integer orderRoleId;
}
