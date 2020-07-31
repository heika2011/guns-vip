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
public class OrderTeamResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 订单编号
     */
    private String orderNum;

    /**
     * 小组id
     */
    private Integer teamId;

    /**
     * 1 定位 2点数 3刮板 4总检台上 5 总检后道 6总检后道
     */
    private Integer type;

    /**
     * 员工名字
     */
    private String userName;

    /**
     * 员工Id
     */
    private Long userId;

    /**
     * 是否同意
     */
    private Integer sureFlag;

}
