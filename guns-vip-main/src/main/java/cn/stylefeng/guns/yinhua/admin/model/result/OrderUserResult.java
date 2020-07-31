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
public class OrderUserResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 用户id
     */
    private Long userId;

    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 产生时间
     */
    private Date createdTime;

    /**
     * 打样或者大货
     */
    private Integer type;

}
