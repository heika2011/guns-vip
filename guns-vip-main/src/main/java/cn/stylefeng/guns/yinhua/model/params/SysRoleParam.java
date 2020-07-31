package cn.stylefeng.guns.yinhua.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
@Data
public class SysRoleParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private Long roleId;

    /**
     * 父角色id
     */
    private Long pid;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 提示
     */
    private String description;

    /**
     * 序号
     */
    private Integer sort;

    /**
     * 乐观锁
     */
    private Integer version;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建用户
     */
    private Long createUser;

    /**
     * 修改用户
     */
    private Long updateUser;

    @Override
    public String checkParam() {
        return null;
    }

}
