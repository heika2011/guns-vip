package cn.stylefeng.guns.yinhua.util;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * 微信公众号消息推送
 */
@Configuration
public class WeChatMessageToUser {

    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpInMemoryConfigStorage().setAppId(ConstantsContext.getGZHID());
        wxMpInMemoryConfigStorage().setSecret(ConstantsContext.getGZHSecret());
        wxMpService.setWxMpConfigStorage(wxMpInMemoryConfigStorage());
        return wxMpService;
    }
    @Bean
    public WxMpInMemoryConfigStorage wxMpInMemoryConfigStorage(){
        WxMpInMemoryConfigStorage wxStorage = new WxMpInMemoryConfigStorage();
        return wxStorage;
    }

}
