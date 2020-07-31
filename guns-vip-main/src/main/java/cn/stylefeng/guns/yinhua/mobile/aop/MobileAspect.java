package cn.stylefeng.guns.yinhua.mobile.aop;

import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import cn.stylefeng.guns.sys.modular.third.service.WeChatForTokenUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * token判断切面
 */
@Component      //声明组件
@Aspect         //声明切面
public class MobileAspect {

    @Autowired
    private SessionManager sessionManager;

    @Around("execution(* cn.stylefeng.guns.yinhua.mobile.controller..*(..))")
    public Object around(ProceedingJoinPoint pj) throws Throwable {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        WeChatForTokenUtil.checkToken(request.getParameter("token"),sessionManager);
        Object obj = pj.proceed();
        return obj;
    }

}
