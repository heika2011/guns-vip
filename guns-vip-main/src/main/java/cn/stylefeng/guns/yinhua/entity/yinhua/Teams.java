package cn.stylefeng.guns.yinhua.entity.yinhua;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 小组
 */
@Data
@Accessors(chain = true)
public class Teams implements Serializable {

    //id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //名字
    private String name;
    //组长id
    private Integer leaderId;
    //userid
    private Long userId;
    //副组长id
    private Integer viceId;
    //组名
    private String teamName;

}
