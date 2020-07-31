package cn.stylefeng.guns.yinhua.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@TableName("model_info")
@Data
public class ModelInfo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 裁片id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 款式id
     */
    @TableField("num")
    private Long num;

    /**
     * 裁片颜色
     */
    @TableField("color")
    private String color;

    /**
     * 裁片依据
     */
    @TableField("color_from")
    private String colorFrom;

    /**
     * 裁片大小
     */
    @TableField("sizes")
    private String sizes;

    /**
     * 裁片位置
     */
    @TableField("places")
    private String places;

    /**
     * 工艺：1，手工，2，数码
     */
    @TableField("craft")
    private String craft;

    /**
     * 有无拼缝：1，有 2，无
     */
    @TableField("piece")
    private Integer piece;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 修改时间
     */
      @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;

    /**
     * 裁片名称
     */
    @TableField("names")
    private String names;

    /**
     * 裁片单价
     */
    @TableField("muchs")
    private Integer muchs;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    //面料
    @TableField("linling")
    private String linling;

    @TableField("screen_num")
    private String screenNum;

    @TableField("screen_type")
    private String screenType;
}
