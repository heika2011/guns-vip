package cn.stylefeng.guns.yinhua.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
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
@TableName("model_note")
public class ModelNote implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 裁片日志id
     */
      @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 款式编号
     */
    @TableField("num")
    private Long num;

    /**
     * 裁片内容
     */
    @TableField("text")
    private String text;

    /**
     * 裁片图片
     */
    @TableField("image")
    private String image;

    /**
     * 日志发起人姓名
     */
    @TableField("name")
    private String name;

    /**
     * 日志创建时间
     */
    @TableField("created_time")
    private Date createdTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ModelNote{" +
        "id=" + id +
        ", num=" + num +
        ", text=" + text +
        ", image=" + image +
        ", name=" + name +
        ", createdTime=" + createdTime +
        "}";
    }
}
