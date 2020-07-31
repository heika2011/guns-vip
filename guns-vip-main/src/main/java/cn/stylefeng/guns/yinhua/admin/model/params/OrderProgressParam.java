package cn.stylefeng.guns.yinhua.admin.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 进度表信息
 * </p>
 *
 * @author xiexin
 * @since 2020-04-29
 */
@Data
public class OrderProgressParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

    @Override
    public String checkParam() {
        return null;
    }

}
