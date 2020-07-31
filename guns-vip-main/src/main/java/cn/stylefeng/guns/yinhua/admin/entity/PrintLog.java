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
 * @since 2020-04-18
 */
@TableName("print_log")
public class PrintLog implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 日志id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 操作人名字
     */
    @TableField("name")
    private String name;

    /**
     * 文件地址
     */
    @TableField("url")
    private String url;

    /**
     * 打印状态
     */
    @TableField("status")
    private Integer status;

    /**
     * 打印时间
     */
    @TableField("created_time")
    private Date createdTime;

    @TableField("order_num")
    private String orderNum;

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    @Override
    public String toString() {
        return "PrintLog{" +
        "id=" + id +
        ", name=" + name +
        ", url=" + url +
        ", status=" + status +
        ", createdTime=" + createdTime +
        "}";
    }
}
