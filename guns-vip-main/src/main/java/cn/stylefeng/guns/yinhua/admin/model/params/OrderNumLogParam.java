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
public class OrderNumLogParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 生产单编号
     */
    private String orderNum;

    /**
     * 出货数
     */
    private Long outSum;

    /**
     * 操作时间
     */
    private Date createdTime;

    /**
     * 出货小组id
     */
    private Integer teamId;

    /**
     * 颜色
     */
    private String color;

    /**
     * 操作人名字
     */
    private String name;

    /**
     * 类型
     */
    private Integer type;

    @Override
    public String checkParam() {
        return null;
    }

}
