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
public class OrderNumParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 款式id
     */
    private Long num;

    /**
     * 生产单编号
     */
    private String orderNum;

    /**
     * 颜色
     */
    private String color;

    /**
     * 数量
     */
    private Long sum;

    /**
     * 单位
     */
    private String units;

    /**
     * 实裁数
     */
    private Long realyCut;

    /**
     * 实点数
     */
    private Long realyPoint;

    /**
     * 坏布
     */
    private Long badLinling;

    /**
     * 印坏
     */
    private Long badF;

    /**
     * 出库时间
     */
    private Date outTime;

    @Override
    public String checkParam() {
        return null;
    }

}
