package cn.stylefeng.guns.sys.modular.system.model.result;

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
 * @since 2020-03-24
 */
@Data
public class SysCountResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 价格计算id
     */
    private Integer id;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 值
     */
    private String value;

}
