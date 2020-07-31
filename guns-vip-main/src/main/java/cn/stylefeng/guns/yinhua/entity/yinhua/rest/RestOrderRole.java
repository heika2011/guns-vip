package cn.stylefeng.guns.yinhua.entity.yinhua.rest;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 返回订单类型的JSON格式
 */
@Data
@Accessors(chain = true)
public class RestOrderRole implements Serializable {

    //订单类型名字
    private String name;
    //类型Id
    private Integer id;
}
