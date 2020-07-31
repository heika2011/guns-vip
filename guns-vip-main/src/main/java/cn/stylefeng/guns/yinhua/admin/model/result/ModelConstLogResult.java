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
public class ModelConstLogResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 裁片id
     */
    private Integer num;

    /**
     * 价格
     */
    private Integer consts;

    /**
     * 报价时间
     */
    private Date createdTime;

}
