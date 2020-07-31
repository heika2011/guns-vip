package cn.stylefeng.guns.yinhua.admin.model.result;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@Data
public class ModelResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 款式主键
     */
    private Integer id;

    /**
     * 款式编号
     */
    private Long num;

    /**
     * 款式名称
     */
    private String name;

    /**
     * 客户编号
     */
    private String customer;

    //客户名称
    private String customerName;
    /**
     * 款号
     */
    private String modelNum;

    /**
     * 画号
     */
    private Long drawNum;

    /**
     * 网板数
     */
    private Integer screenNum;

    /**
     * 面料
     */
    private String linling;

    /**
     * 面料正反
     */
    private String linlingWhere;

    /**
     * 印花面
     */
    private String yinhuaWhere;

    /**
     * 面料来源
     */
    private Integer linlingFrom;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 款式状态：1，未下单，2，已打烊，3，已打产前样，3，已做大货，4，已废弃
     */
    private Integer status;
    private Long createdUser;
    private String modelNote;
}
