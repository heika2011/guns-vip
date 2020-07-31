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
public class OrderPropNoteParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 生产单编号
     */
    private String orderNum;

    /**
     * 是否完成
     */
    private Integer flagDo;

    /**
     * 内容
     */
    private String text;

    /**
     * 预计结束时间
     */
    private Date overTime;

    /**
     * 名字
     */
    private String userName;

    @Override
    public String checkParam() {
        return null;
    }

}
