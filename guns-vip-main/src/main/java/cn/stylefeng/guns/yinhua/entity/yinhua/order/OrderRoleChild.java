package cn.stylefeng.guns.yinhua.entity.yinhua.order;


import lombok.Data;

@Data
public class OrderRoleChild {

    private Integer id;
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
    //名字
    private String name;

    private String num;
}
