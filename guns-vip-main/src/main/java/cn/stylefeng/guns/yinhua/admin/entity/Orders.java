package cn.stylefeng.guns.yinhua.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 款式
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@TableName("orders")
public class Orders implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 生产单id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 款式id
     */
    @TableField("model_id")
    private Long modelId;

    /**
     * 客户名字
     */
    @TableField("customer_name")
    private String customerName;

    /**
     * 款式名字
     */
    @TableField("model_name")
    private String modelName;

    /**
     * 生产单编号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 跟单员
     */
    @TableField("name")
    private String name;

    /**
     * 订单进度1,打样 2，产前样，3，首单 4，翻单
     */
    @TableField("order_progress")
    private Integer orderProgress;

    /**
     * 订单类型 id
     */
    @TableField("order_type")
    private Integer orderType;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 催产次数
     */
    @TableField("urag")
    private Integer urag;

    /**
     * 订单状态 小于 4 代表报价 大于4代表财务
     */
    @TableField("status")
    private Integer status;

    /**
     * 下单时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 交货时间
     */
    @TableField("over_time")
    private Date overTime;

    /**
     * 生产单问题情况
     */
    @TableField("error")
    private Integer error;

    /**
     * 0 未下单， 1，打样，2，大货，3，问题单
     */
    @TableField("order_prog")
    private Integer orderProg;

    /**
     * 单位
     */
    @TableField("units")
    private String units;

    /**
     * 数量
     */
    @TableField("allcount")
    private Double allcount;

    /**
     * 是否有工艺单
     */
    @TableField("hav_sheet")
    private Integer havSheet;

    /**
     * 跟单员Id
     */
    @TableField("name_id")
    private Long nameId;

    /**
     * 实际量
     */
    @TableField("realy_count")
    private Long realyCount;

    /**
     * 实例完成时间
     */
    @TableField("rea_time")
    private Date reaTime;

    /**
     * 最后报价
     */
    @TableField("last_const")
    private Long lastConst;


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

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderProgress() {
        return orderProgress;
    }

    public void setOrderProgress(Integer orderProgress) {
        this.orderProgress = orderProgress;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getUrag() {
        return urag;
    }

    public void setUrag(Integer urag) {
        this.urag = urag;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getOverTime() {
        return overTime;
    }

    public void setOverTime(Date overTime) {
        this.overTime = overTime;
    }

    public Integer getError() {
        return error;
    }

    public void setError(Integer error) {
        this.error = error;
    }

    public Integer getOrderProg() {
        return orderProg;
    }

    public void setOrderProg(Integer orderProg) {
        this.orderProg = orderProg;
    }

    public String getUnits() {
        return units;
    }

    public void setUnits(String units) {
        this.units = units;
    }

    public Double getAllcount() {
        return allcount;
    }

    public void setAllcount(Double allcount) {
        this.allcount = allcount;
    }

    public Integer getHavSheet() {
        return havSheet;
    }

    public void setHavSheet(Integer havSheet) {
        this.havSheet = havSheet;
    }

    public Long getNameId() {
        return nameId;
    }

    public void setNameId(Long nameId) {
        this.nameId = nameId;
    }

    public Long getRealyCount() {
        return realyCount;
    }

    public void setRealyCount(Long realyCount) {
        this.realyCount = realyCount;
    }

    public Date getReaTime() {
        return reaTime;
    }

    public void setReaTime(Date reaTime) {
        this.reaTime = reaTime;
    }

    public Long getLastConst() {
        return lastConst;
    }

    public void setLastConst(Long lastConst) {
        this.lastConst = lastConst;
    }

    @Override
    public String toString() {
        return "Orders{" +
        "id=" + id +
        ", modelId=" + modelId +
        ", customerName=" + customerName +
        ", modelName=" + modelName +
        ", orderNum=" + orderNum +
        ", name=" + name +
        ", orderProgress=" + orderProgress +
        ", orderType=" + orderType +
        ", note=" + note +
        ", urag=" + urag +
        ", status=" + status +
        ", createdTime=" + createdTime +
        ", overTime=" + overTime +
        ", error=" + error +
        ", orderProg=" + orderProg +
        ", units=" + units +
        ", allcount=" + allcount +
        ", havSheet=" + havSheet +
        ", nameId=" + nameId +
        ", realyCount=" + realyCount +
        ", reaTime=" + reaTime +
        ", lastConst=" + lastConst +
        "}";
    }
}
