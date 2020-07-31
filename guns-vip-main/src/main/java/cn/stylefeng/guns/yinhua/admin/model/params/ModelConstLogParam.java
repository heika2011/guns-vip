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
public class ModelConstLogParam implements Serializable, BaseValidatingParam {

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

    @Override
    public String checkParam() {
        return null;
    }

}
