package cn.stylefeng.guns.yinhua.entity.yinhua.order;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 订单与小组关系
 */
@Data
@Accessors(chain = true)
public class OrderTeam implements Serializable {

    //订单编号
    private String orderNum;
    //小组id
    private Integer teamId;
    //类型
    private Integer type;
    //员工名字
    private String userName;
    //员工id
    private Long userId;
    //是否同意请求
    private Integer sureFlag;

    @TableField(exist = false)
    private String user;
}
