package cn.stylefeng.guns.yinhua.entity.yinhua.rest;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class RestCustomer implements Serializable {

    //客户编号
    private String num;
    //客户名字
    private String name;
}
