package cn.stylefeng.guns.base.pojo.node;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class StatusResult implements Serializable {
    //消息
    private String msg;
    //接口码
    private String code;

    public StatusResult(String code,String msg){
        this.msg = msg;
        this.code = code;
    }
}
