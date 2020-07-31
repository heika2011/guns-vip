package cn.stylefeng.guns.yinhua.entity.yinhua.order;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
public class OrderTeamLog implements Serializable {
    //小组Id
    private Integer team;
    //结束时间
    private Date overTime;
    //订单价格
    private Double orderConst;
}
