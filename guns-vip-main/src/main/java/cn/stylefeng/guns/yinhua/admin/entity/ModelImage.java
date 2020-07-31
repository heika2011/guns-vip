package cn.stylefeng.guns.yinhua.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@TableName("model_image")
public class ModelImage implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 裁片图片id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 裁片信息Id
     */
    @TableField("modelinfo_id")
    private Integer modelinfoId;

    /**
     * 裁片对应位置
     */
    @TableField("type")
    private Integer type;

    /**
     * 图片url
     */
    @TableField("url")
    private String url;

    /**
     * 款式编号
     */
    @TableField("num")
    private Long num;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getModelinfoId() {
        return modelinfoId;
    }

    public void setModelinfoId(Integer modelinfoId) {
        this.modelinfoId = modelinfoId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ModelImage{" +
        "id=" + id +
        ", modelinfoId=" + modelinfoId +
        ", type=" + type +
        ", url=" + url +
        ", num=" + num +
        "}";
    }
}
