package cn.stylefeng.guns.yinhua.admin.model.params;

import cn.stylefeng.guns.yinhua.admin.entity.Teams;
import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@Data
public class TeamsParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 小组Id
     */
    private Integer id;

    /**
     * 名字
     */
    private String name;

    /**
     * 组长id
     */
    private Integer leaderId;

    /**
     * id
     */
    private Long userId;

    /**
     * 副组长id
     */
    private Integer viceId;

    /**
     * 小组名
     */
    private String teamName;

    /**
     * 小组编号
     * @return
     */
    private Integer teamNum;

    private String condition;
    @Override
    public String checkParam() {
        return null;
    }

}
