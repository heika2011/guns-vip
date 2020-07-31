package cn.stylefeng.guns.yinhua.model.result;

import lombok.Data;
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
public class SysOperationLogResult implements Serializable {

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

}
