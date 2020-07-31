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
public class OrderPropResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 生产单编号
     */
    private String orderNum;

    /**
     * 角色对应id
     */
    private String type;

    /**
     * 角色对应名字
     */
    private String typeName;

    /**
     * 操作人员名字
     */
    private String name;

    /**
     * 操作是否完毕
     */
    private String doOver;

    /**
     * 顺序
     */
    private String sx;

}
