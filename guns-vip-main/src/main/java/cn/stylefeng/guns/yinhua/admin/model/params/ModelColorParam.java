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
public class ModelColorParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 款式配色id
     */
    private Integer id;

    /**
     * 款式Id
     */
    private Long modelId;

    /**
     * 字母颜色
     */
    private String textColor;

    /**
     * 色数
     */
    private String sum;

    /**
     * 面料色
     */
    private String color;

    @Override
    public String checkParam() {
        return null;
    }

}
