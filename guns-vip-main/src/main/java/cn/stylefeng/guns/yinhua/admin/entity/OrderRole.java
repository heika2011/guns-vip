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
@TableName("order_role")
public class OrderRole implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 生成类型id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 生产类型名字
     */
    @TableField("name")
    private String name;

    /**
     * 流程对应角色id
     */
    @TableField("type")
    private String type;

    /**
     * 是否是名字
     */
    @TableField("isname")
    private Integer isname;

    /**
     * 顺序
     */
    @TableField("shunxu")
    private Integer shunxu;

    /**
     * 名字id
     */
    @TableField("parent_id")
    private Integer parentId;

    /**
     * 角色名字
     */
    @TableField("type_name")
    private String typeName;


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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getIsname() {
        return isname;
    }

    public void setIsname(Integer isname) {
        this.isname = isname;
    }

    public Integer getShunxu() {
        return shunxu;
    }

    public void setShunxu(Integer shunxu) {
        this.shunxu = shunxu;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "OrderRole{" +
        "id=" + id +
        ", name=" + name +
        ", type=" + type +
        ", isname=" + isname +
        ", shunxu=" + shunxu +
        ", parentId=" + parentId +
        ", typeName=" + typeName +
        "}";
    }
}
