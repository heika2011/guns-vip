package cn.stylefeng.guns.yinhua.entity.yinhua.rest;


import lombok.Data;

import java.io.Serializable;

/**
 * 生产单小组统计
 */
@Data
public class RestOrderNumCount implements Serializable {
    //编号
    private String orderNum;
    //款式名字
    private String names;
    //件数
    private String count;
}
