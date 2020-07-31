package cn.stylefeng.guns.sys.modular.system.model.entity;

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
 * @since 2020-03-23
 */
@TableName("sys_message")
public class SysMessage implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 消息通知id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 消息通知类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 接收角色id
     */
    @TableField("to_job")
    private String toJob;

    /**
     * 接收员工id
     */
    @TableField("to_name")
    private String toName;

    /**
     * 创建时间
     */
    @TableField("created_time")
    private Date createdTime;

    /**
     * 通知消息模板
     */
    @TableField("msg")
    private String msg;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getToJob() {
        return toJob;
    }

    public void setToJob(String toJob) {
        this.toJob = toJob;
    }

    public String getToName() {
        return toName;
    }

    public void setToName(String toName) {
        this.toName = toName;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "SysMessage{" +
        "id=" + id +
        ", type=" + type +
        ", toJob=" + toJob +
        ", toName=" + toName +
        ", createdTime=" + createdTime +
        ", msg=" + msg +
        "}";
    }
}
