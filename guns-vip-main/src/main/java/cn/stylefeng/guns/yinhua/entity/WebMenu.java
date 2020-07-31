package cn.stylefeng.guns.yinhua.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * <p>
 * 动态菜单表
 * </p>
 *
 * @author stylefeng
 * @since 2019-04-01
 */
@TableName("web_menu")
public class WebMenu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 菜单id
     */
    @TableField("id")
    private Integer id;

    /**
     * 菜单名字
     */
    @TableField("name")
    private String name;

    /**
     * 对应的vue的地址
     */
    @TableField("code")
    private String code;

    /**
     *
     */
    @TableField("see")
    private Integer see;

    public WebMenu(Integer id, String name, String code, Integer see) {
        this.id = id;
        this.name = name;
        this.code = code;
        this.see = see;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getSee() {
        return see;
    }

    public void setSee(Integer see) {
        this.see = see;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WebMenu webMenu = (WebMenu) o;
        return Objects.equals(id, webMenu.id) &&
                Objects.equals(name, webMenu.name) &&
                Objects.equals(code, webMenu.code) &&
                Objects.equals(see, webMenu.see);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, code, see);
    }

    @Override
    public String toString() {
        return "WebMenu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                ", see=" + see +
                '}';
    }
}
