package cn.stylefeng.guns.base.consts;

import cn.stylefeng.guns.base.enums.CommonStatus;
import cn.stylefeng.guns.base.sms.AliyunSmsProperties;
import cn.stylefeng.roses.core.util.ToolUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static cn.stylefeng.guns.base.consts.ConfigConstant.SYSTEM_CONSTANT_PREFIX;
import static cn.stylefeng.roses.core.util.ToolUtil.getTempPath;

/**
 * 系统常量的容器
 *
 * @author fengshuonan
 * @Date 2019-06-20 13:37
 */
@Slf4j
public class ConstantsContext {

    private static final String TIPS_END = "，若想忽略此提示，请在开发管理->系统配置->参数配置，设置相关参数！";

    /**
     * 所有的常量，可以增删改查
     */
    private static Map<String, Object> CONSTNTS_HOLDER = new ConcurrentHashMap<>();

    /**
     * 添加系统常量
     */
    public static void putConstant(String key, Object value) {
        if (ToolUtil.isOneEmpty(key, value)) {
            return;
        }
        CONSTNTS_HOLDER.put(key, value);
    }

    /**
     * 删除常量
     */
    public static void deleteConstant(String key) {
        if (ToolUtil.isOneEmpty(key)) {
            return;
        }

        //如果是系统常量
        if (!key.startsWith(SYSTEM_CONSTANT_PREFIX)) {
            CONSTNTS_HOLDER.remove(key);
        }
    }

    /**
     * 获取系统常量
     */
    public static Map<String, Object> getConstntsMap() {
        return CONSTNTS_HOLDER;
    }

