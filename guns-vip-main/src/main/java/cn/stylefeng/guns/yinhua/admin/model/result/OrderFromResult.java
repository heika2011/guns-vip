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
public class OrderFromResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 款式编号
     */
    private Long modelId;

    /**
     * 生产单顺序
     */
    private Integer orderNum;

}
