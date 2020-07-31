package cn.stylefeng.guns.yinhua.entity.yinhua.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 裁片价格日志
 */
@Data
@Accessors(chain = true)
public class ModelConstLog implements Serializable {
    //裁片信息Id
    private Integer num;
    //裁片价格
    private Double consts;
    //价格创建时间
    private Date createdTime;
}
