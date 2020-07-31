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
@TableName("order_from")
public class OrderFrom implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 款式编号
     */
    @TableField("model_id")
    private Long modelId;

    /**
     * 生产单顺序
     */
    @TableField("order_num")
    private Integer orderNum;


    public Long getModelId() {
        return modelId;
    }

    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "OrderFrom{" +
        "modelId=" + modelId +
        ", orderNum=" + orderNum +
        "}";
    }
}
