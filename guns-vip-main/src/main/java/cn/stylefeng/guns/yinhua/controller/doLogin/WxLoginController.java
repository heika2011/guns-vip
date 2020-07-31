package cn.stylefeng.guns.yinhua.controller.doLogin;

import cn.stylefeng.guns.base.auth.jwt.JwtTokenUtil;
import cn.stylefeng.guns.base.auth.jwt.payload.JwtPayLoad;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import cn.stylefeng.guns.yinhua.entity.yinhua.Customer;
import cn.stylefeng.guns.yinhua.mobile.mapper.CustomerMapper;
import cn.stylefeng.guns.yinhua.service.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/wxAuth")
public class WxLoginController {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private LoginService loginService;

    /**
     * 微信公众号跳转生产单详情
     * @param url
     * @param response
     * @throws IOException
     */
    @RequestMapping("/login")
    public void wxLogin(String url,HttpServletResponse response) throws IOException {
        //请求获取code的回调地址
        String callBack = ConstantsContext.getWeChatKxUrl() + "/wxAuth/callBack?url="+url;
        String urls = wxMpService.oauth2buildAuthorizationUrl(callBack, WxConsts.OAuth2Scope.SNSAPI_USERINFO, null);

        response.sendRedirect(urls);
    }

    //	回调方法
    @RequestMapping("/callBack")
    public void wxCallBack(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String code = request.getParameter("code");
        String url = request.getParameter("url");
        if(url == null){
            PrintWriter writer = response.getWriter();
            writer.write("非法操作");
            return;
        }
        WxMpOAuth2AccessToken wxMpOAuth2AccessToken = wxMpService.oauth2getAccessToken(code);

        WxMpUser wxMpUser = wxMpService.oauth2getUserInfo(wxMpOAuth2AccessToken, null);

        loginService.doWeChatGHZLogin(wxMpUser,response,url);
    }
}
