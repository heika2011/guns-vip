package cn.stylefeng.guns.yinhua.entity.yinhua.order;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 出货更新记录
 */
@Data
@Accessors(chain = true)
public class OrderNumLog implements Serializable {

    //生产单编号
    private String orderNum;
    //出货数
    private Double outSum;
    //操作时间
    private Date createdTime;
    //小组id
    private Integer teamId;
    //颜色
    private String color;
    //操作人名字
    private String name;
    //类型
    private Integer type;
}
