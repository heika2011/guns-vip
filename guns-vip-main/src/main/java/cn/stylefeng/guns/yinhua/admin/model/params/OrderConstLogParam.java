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
public class OrderConstLogParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 生产单编号
     */
    private String orderNum;

    /**
     * 回扣
     */
    private Double kickback;

    /**
     * 最终报价
     */
    private Double lastConst;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 单价
     */
    private Double consts;

    /**
     * 赔偿金额
     */
    private Double makeUp;

    /**
     * 版费
     */
    private Double sceenConst;

    /**
     * 备注
     */
    private String note;

    @Override
    public String checkParam() {
        return null;
    }

}
