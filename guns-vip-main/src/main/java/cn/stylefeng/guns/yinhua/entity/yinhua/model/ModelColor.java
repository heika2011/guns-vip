package cn.stylefeng.guns.yinhua.entity.yinhua.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 裁片配色表
 *
 */
@Data
public class ModelColor implements Serializable {

    //配色表id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //款式Id
    private Long modelId;
    //字母颜色
    private String textColor;
    //色数
    private String sum;
    //面料色
    private String color;
}
