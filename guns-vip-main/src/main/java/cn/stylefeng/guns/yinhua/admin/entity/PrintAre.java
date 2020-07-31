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
 * @since 2020-05-23
 */
@TableName("print_are")
public class PrintAre implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 打印机id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 打印机名字
     */
    @TableField("print_name")
    private String printName;

    /**
     * 打印机token
     */
    @TableField("token")
    private String token;

    /**
     * 打印机钥匙
     */
    @TableField("print_key")
    private String printKey;

    /**
     * 打印机简称
     */
    @TableField("nickname")
    private String nickname;

    /**
     * 打印机端口
     */
    @TableField("print_port")
    private Integer printPort;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrintName() {
        return printName;
    }

    public void setPrintName(String printName) {
        this.printName = printName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPrintKey() {
        return printKey;
    }

    public void setPrintKey(String printKey) {
        this.printKey = printKey;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getPrintPort() {
        return printPort;
    }

    public void setPrintPort(Integer printPort) {
        this.printPort = printPort;
    }

    @Override
    public String toString() {
        return "PrintAre{" +
        "id=" + id +
        ", printName=" + printName +
        ", token=" + token +
        ", printKey=" + printKey +
        ", nickname=" + nickname +
        ", printPort=" + printPort +
        "}";
    }
}
