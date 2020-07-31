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
 * @since 2020-05-23
 */
@Data
public class PrintAreParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 打印机id
     */
    private Integer id;

    /**
     * 打印机名字
     */
    private String printName;

    /**
     * 打印机token
     */
    private String token;

    /**
     * 打印机钥匙
     */
    private String printKey;

    /**
     * 打印机简称
     */
    private String nickname;

    /**
     * 打印机端口
     */
    private Integer printPort;

    @Override
    public String checkParam() {
        return null;
    }

}
