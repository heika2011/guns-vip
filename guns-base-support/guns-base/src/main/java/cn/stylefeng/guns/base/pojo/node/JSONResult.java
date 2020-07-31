package cn.stylefeng.guns.base.pojo.node;

import lombok.Data;

import java.io.Serializable;

/**
 * 前端结果返回
 */
@Data
public class JSONResult implements Serializable {

    //返回数据
    private Object data=null;
    //返回接口状态
    private StatusResult state;

    //返回JSON
    public JSONResult(String code,String msg,Object data){
        state = new StatusResult(code,msg);
        this.data = data;
    }
    public JSONResult(String code,String msg){
        state = new StatusResult(code,msg);
    }
    public static JSONResult OK(Object data){
        return new JSONResult("0","ok",data);
    }
    //返回报错信息
    public JSONResult(Throwable throwable){
        state = new StatusResult("-1",throwable.getMessage());
    }

}
