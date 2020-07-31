package cn.stylefeng.guns.yinhua.entity.yinhua.team;

import lombok.Data;

import java.io.Serializable;

@Data
public class TeamJob implements Serializable {

    //组长名字
    private String name;
    private Integer type;
}
