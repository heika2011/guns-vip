package cn.stylefeng.guns.yinhua.service;

import java.util.Map;

/**
 * jsjdk有关配置
 * @Date 2020/4/4
 * @Auth xiexin
 */
public interface WeXinService {
    /**
     * 初始化JSSDK配置信息
     * @param shareUrl
     * @return
     * @throws Exception
     */
    Map initJSSDKConfigInfo(String shareUrl);
}
