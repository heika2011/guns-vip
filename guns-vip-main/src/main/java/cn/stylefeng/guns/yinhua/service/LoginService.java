package cn.stylefeng.guns.yinhua.service;

import me.chanjar.weixin.mp.bean.result.WxMpUser;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录业务接口
 */
public interface LoginService {
    void doLoginUser(String code);
    void doWeChatGHZLogin(WxMpUser wxMpUser, HttpServletResponse response,String url) throws IOException;
}
