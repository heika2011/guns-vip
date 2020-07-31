package cn.stylefeng.guns.yinhua.entity.yinhua.rest;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 获取小组的统计数据
 */
@Data
public class RestOrderNumCountFor implements Serializable {

    /**
     * 数据
     */
    private List<RestOrderNumCount> restOrderNumCounts;
    /**
     * 提成
     */
    private String money;
}
