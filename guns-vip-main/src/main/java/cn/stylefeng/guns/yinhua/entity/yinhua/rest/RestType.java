package cn.stylefeng.guns.yinhua.entity.yinhua.rest;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 统计返回类型
 */
@Data
@Accessors(chain = true)
public class RestType implements Serializable {
    //类型
    private String type;
    //统计内容
    private String value;
    //数值
    private String num;
}
