package cn.stylefeng.guns.yinhua.entity.yinhua.team;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 小组成员
 */
@Data
public class UserTeamU implements Serializable {

    /**
     * 成员id
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;

    //小组id
    private Integer teamId;
    //职务
    private Integer job;
    //名字
    private String name;
    //用户id
    private Long userId;
}
