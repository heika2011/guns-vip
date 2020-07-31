package cn.stylefeng.guns.yinhua.entity.yinhua;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 小组数据统计
 */
@Data
@Accessors(chain = true)
public class TeamsData implements Serializable {
    //小组名字
    private String teamName;
    //小组产值
    private Double teamCZ;
    //小组生产单量
    private String teamCL;
}
