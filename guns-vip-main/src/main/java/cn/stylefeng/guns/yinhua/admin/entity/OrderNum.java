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
@TableName("order_num")
public class OrderNum implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 款式id
     */
    @TableField("num")
    private Long num;

    /**
     * 生产单编号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 颜色
     */
    @TableField("color")
    private String color;

    /**
     * 数量
     */
    @TableField("sum")
    private Long sum;

    /**
     * 单位
     */
    @TableField("units")
    private String units;

    /**
     * 实裁数
     */
    @TableField("realy_cut")
    private Long realyCut;

    /**
     * 实点数
     */
    @TableField("realy_point")
    private Long realyPoint;

    /**
     * 坏布
     */
    @TableField("bad_linling")
    private Long badLinling;

    /**
     * 印坏
     */
    @TableField("bad_f")
    private Long badF;

    /**
     * 出库时间
     */
    @TableField("out_time")
    private Date outTime;


    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getSum() {
        return sum;
    }

    public void setSum(Long sum) {
        this.sum = sum;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Long getRealyCut() {
        return realyCut;
    }

    public void setRealyCut(Long realyCut) {
        this.realyCut = realyCut;
    }

    public Long getRealyPoint() {
        return realyPoint;
    }

    public void setRealyPoint(Long realyPoint) {
        this.realyPoint = realyPoint;
    }

    public Long getBadLinling() {
        return badLinling;
    }

    public void setBadLinling(Long badLinling) {
        this.badLinling = badLinling;
    }

    public Long getBadF() {
        return badF;
    }

    public void setBadF(Long badF) {
        this.badF = badF;
    }

    public Date getOutTime() {
        return outTime;
    }

    public void setOutTime(Date outTime) {
        this.outTime = outTime;
    }

    @Override
    public String toString() {
        return "OrderNum{" +
        "num=" + num +
        ", orderNum=" + orderNum +
        ", color=" + color +
        ", sum=" + sum +
        ", units=" + units +
        ", realyCut=" + realyCut +
        ", realyPoint=" + realyPoint +
        ", badLinling=" + badLinling +
        ", badF=" + badF +
        ", outTime=" + outTime +
        "}";
    }
}
