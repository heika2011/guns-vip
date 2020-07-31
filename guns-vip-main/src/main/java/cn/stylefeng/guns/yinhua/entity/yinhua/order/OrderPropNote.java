package cn.stylefeng.guns.yinhua.entity.yinhua.order;


import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 生产单进度日志
 */
@Data
@Accessors(chain = true)
public class OrderPropNote implements Serializable {

    //生产单编号
    private String orderNum;
    //生产单是否完成
    private Integer flagDo;
    //生产单日志信息
    private String text;
    //预计完成时间
    private Date overTime;
    //发布人名字
    private String userName;

    @TableField(exist = false)
    private String typeName;
}
