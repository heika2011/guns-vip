package cn.stylefeng.guns.yinhua.service.impl;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.sys.modular.third.model.AuthSources;
import cn.stylefeng.guns.sys.modular.third.model.AuthWeChatRequest;
import cn.stylefeng.guns.sys.modular.third.service.HttpHelper;
import cn.stylefeng.guns.yinhua.service.WeXinService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 微信jsjdk的有关配置
 * @Date 2020/4/4
 * @Auth xiexin
 */
@Service
public class WeXinServiceImpl implements WeXinService {

    /**
     * 初始化JSSDK配置信息
     * @param shareUrl
     * @return
     * @throws Exception
     */
    @Override
    public Map initJSSDKConfigInfo(String shareUrl) {
        String accessToken = AuthWeChatRequest.accessTokens;
        String jsapiTicket = this.getJSSDKJsapiTicket(accessToken);
        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        String nonceStr = UUID.randomUUID().toString();
        String signature = null;
        try {
            signature = this.buildJSSDKSignature(jsapiTicket,timestamp,nonceStr,shareUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Map<String,String> map = new HashMap<String,String>();
        map.put("shareUrl", shareUrl);
        map.put("jsapi_ticket", jsapiTicket);
        map.put("nonceStr", nonceStr);
        map.put("timestamp", timestamp);
        map.put("signature", signature);
        map.put("appid", ConstantsContext.getWeChatClientId());
        return map;
    }


    /**
     * JSSDK jsapi_ticket
     * @param token
     * @return
     */
    public String getJSSDKJsapiTicket(String token) {
        String url = AuthSources.WECHAT.ticket().replace("ACCESS_TOKEN", token);
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpHelper.doGet(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String jsapi_ticket = jsonObject.getString("ticket");
        return jsapi_ticket;
    }
    /**
     * 构建分享链接的签名。
     * @param ticket
     * @param nonceStr
     * @param url
     * @return
     * @throws Exception
     */
    public static String buildJSSDKSignature(String ticket,String timestamp,String nonceStr ,String url) throws Exception {

        String orderedString = "jsapi_ticket=" + ticket
                + "&noncestr=" + nonceStr + "&timestamp=" + timestamp
                + "&url=" + url;
        return sha1(orderedString);
    }

    /**
     * sha1 加密JSSDK微信配置参数获取签名。
     *
     * @return
     */
    public static String sha1(String orderedString) throws Exception {
        String ciphertext = null;
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] digest = md.digest(orderedString.getBytes());
        ciphertext = byteToStr(digest);
        return ciphertext.toLowerCase();
    }
    /**
     * 将字节数组转换为十六进制字符串
     *
     * @param byteArray
     * @return
     */
    private static String byteToStr(byte[] byteArray) {
        String strDigest = "";
        for (int i = 0; i < byteArray.length; i++) {
            strDigest += byteToHexStr(byteArray[i]);
        }
        return strDigest;
    }
    /**
     * 将字节转换为十六进制字符串
     *
     * @param mByte
     * @return
     */
    private static String byteToHexStr(byte mByte) {
        char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
        tempArr[1] = Digit[mByte & 0X0F];

        String s = new String(tempArr);
        return s;
    }
}
