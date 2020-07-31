package cn.stylefeng.guns.sys.modular.system.model.entity;

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
 * @since 2020-03-24
 */
@TableName("sys_count")
public class SysCount implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 价格计算id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 类型
     */
    @TableField("type")
    private Integer type;

    /**
     * 值
     */
    @TableField("value")
    private String value;


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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "SysCount{" +
        "id=" + id +
        ", type=" + type +
        ", value=" + value +
        "}";
    }
}
