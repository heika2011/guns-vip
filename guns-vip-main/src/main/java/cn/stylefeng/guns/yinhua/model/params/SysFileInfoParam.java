package cn.stylefeng.guns.yinhua.model.params;

import lombok.Data;
import cn.stylefeng.roses.kernel.model.validator.BaseValidatingParam;
import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 文件信息表
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
@Data
public class SysFileInfoParam implements Serializable, BaseValidatingParam {

    private static final long serialVersionUID = 1L;


    /**
     * 主键id
     */
    private String fileId;

    /**
     * 文件仓库（oss仓库）
     */
    private String fileBucket;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件后缀
     */
    private String fileSuffix;

    /**
     * 文件大小kb
     */
    private Long fileSizeKb;

    /**
     * 文件唯一标识id
     */
    private String finalName;

    /**
     * 存储路径
     */
    private String filePath;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 创建用户
     */
    private Long createUser;

    /**
     * 修改用户
     */
    private Long updateUser;

    @Override
    public String checkParam() {
        return null;
    }

}
