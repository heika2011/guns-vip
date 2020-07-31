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
@TableName("order_prop")
public class OrderProp implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 生产单编号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 角色对应id
     */
    @TableField("type")
    private String type;

    /**
     * 角色对应名字
     */
    @TableField("type_name")
    private String typeName;

    /**
     * 操作人员名字
     */
    @TableField("name")
    private String name;

    /**
     * 操作是否完毕
     */
    @TableField("do_over")
    private String doOver;

    /**
     * 顺序
     */
    @TableField("sx")
    private String sx;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDoOver() {
        return doOver;
    }

    public void setDoOver(String doOver) {
        this.doOver = doOver;
    }

    public String getSx() {
        return sx;
    }

    public void setSx(String sx) {
        this.sx = sx;
    }

    @Override
    public String toString() {
        return "OrderProp{" +
        "orderNum=" + orderNum +
        ", type=" + type +
        ", typeName=" + typeName +
        ", name=" + name +
        ", doOver=" + doOver +
        ", sx=" + sx +
        "}";
    }
}
