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
public class OrderSheetResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 生产单编号
     */
    private String orderNum;

    /**
     * 工艺单地址
     */
    private String url;

}
