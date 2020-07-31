package cn.stylefeng.guns.sys.modular.third.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 授权所需的token
 *
 * @author yadong.zhang (yadong.zhang0415(a)gmail.com)
 * @since 1.8
 */
@Getter
@Setter
@Builder
public class AuthToken {
    private String accessToken;
    private String code;
    private int expireIn;
    private String refreshToken;
    private String uid;

}

