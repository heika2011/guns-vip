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
@TableName("order_sheet")
public class OrderSheet implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 工艺单id
     */
    @TableField("id")
    private Long id;
    /**
     * 生产单编号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 工艺单地址
     */
    @TableField("url")
    private String url;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "OrderSheet{" +
        "orderNum=" + orderNum +
        ", url=" + url +
        "}";
    }
}