    /**
     * 获取验证码开关
     */
    public static Boolean getKaptchaOpen() {
        String gunsKaptchaOpen = (String) CONSTNTS_HOLDER.get("GUNS_KAPTCHA_OPEN");
        if (CommonStatus.ENABLE.getCode().equalsIgnoreCase(gunsKaptchaOpen)) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 获取自动打印状态
     */
    public static Boolean getAutoPrintFlag() {
        String gunsKaptchaOpen = (String) CONSTNTS_HOLDER.get("AUTO_PRINT_FLAG");
        if(gunsKaptchaOpen.equals("0")){
            return false;
        }else if(gunsKaptchaOpen.equals("1")){
            return true;
        }
        return null;
    }

    /**
     * 获取短信的配置
     */
    public static AliyunSmsProperties getAliyunSmsProperties() {
        String gunsSmsAccesskeyId = (String) CONSTNTS_HOLDER.get("GUNS_SMS_ACCESSKEY_ID");
        String gunsSmsAccesskeySecret = (String) CONSTNTS_HOLDER.get("GUNS_SMS_ACCESSKEY_SECRET");
        String gunsSmsSignName = (String) CONSTNTS_HOLDER.get("GUNS_SMS_SIGN_NAME");
        String gunsSmsLoginTemplateCode = (String) CONSTNTS_HOLDER.get("GUNS_SMS_LOGIN_TEMPLATE_CODE");
        String gunsSmsInvalidateMinutes = (String) CONSTNTS_HOLDER.get("GUNS_SMS_INVALIDATE_MINUTES");

        AliyunSmsProperties aliyunSmsProperties = new AliyunSmsProperties();
        aliyunSmsProperties.setAccessKeyId(gunsSmsAccesskeyId);
        aliyunSmsProperties.setAccessKeySecret(gunsSmsAccesskeySecret);
        aliyunSmsProperties.setSignName(gunsSmsSignName);
        aliyunSmsProperties.setLoginTemplateCode(gunsSmsLoginTemplateCode);
        aliyunSmsProperties.setInvalidateMinutes(Integer.valueOf(gunsSmsInvalidateMinutes));
        return aliyunSmsProperties;
    }

    /**
     * 获取管理系统名称
     */
    public static String getSystemName() {
        String systemName = (String) CONSTNTS_HOLDER.get("GUNS_SYSTEM_NAME");
        if (ToolUtil.isEmpty(systemName)) {
            log.error("系统常量存在空值！常量名称：GUNS_SYSTEM_NAME，采用默认名称：Guns快速开发平台" + TIPS_END);
            return "Guns快速开发平台";
        } else {
            return systemName;
        }
    }

    /**
     * 获取管理系统名称
     */
    public static String getDefaultPassword() {
        String defaultPassword = (String) CONSTNTS_HOLDER.get("GUNS_DEFAULT_PASSWORD");
        if (ToolUtil.isEmpty(defaultPassword)) {
            log.error("系统常量存在空值！常量名称：GUNS_DEFAULT_PASSWORD，采用默认密码：111111" + TIPS_END);
            return "111111";
        } else {
            return defaultPassword;
        }
    }

    /**
     * 获取管理系统名称
     */
    public static String getOAuth2UserPrefix() {
        String oauth2Prefix = (String) CONSTNTS_HOLDER.get("GUNS_OAUTH2_PREFIX");
        if (ToolUtil.isEmpty(oauth2Prefix)) {
            log.error("系统常量存在空值！常量名称：GUNS_OAUTH2_PREFIX，采用默认值：oauth2" + TIPS_END);
            return "oauth2";
        } else {
            return oauth2Prefix;
        }
    }

    /**
     * 获取顶部导航条是否开启
     */
    public static Boolean getDefaultAdvert() {
        String gunsDefaultAdvert = (String) CONSTNTS_HOLDER.get("GUNS_DEFAULT_ADVERT");
        if (ToolUtil.isEmpty(gunsDefaultAdvert)) {
            log.error("系统常量存在空值！常量名称：GUNS_DEFAULT_ADVERT，采用默认值：true" + TIPS_END);
            return true;
        } else {
            if (CommonStatus.ENABLE.getCode().equalsIgnoreCase(gunsDefaultAdvert)) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * 获取系统发布的版本号（防止css和js的缓存）
     */
    public static String getReleaseVersion() {
        String systemReleaseVersion = (String) CONSTNTS_HOLDER.get("GUNS_SYSTEM_RELEASE_VERSION");
        if (ToolUtil.isEmpty(systemReleaseVersion)) {
            log.error("系统常量存在空值！常量名称：GUNS_SYSTEM_RELEASE_VERSION，采用默认值：guns" + TIPS_END);
            return ToolUtil.getRandomString(8);
        } else {
            return systemReleaseVersion;
        }
    }

    /**
     * 获取文件上传路径(用于头像和富文本编辑器)
     */
    public static String getFileUploadPath() {
        String gunsFileUploadPath = (String) CONSTNTS_HOLDER.get("GUNS_FILE_UPLOAD_PATH");
        if (ToolUtil.isEmpty(gunsFileUploadPath)) {
            log.error("系统常量存在空值！常量名称：GUNS_FILE_UPLOAD_PATH，采用默认值：系统tmp目录" + TIPS_END);
            return getTempPath();
        } else {
            //判断有没有结尾符
            if (!gunsFileUploadPath.endsWith(File.separator)) {
                gunsFileUploadPath = gunsFileUploadPath + File.separator;
            }

            //判断目录存不存在
            File file = new File(gunsFileUploadPath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (mkdirs) {
                    return gunsFileUploadPath;
                } else {
                    log.error("系统常量存在空值！常量名称：GUNS_FILE_UPLOAD_PATH，采用默认值：系统tmp目录" + TIPS_END);
                    return getTempPath();
                }
            } else {
                return gunsFileUploadPath;
            }
        }
    }

    /**
     * 用于存放bpmn文件
     */
    public static String getBpmnFileUploadPath() {
        String bpmnFileUploadPath = (String) CONSTNTS_HOLDER.get("GUNS_BPMN_FILE_UPLOAD_PATH");
        if (ToolUtil.isEmpty(bpmnFileUploadPath)) {
            log.error("系统常量存在空值！常量名称：GUNS_BPMN_FILE_UPLOAD_PATH，采用默认值：系统tmp目录" + TIPS_END);
            return getTempPath();
        } else {
            //判断有没有结尾符
            if (!bpmnFileUploadPath.endsWith(File.separator)) {
                bpmnFileUploadPath = bpmnFileUploadPath + File.separator;
            }

            //判断目录存不存在
            File file = new File(bpmnFileUploadPath);
            if (!file.exists()) {
                boolean mkdirs = file.mkdirs();
                if (mkdirs) {
                    return bpmnFileUploadPath;
                } else {
                    log.error("系统常量存在空值！常量名称：GUNS_BPMN_FILE_UPLOAD_PATH，采用默认值：系统tmp目录" + TIPS_END);
                    return getTempPath();
                }
            } else {
                return bpmnFileUploadPath;
            }
        }
    }

    /**
     * 获取系统地密钥
     */
    public static String getJwtSecret() {
        String systemReleaseVersion = (String) CONSTNTS_HOLDER.get("GUNS_JWT_SECRET");
        if (ToolUtil.isEmpty(systemReleaseVersion)) {
            String randomSecret = ToolUtil.getRandomString(32);
            CONSTNTS_HOLDER.put("GUNS_JWT_SECRET", randomSecret);
            log.error("jwt密钥存在空值！常量名称：GUNS_JWT_SECRET，采用默认值：随机字符串->" + randomSecret + TIPS_END);
            return randomSecret;
        } else {
            return systemReleaseVersion;
        }
    }

    /**
     * 获取系统地密钥过期时间（单位：秒）
     */
    public static Long getJwtSecretExpireSec() {
        Long defaultSecs = 86400L;
        String systemReleaseVersion = (String) CONSTNTS_HOLDER.get("GUNS_JWT_SECRET_EXPIRE");
        if (ToolUtil.isEmpty(systemReleaseVersion)) {
            log.error("jwt密钥存在空值！常量名称：GUNS_JWT_SECRET_EXPIRE，采用默认值：1天" + TIPS_END);
            CONSTNTS_HOLDER.put("GUNS_JWT_SECRET_EXPIRE", String.valueOf(defaultSecs));
            return defaultSecs;
        } else {
            try {
                return Long.valueOf(systemReleaseVersion);
            } catch (NumberFormatException e) {
                log.error("jwt密钥过期时间不是数字！常量名称：GUNS_JWT_SECRET_EXPIRE，采用默认值：1天" + TIPS_END);
                CONSTNTS_HOLDER.put("GUNS_JWT_SECRET_EXPIRE", String.valueOf(defaultSecs));
                return defaultSecs;
            }
        }
    }

    /**
     * 获取token的header标识
     */
    public static String getTokenHeaderName() {
        String tokenHeaderName = (String) CONSTNTS_HOLDER.get("GUNS_TOKEN_HEADER_NAME");
        if (ToolUtil.isEmpty(tokenHeaderName)) {
            String defaultName = "Authorization";
            CONSTNTS_HOLDER.put("GUNS_TOKEN_HEADER_NAME", defaultName);
            log.error("获取token的header标识为空！常量名称：GUNS_TOKEN_HEADER_NAME，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return tokenHeaderName;
        }
    }
    /**
     *获取微信的可信回调地址
     */
    public static String getWeChatKxUrl() {
        String weChatKxUrl = (String) CONSTNTS_HOLDER.get("WECHAT_KX_BACK");
        if (ToolUtil.isEmpty(weChatKxUrl)) {
            String defaultName = "http://xiexinxin.ngrok2.xiaomiqiu.cn";
            CONSTNTS_HOLDER.put("WECHAT_KX_BACK", defaultName);
            log.error("获取微信的回调地址标识为空！常量名称：WECHAT_KX_BACK，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return weChatKxUrl;
        }
    }
    /**
     *获取微信的id
     */
    public static String getWeChatClientId() {
        String weChatKxUrl = (String) CONSTNTS_HOLDER.get("WECHAT_CLIENTID");
        if (ToolUtil.isEmpty(weChatKxUrl)) {
            String defaultName = "ww58c1a241a9299cff";
            CONSTNTS_HOLDER.put("WECHAT_CLIENTID", defaultName);
            log.error("获取client为空！常量名称：WECHAT_CLIENTID，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return weChatKxUrl;
        }
    }
    /**
     *获取微信应用的id
     */
    public static Integer getWeChatAgentId() {
        String WeChatAgentId =  (String)CONSTNTS_HOLDER.get("WECHAT_AGENTID");
        if (ToolUtil.isEmpty(WeChatAgentId)) {
            Integer defaultName = 1000032;
            CONSTNTS_HOLDER.put("WECHAT_AGENTID", defaultName);
            log.error("获取agentId为空！常量名称：WECHAT_AGENTID，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return Integer.valueOf(WeChatAgentId);
        }
    }
    /**
     *获取微信的serect
     */
    public static String getWeChatSerect() {
        String weChatKxUrl = (String) CONSTNTS_HOLDER.get("WECHAT_SERECT");
        if (ToolUtil.isEmpty(weChatKxUrl)) {
            String defaultName = "15VkZLVz3rRLT50ykKaShGa66ULNCH05pEpy5yULF8o";
            CONSTNTS_HOLDER.put("WECHAT_SERECT", defaultName);
            log.error("获取serect为空！常量名称：WECHAT_SERECT，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return weChatKxUrl;
        }
    }
    /**
     *获取公众号ID
     */
    public static String getGZHID() {
        String weChatKxUrl = (String) CONSTNTS_HOLDER.get("WECHAT_GZH_ID");
        if (ToolUtil.isEmpty(weChatKxUrl)) {
            String defaultName = "wxd94fe6e875d91499";
            CONSTNTS_HOLDER.put("WECHAT_GZH_ID", defaultName);
            log.error("获取serect为空！常量名称：WECHAT_GZH_ID，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return weChatKxUrl;
        }
    }
    /**
     *获取公众号Secret
     */
    public static String getGZHSecret() {
        String weChatKxUrl = (String) CONSTNTS_HOLDER.get("WECHAT_GZH_SECRET");
        if (ToolUtil.isEmpty(weChatKxUrl)) {
            String defaultName = "b01ce2810c4cc67900cda6604d1d6c26";
            CONSTNTS_HOLDER.put("WECHAT_GZH_SECRET", defaultName);
            log.error("getGZHSecret！常量名称：WECHAT_GZH_SECRET，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return weChatKxUrl;
        }
    }
    /**
     *获取前端详情跳转地址
     */
    public static String getJumpUrl() {
        String weChatKxUrl = (String) CONSTNTS_HOLDER.get("MOBILE_JUMPURL");
        if (ToolUtil.isEmpty(weChatKxUrl)) {
            String defaultName = "http://localhost:3000/#/?jumpOrderNum=%22{orderNum}%22&type={type}";
            CONSTNTS_HOLDER.put("MOBILE_JUMPURL", defaultName);
            log.error("获取前端详情跳转地址失败！常量名称：MOBILE_JUMPURL，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return weChatKxUrl;
        }
    }
    /**
     *获取前端地址
     */
    public static String getWebUrl() {
        String weChatKxUrl = (String) CONSTNTS_HOLDER.get("WEB_OUT_URL");
        if (ToolUtil.isEmpty(weChatKxUrl)) {
            String defaultName = "http://localhost";
            CONSTNTS_HOLDER.put("WEB_OUT_URL", defaultName);
            log.error("获取前端地址失败！常量名称：WEB_OUT_URL，采用默认值：" + defaultName + TIPS_END);
            return defaultName;
        } else {
            return weChatKxUrl;
        }
    }
    //获取double
    public static Double getDouble(String type) {
        String weChatKxUrl = (String) CONSTNTS_HOLDER.get(type);
            return Double.parseDouble(weChatKxUrl);
    }

    /**
     * 获取租户是否开启的标识，默认是关的
     */
    public static Boolean getTenantOpen() {
        String tenantOpen = (String) CONSTNTS_HOLDER.get("GUNS_TENANT_OPEN");
        if (ToolUtil.isEmpty(tenantOpen)) {
            log.error("系统常量存在空值！常量名称：GUNS_TENANT_OPEN，采用默认值：DISABLE" + TIPS_END);
            return false;
        } else {
            return CommonStatus.ENABLE.getCode().equalsIgnoreCase(tenantOpen);
        }
    }

}
