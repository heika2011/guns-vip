package cn.stylefeng.guns.sys.modular.third.service;


import cn.stylefeng.guns.base.auth.exception.AuthException;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.auth.service.AuthService;
import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;

public class WeChatForTokenUtil {


    /**
     * 检查是否登录
     * @param token
     */
    public static void checkToken(String token,SessionManager sessionManager){
        LoginUser session = sessionManager.getSession(token);
        if(session == null){
            throw new AuthException(-2,"用户尚未登录！");
        }
    }
    /**
     * 存储登录用户信息
     * @param token
     */
    public static void saveUser(String token, String username, AuthService authService,SessionManager sessionManager){
        sessionManager.createSession(token,authService.user(username,token));
    }
    public static String sortAndEncrypt(String appSecret, String timestamp, String nonce) {
        ArrayList<String> list = new ArrayList<String>();
        list.add(appSecret);
        list.add(timestamp);
        list.add(nonce);
        Collections.sort(list);
        return DigestUtils.shaHex(list.get(0) + list.get(1) + list.get(2));
    }
}
