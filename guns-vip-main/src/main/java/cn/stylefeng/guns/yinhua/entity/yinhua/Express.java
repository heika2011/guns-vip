package cn.stylefeng.guns.yinhua.entity.yinhua;

import lombok.Data;

import java.io.Serializable;

/**
 * 快递相关
 */
@Data
public class Express implements Serializable {

    /**
     * 生产单编号
     */
    private String orderNum;
     /**
     * 快递名称
     */
    private String expressName;

    /**
     * 快递编号
     */
    private String expressNum;
}
