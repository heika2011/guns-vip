package cn.stylefeng.guns.yinhua.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 操作日志
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
@Data
public class SysOperationLogParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Long operationLogId;

    /**
     * 日志类型(字典)
     */
    private String logType;

    /**
     * 日志名称
     */
    private String logName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 类名称
     */
    private String className;

    /**
     * 方法名称
     */
    private String method;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 是否成功(字典)
     */
    private String succeed;

    /**
     * 备注
     */
    private String message;

    @Override
    public String checkParam() {
        return null;
    }

}
