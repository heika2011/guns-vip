package cn.stylefeng.guns.sys.core.exception;

/**
 * 前端异常统一处理
 */
public class OutExcetion extends RuntimeException{
    public OutExcetion(String message){
        super(message);
    }
}
