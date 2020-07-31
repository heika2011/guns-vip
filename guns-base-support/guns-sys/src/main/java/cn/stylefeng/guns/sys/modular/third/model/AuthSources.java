package cn.stylefeng.guns.sys.modular.third.model;


import me.zhyd.oauth.exception.AuthException;
import me.zhyd.oauth.enums.AuthResponseStatus;

/**
 * 各api需要的url， 用枚举类分平台类型管理
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.0
 */
public enum AuthSources {

    /**
     * 微信
     */
    WECHAT {
        @Override
        public String authorize() {
            return "https://open.weixin.qq.com/connect/oauth2/authorize";
        }

        @Override
        public String accessToken() {
            return "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
        }

        @Override
        public String userInfo() {
            return "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
        }

        @Override
        public String refresh() {
            return "https://api.weixin.qq.com/sns/oauth2/refresh_token";
        }
        @Override
        public String ticket(){
            return "https://qyapi.weixin.qq.com/cgi-bin/get_jsapi_ticket?access_token=ACCESS_TOKEN";
        }
    };

    /**
     * 授权的api
     *
     * @return url
     */
    public abstract String authorize();

    /**
     * 获取accessToken的api
     *
     * @return url
     */
    public abstract String accessToken();

    /**
     * 获取用户信息的api
     *
     * @return url
     */
    public abstract String userInfo();

    /**
     * 获取jsjdkticket
     * @return
     */
    public abstract String ticket();
    /**
     * 取消授权的api
     *
     * @return url
     */
    public String revoke() {
        throw new AuthException(AuthResponseStatus.UNSUPPORTED);
    }

    /**
     * 刷新授权的api
     *
     * @return url
     */
    public String refresh() {
        throw new AuthException(AuthResponseStatus.UNSUPPORTED);
    }

}
