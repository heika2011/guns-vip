package cn.stylefeng.guns.yinhua.entity.yinhua.order;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 生产单最终报价
 */
@Data
@Accessors(chain = true)
public class OrderConstLog implements Serializable {

    //生产单编号
    private String orderNum;
    //生产单回扣
    private Double kickback;
    //生产单最终报价
    private Double lastConst;
    //生产单报价单创建时间
    private Date createdTime;
    //单价
    private Double consts;
    //赔偿金额
    private Double makeUp;
    //版费
    private Double sceenConst;
    //备注
    private String orderNote;

    //单位
    private String units;
}
