package cn.stylefeng.guns.yinhua.admin.model.result;

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
 * @since 2020-04-18
 */
@Data
public class PrintLogResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 日志id
     */
    private Integer id;

    /**
     * 操作人名字
     */
    private String name;

    /**
     * 文件地址
     */
    private String url;

    /**
     * 打印状态
     */
    private Integer status;

    /**
     * 打印时间
     */
    private Date createdTime;
    private String orderNum;
}
