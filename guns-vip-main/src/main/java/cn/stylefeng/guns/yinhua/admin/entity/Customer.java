package cn.stylefeng.guns.yinhua.admin.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author xiexin
 * @since 2020-03-19
 */
@TableName("customer")
public class Customer implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 客户编号
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 客户编号
     */
    @TableField("num")
    private String num;

    /**
     * 客户名称
     */
    @TableField("name")
    private String name;

    /**
     * 联系人
     */
    @TableField("contact")
    private String contact;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 联系地址
     */
    @TableField("addr")
    private String addr;

    /**
     * 等级:1一般，2普通，3重要
     */
    @TableField("level")
    private Integer level;

    /**
     * 要求:1严格，2普通
     */
    @TableField("requires")
    private Integer requires;

    /**
     * 结算方式:1月结，2现金
     */
    @TableField("settle")
    private Integer settle;

    /**
     * 备注
     */
    @TableField("note")
    private String note;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    @TableField("created_user")
    private Long createdUser;

    public Long getCreatedUser() {
        return createdUser;
    }

    public void setCreatedUser(Long createdUser) {
        this.createdUser = createdUser;
    }

    private String openId;

    private String wName;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getwName() {
        return wName;
    }

    public void setwName(String wName) {
        this.wName = wName;
    }

    /**
     * 修改时间
     */
      @TableField(value = "update_time", fill = FieldFill.UPDATE)
    private Date updateTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddr() {
        return addr;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getRequires() {
        return requires;
    }

    public void setRequires(Integer requires) {
        this.requires = requires;
    }

    public Integer getSettle() {
        return settle;
    }

    public void setSettle(Integer settle) {
        this.settle = settle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
        "id=" + id +
        ", num=" + num +
        ", name=" + name +
        ", contact=" + contact +
        ", phone=" + phone +
        ", addr=" + addr +
        ", level=" + level +
        ", requires=" + requires +
        ", settle=" + settle +
        ", note=" + note +
        ", createdTime=" + createdTime +
        ", updateTime=" + updateTime +
        "}";
    }
}
