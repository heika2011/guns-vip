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
@TableName("order_const_log")
public class OrderConstLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 生产单编号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 回扣
     */
    @TableField("kickback")
    private Double kickback;

    /**
     * 最终报价
     */
    @TableField("last_const")
    private Double lastConst;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 单价
     */
    @TableField("consts")
    private Double consts;

    /**
     * 赔偿金额
     */
    @TableField("make_up")
    private Double makeUp;

    /**
     * 版费
     */
    @TableField("sceen_const")
    private Double sceenConst;

    /**
     * 备注
     */
    @TableField("note")
    private String note;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Double getKickback() {
        return kickback;
    }

    public void setKickback(Double kickback) {
        this.kickback = kickback;
    }

    public Double getLastConst() {
        return lastConst;
    }

    public void setLastConst(Double lastConst) {
        this.lastConst = lastConst;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Double getConsts() {
        return consts;
    }

    public void setConsts(Double consts) {
        this.consts = consts;
    }

    public Double getMakeUp() {
        return makeUp;
    }

    public void setMakeUp(Double makeUp) {
        this.makeUp = makeUp;
    }

    public Double getSceenConst() {
        return sceenConst;
    }

    public void setSceenConst(Double sceenConst) {
        this.sceenConst = sceenConst;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    @Override
    public String toString() {
        return "OrderConstLog{" +
        "orderNum=" + orderNum +
        ", kickback=" + kickback +
        ", lastConst=" + lastConst +
        ", createdTime=" + createdTime +
        ", consts=" + consts +
        ", makeUp=" + makeUp +
        ", sceenConst=" + sceenConst +
        ", note=" + note +
        "}";
    }
}
