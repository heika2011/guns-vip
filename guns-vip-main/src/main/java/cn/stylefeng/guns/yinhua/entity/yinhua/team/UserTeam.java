package cn.stylefeng.guns.yinhua.entity.yinhua.team;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 小组
 */
@Data
public class UserTeam implements Serializable {

    /**
     * 小组id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //小组名
    private String name;
    //小组组长信息
    @TableField(exist = false)
    private List<TeamJob> leader;
    //创建时间
    private Date createdTime;
    //生产单小组类型 1 打样组 2生产组 3激光组 4移模组
    private Integer type;

    @TableField(exist = false)
    private List<UserTeamU> users;
}
