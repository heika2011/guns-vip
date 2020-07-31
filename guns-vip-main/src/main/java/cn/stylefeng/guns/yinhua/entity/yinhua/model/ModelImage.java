package cn.stylefeng.guns.yinhua.entity.yinhua.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 裁片图片信息
 */
@Data
@TableName("model_image")
public class ModelImage implements Serializable {

    //图片id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //裁片信息id
    private Integer modelinfoId;
    //对应位置
    private Integer type;
    //图片地址
    private String url;
    //款式编号
    private Long num;
}
