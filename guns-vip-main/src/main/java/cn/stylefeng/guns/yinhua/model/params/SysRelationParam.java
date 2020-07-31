package cn.stylefeng.guns.yinhua.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 角色和菜单关联表
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
@Data
public class SysRelationParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long relationId;

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 角色id
     */
    private Long roleId;

    @Override
    public String checkParam() {
        return null;
    }

}
