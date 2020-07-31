package cn.stylefeng.guns.sys.modular.system.model.params;

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
 * @since 2020-03-23
 */
@Data
public class SysMessageParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 消息通知id
     */
    private Integer id;

    /**
     * 消息通知类型
     */
    private Integer type;

    /**
     * 接收角色id
     */
    private String toJob;

    /**
     * 接收员工id
     */
    private String toName;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 通知消息模板
     */
    private String msg;

    private String condition;
    @Override
    public String checkParam() {
        return null;
    }

}
