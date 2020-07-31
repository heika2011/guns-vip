package cn.stylefeng.guns.sys.modular.system.model.result;

import lombok.Data;
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
public class SysMessageResult implements Serializable {

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

    private String name;

    private String userName;
}
