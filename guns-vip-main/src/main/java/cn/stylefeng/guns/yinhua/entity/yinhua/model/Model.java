package cn.stylefeng.guns.yinhua.entity.yinhua.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 款式数据
 */
@Data
@TableName("model")
public class Model implements Serializable {
    //款式id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //款式编号
    private Long num;
    //款式名称
    private String name;
    //客户编号
    private String customer;
    //客户重要等级
    @TableField(exist = false)
    private Integer level;
    //客户名称
    @TableField(exist = false)
    private String customerName;
    //款号
    private String modelNum;
    //画号
    private Long drawNum;
    //网板数
    private Integer screenNum;
    //面料
    private String linling;
    //面料位置
    private String linlingWhere;
    //印花面
    private String yinhuaWhere;
    //面料来源
    private Integer linlingFrom;
    //创建时间
    private Date createdTime;
    //修改时间
    private Date updateTime;
    //款式状态
    private Integer status;

    private Long createdUser;

    private String modelNote;
}
