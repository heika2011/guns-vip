package cn.stylefeng.guns.yinhua.entity.yinhua.order;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 生产单数量表
 */
@Data
@TableName("order_num")
@Accessors(chain = true)
public class OrderNum implements Serializable {

    //数量表id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //款式id
    private Long num;
    //生产单编号
    private String orderNum;
    //颜色
    private String color;
    //数量
    private String sum;
    //单位
    private String units;
    //实裁数
    private Long realyCut;
    //实点数
    private Long realyPoint;
    //坏布
    private Long badLinling;
    //印坏
    private Long badF;
    //出库时间
    private Date outTime;
}
