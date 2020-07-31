package cn.stylefeng.guns.yinhua.admin.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author xiexin
 * @since 2020-03-19
 */
@Data
public class CustomerResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 客户编号
     */
    private Integer id;

    /**
     * 客户编号
     */
    private String num;

    /**
     * 客户名称
     */
    private String name;

    /**
     * 联系人
     */
    private String contact;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 联系地址
     */
    private String addr;

    /**
     * 等级:1一般，2普通，3重要
     */
    private Integer level;

    /**
     * 要求:1严格，2普通
     */
    private Integer requires;

    /**
     * 结算方式:1月结，2现金
     */
    private Integer settle;

    /**
     * 备注
     */
    private String note;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    private String openId;

    private String wName;
    private Long createdUser;
}
