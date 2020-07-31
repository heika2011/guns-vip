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
@TableName("order_team")
public class OrderTeam implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 订单编号
     */
    @TableField("order_num")
    private String orderNum;

    /**
     * 小组id
     */
    @TableField("team_id")
    private Integer teamId;

    /**
     * 1 定位 2点数 3刮板 4总检台上 5 总检后道 6总检后道
     */
    @TableField("type")
    private Integer type;

    /**
     * 员工名字
     */
    @TableField("user_name")
    private String userName;

    /**
     * 员工Id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 是否同意
     */
    @TableField("sure_flag")
    private Integer sureFlag;


    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getSureFlag() {
        return sureFlag;
    }

    public void setSureFlag(Integer sureFlag) {
        this.sureFlag = sureFlag;
    }

    @Override
    public String toString() {
        return "OrderTeam{" +
        "orderNum=" + orderNum +
        ", teamId=" + teamId +
        ", type=" + type +
        ", userName=" + userName +
        ", userId=" + userId +
        ", sureFlag=" + sureFlag +
        "}";
    }
}
