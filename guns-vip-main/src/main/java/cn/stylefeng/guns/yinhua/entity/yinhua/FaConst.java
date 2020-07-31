package cn.stylefeng.guns.yinhua.entity.yinhua;

import lombok.Data;

import java.io.Serializable;

/**
 * 财务设置金额
 */
@Data
public class FaConst implements Serializable {

    //生产单编号
    private String orderNum;
    //财务设置的金额
    private Double faConst;
    //财务设置编号
    private String faNum;
}
