package cn.stylefeng.guns.yinhua.entity.yinhua.rest;

import cn.stylefeng.guns.yinhua.entity.yinhua.model.Model;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelNote;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 订单详情或者款式详情
 */
@Data
@Accessors(chain = true)
public class RestAllModel implements Serializable {

    //裁片信息
    private List<ModelCut> modelInfo;
    //款式信息
    private Model model;
    //款式日志
    private List<ModelNote> modelNote;
}
