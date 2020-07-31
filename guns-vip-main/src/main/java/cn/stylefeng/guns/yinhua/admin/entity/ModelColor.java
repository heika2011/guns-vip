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
@TableName("model_color")
public class ModelColor implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 款式配色id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 款式Id
     */
    @TableField("model_id")
    private Long modelId;

    /**
     * 字母颜色
     */
    @TableField("text_color")
    private String textColor;

    /**
     * 色数
     */
    @TableField("sum")
    private String sum;

    /**
     * 面料色
     */
    @TableField("color")
    private String color;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "ModelColor{" +
        "id=" + id +
        ", modelId=" + modelId +
        ", textColor=" + textColor +
        ", sum=" + sum +
        ", color=" + color +
        "}";
    }
}
