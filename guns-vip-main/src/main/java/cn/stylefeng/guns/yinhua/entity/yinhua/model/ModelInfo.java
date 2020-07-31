package cn.stylefeng.guns.yinhua.entity.yinhua.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 款式 裁片信息
 */
@Data
@TableName("model_info")
public class ModelInfo implements Serializable {

    //裁片id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //裁片名称
    private String names;
    //款式id
    private Long num;
    //裁片颜色
    private String color;
    //裁片颜色依据
    private String colorFrom;
    //裁片大小
    private String sizes;
    //面料
    private String linling;
    //裁片位置
    private String places;
    //裁片工艺
    private String craft;
    //裁片是否拼缝
    private Integer piece;
    //备注
    private String note;
    //裁片创建时间
    private Date createdTime;
    //裁片修改时间
    private Date updateTime;
    //裁片价格
    private Double muchs;

    private String screenNum;

    private String screenType;
}
