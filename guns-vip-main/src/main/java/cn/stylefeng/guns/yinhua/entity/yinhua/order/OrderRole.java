package cn.stylefeng.guns.yinhua.entity.yinhua.order;


import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderRole;
import lombok.Data;

/**
 * 订单类型
 */
@Data
public class OrderRole extends RestOrderRole {
    //类型对应角色id
    private String type;
    //角色名
    private String typeName;
    //是否是名字
    private Integer isname;
    //生产顺序
    private Integer shunxu;
    //名字id
    private Integer parentId;
}
