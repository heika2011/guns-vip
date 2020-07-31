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
@TableName("model_const_log")
public class ModelConstLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 裁片id
     */
    @TableField("num")
    private Integer num;

    /**
     * 价格
     */
    @TableField("consts")
    private Double consts;

    /**
     * 报价时间
     */
    @TableField("created_time")
    private Date createdTime;


    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getConsts() {
        return consts;
    }

    public void setConsts(Double consts) {
        this.consts = consts;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "ModelConstLog{" +
        "num=" + num +
        ", consts=" + consts +
        ", createdTime=" + createdTime +
        "}";
    }
}
