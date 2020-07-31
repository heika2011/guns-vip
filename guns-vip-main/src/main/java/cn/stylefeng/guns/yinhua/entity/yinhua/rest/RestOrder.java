package cn.stylefeng.guns.yinhua.entity.yinhua.rest;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class RestOrder implements Serializable {

    //款式id
    private Long modelId;
    //生产单编号
    private String orderNum;

    //跟单员
    private String name;
    //生产单进度
    private Integer orderProgress;
    //生产单类型
    private Integer orderType;
    //生产单备注
    private String note;
    //下单时间
    private Date createdTime;
    //交货时间
    private Date overTime;
    //生产单数量
    private Double allcount;
}
