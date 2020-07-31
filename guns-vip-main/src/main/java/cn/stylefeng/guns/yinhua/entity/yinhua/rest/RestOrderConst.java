package cn.stylefeng.guns.yinhua.entity.yinhua.rest;

import cn.stylefeng.guns.yinhua.entity.yinhua.model.Model;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 报价单信息
 */
@Data
public class RestOrderConst implements Serializable {
    //生产单编号
    private String orderNum;
    //单价
    private String consts;

    //单位
    private String units;
    //版费
    private String sceenConst;
    //备注
    private String note;
    //改价备注
    private String changeNote;
    //历史报价
    private List<RestOrderConstLog> constHistory;
    //裁片信息
    private List<ModelCut> modelInfos;
    //款式信息
    private Model model;

    private Date createdTime;
}
