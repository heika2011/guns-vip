package cn.stylefeng.guns.yinhua.excetion;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.sys.core.log.LogManager;
import cn.stylefeng.guns.sys.core.log.factory.LogTaskFactory;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 前端异常处理
 */
@ControllerAdvice
public class DoOutExcetion {

    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public JSONResult notFount(RuntimeException e) {
        if (LoginContextHolder.getContext().hasLogin()) {
            LogManager.me().executeLog(LogTaskFactory.exceptionLog(LoginContextHolder.getContext().getUserId(), e));
        }
        return new JSONResult(e);
    }

}
