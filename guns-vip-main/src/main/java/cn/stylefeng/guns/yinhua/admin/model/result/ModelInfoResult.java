package cn.stylefeng.guns.yinhua.admin.model.result;

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
public class ModelInfoResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 裁片id
     */
    private Integer id;

    /**
     * 款式id
     */
    private Long num;

    /**
     * 裁片颜色
     */
    private String color;

    /**
     * 裁片依据；1,，按文件，2，按纸样，3，按点位，4，按样衣
     */
    private String colorFrom;

    /**
     * 裁片大小
     */
    private String sizes;

    /**
     * 裁片位置
     */
    private String places;

    /**
     * 工艺：1，手工，2，数码
     */
    private String craft;

    /**
     * 有无拼缝：1，有 2，无
     */
    private Integer piece;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 裁片名称
     */
    private String names;

    /**
     * 裁片单价
     */
    private Integer muchs;

    /**
     * 备注
     */
    private String note;

    //面料
    private String linling;

    private String screenNum;
    private String screenType;
}
