package cn.stylefeng.guns.sys.modular.third.controller;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.service.AuthService;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import cn.stylefeng.guns.sys.modular.third.factory.OAuthRequestFactory;
import cn.stylefeng.guns.sys.modular.third.service.*;
import cn.stylefeng.roses.core.base.controller.BaseController;
import lombok.extern.slf4j.Slf4j;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.request.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * OAuth统一回调地址
 *
 * @author fengshuonan
 * @Date 2019/6/9 16:38
 */
@Controller
@RequestMapping("/oauth")
@Slf4j
public class OAuthController extends BaseController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private OauthUserInfoService oauthUserInfoService;
    /**
     * 第三方登录跳转
     *
     * @author fengshuonan
     * @Date 2019/6/9 16:44
     */
    @RequestMapping("/doLogin")
    public String renderAuth(){
        return "/common/test.html";
    }

    /**
     * 第三方登录跳转
     */
    @RequestMapping("/render/{source}")
    public void renderAuth(@PathVariable("source") String source, String type,String url,HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthRequest authRequest = OAuthRequestFactory.getAuthRequest(source);
        String temp = authRequest.authorize();
        if(url == null || "".equals(url)){
            temp = temp.replace("{URL}","").replace("{TYPE}","");
        }else {
            temp = temp.replace("{URL}",url).replace("{TYPE}",type);
        }
        response.sendRedirect(temp);
    }

    /**
     * 第三方登录成功后的回调地址
     */
    @RequestMapping("/callback/{source}")
    public void callback(@PathVariable("source") String source, AuthCallback callback,String render
                         ,String type,HttpServletResponse response) throws IOException {

        //通过回调的code，请求对应的oauth server获取用户基本信息和token
        AuthRequest authRequest = OAuthRequestFactory.getAuthRequest(source);
        AuthResponse authResponse = authRequest.login(callback);

        AuthUser oauthUser = (AuthUser) authResponse.getData();
        log.info("第三方登录回调成功：" + oauthUser);

        //进行第三方用户登录过程
        String token = loginService.oauthLogin(oauthUser);

        if(render == null || render .equals("") ){
            response.sendRedirect(ConstantsContext.getWebUrl()+"/#/?token="+token);
        }else {
            response.sendRedirect("/WeChatMange/jump?orderNum="+render);
        }
    }

    /**
     * 第三方登录的头像
     *
     * @author fengshuonan
     * @Date 2019/6/9 16:44
     */
    @RequestMapping("/avatar")
    public String avatar() {

        Long userId = LoginContextHolder.getContext().getUser().getId();

        //获取第三方登录用户的头像
        String avatarUrl = oauthUserInfoService.getAvatarUrl(userId);

        return REDIRECT + avatarUrl;
    }

}
