package cn.stylefeng.guns.yinhua.controller;

import cn.stylefeng.guns.sys.modular.third.service.HttpHelper;
import cn.stylefeng.guns.sys.modular.third.service.WeChatForTokenUtil;
import cn.stylefeng.guns.yinhua.util.WeChatMessageToUser;
import com.alibaba.fastjson.JSONObject;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;

@Controller
@RequestMapping("/mobile")
public class TestController {

    @RequestMapping("/getToken")
    public void getToken(String signature, String timestamp, String nonce, String echostr, HttpServletResponse response) throws Exception{
        String s = WeChatForTokenUtil.sortAndEncrypt("yinhua", timestamp, nonce);
        if(s.equals(signature)){
            BufferedOutputStream out = new BufferedOutputStream(
                    response.getOutputStream());
            out.write(echostr.getBytes());
            out.flush();
            out.close();
        }
    }
    @RequestMapping("/test")
    public void test1(){

    }

}
