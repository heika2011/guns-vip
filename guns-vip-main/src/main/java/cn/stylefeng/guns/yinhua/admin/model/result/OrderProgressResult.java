package cn.stylefeng.guns.yinhua.admin.model.result;

import lombok.Data;
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
public class OrderProgressResult implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * id
     */
    private Integer id;

    /**
     * 名称
     */
    private String name;

}
