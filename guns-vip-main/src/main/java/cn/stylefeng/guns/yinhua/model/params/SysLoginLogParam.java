package cn.stylefeng.guns.yinhua.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 登录记录
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
@Data
public class SysLoginLogParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long loginLogId;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 管理员id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否执行成功
     */
    private String succeed;

    /**
     * 具体消息
     */
    private String message;

    /**
     * 登录ip
     */
    private String ipAddress;

    @Override
    public String checkParam() {
        return null;
    }

}
