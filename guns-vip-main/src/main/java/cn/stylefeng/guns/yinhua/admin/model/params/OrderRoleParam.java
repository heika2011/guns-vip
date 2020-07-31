package cn.stylefeng.guns.yinhua.admin.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
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
public class OrderRoleParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 生成类型id
     */
    private Integer id;

    /**
     * 生产类型名字
     */
    private String name;

    /**
     * 流程对应角色id
     */
    private String type;

    /**
     * 是否是名字
     */
    private Integer isname;

    /**
     * 顺序
     */
    private Integer shunxu;

    /**
     * 名字id
     */
    private Integer parentId;

    /**
     * 角色名字
     */
    private String typeName;

    private String condition;

    @Override
    public String checkParam() {
        return null;
    }

}
