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
public class OrderRoleResult implements Serializable {

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

}
