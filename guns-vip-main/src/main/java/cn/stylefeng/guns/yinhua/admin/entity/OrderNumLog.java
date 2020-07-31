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
@TableName("order_num_log")
public class OrderNumLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 生产单编号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 出货数
     */
    @TableField("out_sum")
    private Long outSum;

    /**
     * 操作时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 出货小组id
     */
    @TableField("team_id")
    private Integer teamId;

    /**
     * 颜色
     */
    @TableField("color")
    private String color;

    /**
     * 操作人名字
     */
    @TableField("name")
    private String name;

    /**
     * 类型
     */
    @TableField("type")
    private Integer type;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Long getOutSum() {
        return outSum;
    }

    public void setOutSum(Long outSum) {
        this.outSum = outSum;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "OrderNumLog{" +
        "orderNum=" + orderNum +
        ", outSum=" + outSum +
        ", createdTime=" + createdTime +
        ", teamId=" + teamId +
        ", color=" + color +
        ", name=" + name +
        ", type=" + type +
        "}";
    }
}
