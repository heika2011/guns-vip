package cn.stylefeng.guns.yinhua.admin.entity;


import lombok.Data;

import java.io.Serializable;

/**
 * 微信用户
 */
@Data
public class WeChatPeople implements Serializable {

    //用户的标识，对当前公众号唯一
    private String openId;

    //用户的昵称
    private String nickName;

    //用户头像，最后一个数值代表正方形头像大小
    private String imageUrl;

}
