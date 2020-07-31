package cn.stylefeng.guns.yinhua.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 基础字典
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
@Data
public class SysDictResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 字典id
     */
    private Long dictId;

    /**
     * 所属字典类型的id
     */
    private Long dictTypeId;

    /**
     * 字典编码
     */
    private String code;

    /**
     * 字典名称
     */
    private String name;

    /**
     * 上级代码id
     */
    private Long parentId;

    /**
     * 所有上级id
     */
    private String parentIds;

    /**
     * 状态（字典）
     */
    private String status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 字典的描述
     */
    private String description;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private Long createUser;

    /**
     * 修改人
     */
    private Long updateUser;

}
