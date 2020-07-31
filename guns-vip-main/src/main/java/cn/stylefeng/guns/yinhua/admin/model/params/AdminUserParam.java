package cn.stylefeng.guns.yinhua.admin.model.params;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class AdminUserParam implements Serializable {

    //用户Id
    private String userId;
    //用户名字
    private String name;

    private List<String> userIds;
}
