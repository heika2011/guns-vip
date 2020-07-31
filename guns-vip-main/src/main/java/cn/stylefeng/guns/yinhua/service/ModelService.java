package cn.stylefeng.guns.yinhua.service;


import cn.stylefeng.guns.yinhua.entity.ModelFile;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.ModelCut;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllModel;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface ModelService {
    Page<Model> getModelList(Long page, Long pageSize, String name, Integer status);
    Model addModel(Model model);
    Integer addModelImage(ModelImage modelImage);
    void deleteModelImage(String url);
    void updateModelImage(ModelImage modelImage);
    ModelInfo addModelInfo(ModelInfo modelInfo,Integer imageId);
    ModelColor addModelColor(ModelColor modelColor);
    void deleteModelColor(String color);
    ModelImage getModelImage(Integer id,Integer type);
    List<ModelCut> getModelInfo(Long num);
    List<ModelColor> getModelColor(Long num);
    Model getModel(Long num);
    void addModelNote(ModelNote modelNote);
    List<ModelNote> getModelNote(Long num);
    RestAllModel getAllModel(Long num);
    ModelInfo updateModelInfo(ModelInfo modelInfo, Integer imageId);
    List<ModelInfo> getModelCut(Long num);
    void changeModelInfoMuchs(Integer id, Double muchs);
    void deleteModel(Long num);
    RestOrderCount getDataCount();
    ModelCut getModelInfoById(Integer id);
    void updateModel(Model model);
    void deleteModelInfoAndImage(Integer id);
    void addModelFile(ModelFile modelFile);
    void deleteModelFile(Integer id);
    List<ModelFile> getModelFile(Long num);
    void deleteModelColorForId(Integer id);
    void updateModelColor(ModelColor modelColor);

    void deleteModelNote(Integer id);

    String getModelNoteByOrderNum(String orderNum);

    void saveModelNote(String orderNum, String note);

    Long copyModel(Long num);
}
