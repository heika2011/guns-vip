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
@TableName("teams")
public class Teams implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 小组Id
     */
      @TableId(value = "id", type = IdType.ID_WORKER)
    private Integer id;

    /**
     * 名字
     */
    @TableField("name")
    private String name;

    /**
     * 组长id
     */
    @TableField("leader_id")
    private Integer leaderId;

    /**
     * id
     */
    @TableField("user_id")
    private Long userId;

    /**
     * 副组长id
     */
    @TableField("vice_id")
    private Integer viceId;

    /**
     * 小组名
     */
    @TableField("team_name")
    private String teamName;
    /**
     * 小组编号
     */
    @TableField("team_num")
    private String teamNum;

    public String getTeamNum() {
        return teamNum;
    }

    public void setTeamNum(String teamNum) {
        this.teamNum = teamNum;
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

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getViceId() {
        return viceId;
    }

    public void setViceId(Integer viceId) {
        this.viceId = viceId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "Teams{" +
        "id=" + id +
        ", name=" + name +
        ", leaderId=" + leaderId +
        ", userId=" + userId +
        ", viceId=" + viceId +
        ", teamName=" + teamName +
        "}";
    }
}
