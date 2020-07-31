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
@TableName("model")
@Data
public class Model implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 款式主键
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 款式编号
     */
    @TableField("num")
    private Long num;

    /**
     * 款式名称
     */
    @TableField("name")
    private String name;

    /**
     * 客户编号
     */
    @TableField("customer")
    private String customer;

    //客户名称
    @TableField(exist = false)
    private String customerName;
    /**
     * 款号
     */
    @TableField("model_num")
    private String modelNum;

    /**
     * 画号
     */
    @TableField("draw_num")
    private Long drawNum;

    /**
     * 网板数
     */
    @TableField("screen_num")
    private Integer screenNum;

    /**
     * 面料
     */
    @TableField("linling")
    private String linling;

    /**
     * 面料正反
     */
    @TableField("linling_where")
    private String linlingWhere;

    /**
     * 印花面
     */
    @TableField("yinhua_where")
    private String yinhuaWhere;

    /**
     * 面料来源
     */
    @TableField("linling_from")
    private Integer linlingFrom;

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
     * 款式状态：1，未下单，2，已打烊，3，已打产前样，3，已做大货，4，已废弃
     */
    @TableField("status")
    private Integer status;

    @TableField("created_user")
    private Long createdUser;

    @TableField("model_note")
    private String modelNote;
}
