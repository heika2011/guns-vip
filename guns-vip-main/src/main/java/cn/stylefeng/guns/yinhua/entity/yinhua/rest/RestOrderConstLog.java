package cn.stylefeng.guns.yinhua.entity.yinhua.rest;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 生产单历史报价数据
 */
@Data
public class RestOrderConstLog implements Serializable {
    //日期
    private Date createdTime;
    //报价
    private String consts;
    //单位
    private String units;
}
