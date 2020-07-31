package cn.stylefeng.guns.yinhua.entity.yinhua.rest;

import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelImage;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class ModelCut {

    private List<ModelImage> modelImage;
    private ModelInfo modelInfo;
}
