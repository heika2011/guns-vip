package cn.stylefeng.guns.yinhua.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 字典类型表
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
@Data
public class SysDictTypeResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 字典类型id
     */
    private Long dictTypeId;

    /**
     * 字典类型编码
     */
    private String code;

    /**
     * 字典类型名称
     */
    private String name;

    /**
     * 字典描述
     */
    private String description;

    /**
     * 是否是系统字典，Y-是，N-否
     */
    private String systemFlag;

    /**
     * 状态(字典)
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 添加时间
     */
    private Date createTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 修改人
     */
    private Long updateUser;

}
