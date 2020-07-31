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
@TableName("order_prop_note")
public class OrderPropNote implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 生产单编号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 是否完成
     */
    @TableField("flag_do")
    private Integer flagDo;

    /**
     * 内容
     */
    @TableField("text")
    private String text;

    /**
     * 预计结束时间
     */
    @TableField("over_time")
    private Date overTime;

    /**
     * 名字
     */
    @TableField("user_name")
    private String userName;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getFlagDo() {
        return flagDo;
    }

    public void setFlagDo(Integer flagDo) {
        this.flagDo = flagDo;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "OrderPropNote{" +
        "orderNum=" + orderNum +
        ", flagDo=" + flagDo +
        ", text=" + text +
        ", overTime=" + overTime +
        ", userName=" + userName +
        "}";
    }
}
