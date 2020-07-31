package cn.stylefeng.guns.yinhua.model.result;

import lombok.Data;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 菜单表
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
@Data
public class SysMenuResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private Long menuId;

    /**
     * 菜单编号
     */
    private String code;

    /**
     * 菜单父编号
     */
    private String pcode;

    /**
     * 当前菜单的所有父菜单编号
     */
    private String pcodes;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * url地址
     */
    private String url;

    /**
     * 菜单排序号
     */
    private Integer sort;

    /**
     * 菜单层级
     */
    private Integer levels;

    /**
     * 是否是菜单(字典)
     */
    private String menuFlag;

    /**
     * 备注
     */
    private String description;

    /**
     * 菜单状态(字典)
     */
    private String status;

    /**
     * 是否打开新页面的标识(字典)
     */
    private String newPageFlag;

    /**
     * 是否打开(字典)
     */
    private String openFlag;

    /**
     * 系统分类(字典)
     */
    private String systemType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
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
