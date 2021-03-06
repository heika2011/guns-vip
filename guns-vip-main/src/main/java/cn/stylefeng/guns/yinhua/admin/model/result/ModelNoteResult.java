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
 * @since 2020-03-14
 */
@Data
public class ModelNoteResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 裁片日志id
     */
    private Integer id;

    /**
     * 款式编号
     */
    private Long num;

    /**
     * 裁片内容
     */
    private String text;

    /**
     * 裁片图片
     */
    private String image;

    /**
     * 日志发起人姓名
     */
    private String name;

    /**
     * 日志创建时间
     */
    private Date createdTime;

}
