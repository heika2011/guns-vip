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
public class ModelImageParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 裁片图片id
     */
    private Integer id;

    /**
     * 裁片信息Id
     */
    private Integer modelinfoId;

    /**
     * 裁片对应位置
     */
    private Integer type;

    /**
     * 图片url
     */
    private String url;

    /**
     * 款式编号
     */
    private Long num;

    @Override
    public String checkParam() {
        return null;
    }

}
