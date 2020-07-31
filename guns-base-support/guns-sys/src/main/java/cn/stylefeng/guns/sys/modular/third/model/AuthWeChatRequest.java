package cn.stylefeng.guns.sys.modular.third.model;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.stylefeng.guns.sys.modular.third.entity.AuthToken;
import com.alibaba.fastjson.JSONObject;
import me.zhyd.oauth.config.AuthConfig;
import me.zhyd.oauth.enums.AuthResponseStatus;
import me.zhyd.oauth.enums.AuthUserGender;
import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.model.AuthCallback;
import me.zhyd.oauth.model.AuthResponse;
import me.zhyd.oauth.model.AuthUser;
import me.zhyd.oauth.utils.StringUtils;
import me.zhyd.oauth.utils.UrlBuilder;

public class AuthWeChatRequest extends AuthDefaultRequest {

    public static String accessTokens = "";

    public AuthWeChatRequest(AuthConfig config) {
        super(config, AuthSources.WECHAT);
    }

    /**
     * 微信的特殊性，此时返回的信息同时包含 openid 和 access_token
     *
     * @param authCallback 回调返回的参数
     * @return 所有信息
     */
    @Override
    protected AuthToken getAccessToken(AuthCallback authCallback) {
        AuthToken token = this.getToken(accessTokenUrls(authCallback.getCode()));
        accessTokens = token.getAccessToken();
        return token;
    }

    @Override
    protected AuthUser getUserInfo(AuthToken authToken) {
        HttpResponse response = doGetUserInfo(authToken);
        JSONObject object1 = JSONObject.parseObject(response.body());
        String userid = object1.getString("UserId");
        HttpResponse response1 = HttpRequest.get(
                UrlBuilder.fromBaseUrl("https://qyapi.weixin.qq.com/cgi-bin/user/get")
                        .queryParam("access_token",authToken.getAccessToken())
                        .queryParam("userid",userid)
                        .build()).execute();
        JSONObject object = JSONObject.parseObject(response1.body());


        return AuthUser.builder()
                .username(object.getString("userid"))
                .nickname(object.getString("name"))
                .avatar(object.getString("headimgurl"))
                .uuid(object.getString("userid"))
                .avatar(object.getString("avatar"))
                .email(object.getString("email"))
                .gender(AuthUserGender.getRealGender(object.getString("gender")))
                .build();
    }
    public AuthResponse refresh(AuthToken oldToken) {
        return AuthResponse.builder()
                .code(AuthResponseStatus.SUCCESS.getCode())
                .data(this.getToken(refreshTokenUrl(oldToken.getRefreshToken())))
                .build();
    }

    /**
     * 检查响应内容是否正确
     *
     * @param object 请求响应内容
     */
    private void checkResponse(JSONObject object) {
        if (object.containsKey("errcode")) {
            throw new AuthException(object.getIntValue("errcode"), object.getString("errmsg"));
        }
    }

    /**
     * 获取token，适用于获取access_token和刷新token
     *
     * @param accessTokenUrl 实际请求token的地址
     * @return token对象
     */
    private AuthToken getToken(String accessTokenUrl) {
        HttpResponse response = HttpRequest.get(accessTokenUrl).execute();
        JSONObject accessTokenObject = JSONObject.parseObject(response.body());


        return AuthToken.builder()
                .accessToken(accessTokenObject.getString("access_token"))
                .expireIn(accessTokenObject.getIntValue("expires_in"))
                .build();
    }

    /**
     * 返回带{@code state}参数的授权url，授权回调时会带上这个{@code state}
     *
     * @param state state 验证授权流程的参数，可以防止csrf
     * @return 返回授权地址
     * @since 1.9.3
     */
    @Override
    public String authorize(String state) {
        return UrlBuilder.fromBaseUrl(source.authorize())
                .queryParam("response_type", "code")
                .queryParam("appid", config.getClientId())
                .queryParam("redirect_uri", config.getRedirectUri())
                .queryParam("scope", "snsapi_base")
                .queryParam("state", getRealState(state)+"#wechat_redirect")
                .build();
    }

    /**
     * 返回获取accessToken的url
     *
     * @param code 授权码
     * @return 返回获取accessToken的url
     */
    protected String accessTokenUrls(String code) {
        return UrlBuilder.fromBaseUrl(source.accessToken())
                .queryParam("corpid", config.getClientId())
                .queryParam("corpsecret", config.getClientSecret())
                .build();
    }

    /**
     * 返回获取userInfo的url
     *
     * @param authToken 用户授权后的token
     * @return 返回获取userInfo的url
     */
    @Override
    protected String userInfoUrl(AuthToken authToken) {
        return UrlBuilder.fromBaseUrl(source.userInfo())
                .queryParam("access_token", authToken.getAccessToken())
                .queryParam("code", authToken.getCode())
                .build();
    }

    /**
     * 返回获取userInfo的url
     *
     * @param refreshToken getAccessToken方法返回的refreshToken
     * @return 返回获取userInfo的url
     */
    @Override
    protected String refreshTokenUrl(String refreshToken) {
        return UrlBuilder.fromBaseUrl(source.refresh())
                .queryParam("appid", config.getClientId())
                .queryParam("refresh_token", refreshToken)
                .queryParam("grant_type", "refresh_token")
                .build();
    }
}
