package cn.stylefeng.guns.yinhua.mobile.controller.model;


import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.ModelFile;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.ModelCut;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllModel;
import cn.stylefeng.guns.yinhua.service.ModelService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 款式请求类
 */
@RestController
@RequestMapping("/mobile/model")
public class ModelController {

    @Autowired
    private ModelService modelService;
    /**
     * 分页获取款式列表
     * @return
     */
    @RequestMapping("/getModelList")
    @Permission
    @ApiOperation(value="获取款式列表",notes="token,初始页Page,条数pageSize,订单状态筛选，搜索关键字", httpMethod = "GET")
    public PageResult<Model> getModelList(String token, Long page, Long pageSize, Integer status, String name){
        Page<Model> modelList = modelService.getModelList(page, pageSize,name,status);
        return new PageResult<Model>(page,pageSize,modelList.getTotal(),modelList.getRecords().size(),modelList.getRecords());
    }
    /**
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @ApiOperation(value="获取数据汇总",notes="token" , httpMethod = "GET")
    public JSONResult getDataCount(String token){
        RestOrderCount dataCount = modelService.getDataCount();
        return new JSONResult("0","OK",dataCount);
    }

    /**
     * 款式复制
     * @return
     */
    @RequestMapping("/copyModel")
    @ApiOperation(value="款式复制",notes="token" , httpMethod = "GET")
    public JSONResult copyModel(String token,Long num){
        Long aLong = modelService.copyModel(num);
        return new JSONResult("0","OK",aLong);
    }
    /**
     * 单个款式详情获取
     * @return
     */
    @RequestMapping("/getAllModel")
    @ApiOperation(value="单个款式详情获取",notes="token,款式编号", httpMethod = "GET")
    public JSONResult getAllModel(String token,Long num){
        RestAllModel restAllModel = modelService.getAllModel(num);
        return new JSONResult("0","OK",restAllModel);
    }
    /**
     * 裁片图片删除
     * @return
     */
    @RequestMapping("/deleteModelImage")
    @ApiOperation(value="删除款式图片",notes="token,图片url", httpMethod = "GET")
    public JSONResult deleteModelImage(String token,String url){
        modelService.deleteModelImage(url);
        return new JSONResult("0","ok");
    }
    /**
     * 裁片图片添加
     * @return
     */
    @RequestMapping("/addModelImage")
    @ApiOperation(value="删除款式图片",notes="token,图片信息，多个图片Url请以逗号隔开,返回图片id,提交裁片信息需要此参数", httpMethod = "GET")
    public JSONResult addModelImage(String token,ModelImage modelImage){
        Integer id = modelService.addModelImage(modelImage);
        return new JSONResult("0","ok",id);
    }
    /**
     * 裁片图片获取
     * @return
     */
    @RequestMapping("/getModelImage")
    @ApiOperation(value="删除款式图片",notes="token,裁片id,图片类型", httpMethod = "GET")
    public JSONResult getModelImage(String token,Integer id,Integer type){
        ModelImage modelImage = modelService.getModelImage(id,type);
        return new JSONResult("0","ok",modelImage);
    }
    /**
     * 裁片图片修改
     * @return
     */
    @RequestMapping("/updateModelImage")
    @ApiOperation(value="删除款式图片",notes="token,图片信息", httpMethod = "GET")
    public JSONResult updateModelImage(String token,ModelImage modelImage){
        modelService.updateModelImage(modelImage);
        return new JSONResult("0","ok");
    }
    /**
     * 裁片信息添加
     * @return
     */
    @RequestMapping("/addModelInfo")
    @ApiOperation(value="裁片信息添加,修改",notes="token,图片id集合，裁片信息，修改时需要提交num(款式编号)，如果是第一次添加将会生成款式编号，并返回,这个编号将会随着款式所有组件公用", httpMethod = "GET")
    public JSONResult addModelInfo(String token,ModelInfo modelInfo,Integer imageId){
        ModelInfo modelInfo1 = modelService.addModelInfo(modelInfo,imageId);
        return new JSONResult("0","ok",modelInfo1.getNum());
    }

    /**
     * 裁片信息修改
     * @return
     */
    @RequestMapping("/updateModelInfo")
    @ApiOperation(value="裁片信息修改",notes="token,裁片信息，修改时需要提交num(款式编号)，如果是第一次添加将会生成款式编号，并返回,这个编号将会随着款式所有组件公用", httpMethod = "GET")
    public JSONResult updateModelInfo(String token,ModelInfo modelInfo,Integer imageId){
        ModelInfo modelInfo1 = modelService.updateModelInfo(modelInfo, imageId);
        return new JSONResult("0","ok",modelInfo1.getNum());
    }
    /**
     * 删除款式信息
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @Permission
    @RequestMapping("/deleteModelInfoAndImage")
    @ResponseBody
    public JSONResult deleteModelInfoAndImage(Integer id) {
        modelService.deleteModelInfoAndImage(id);
        return new JSONResult("0","OK");
    }
    /**
     * 单个裁片信息查看
     * @return
     */
    @RequestMapping("/getModelInfo")
    @ApiOperation(value="单个裁片信息查看",notes="token,裁片id，修改时需要提交num(款式编号)，如果是第一次添加将会生成款式编号，并返回,这个编号将会随着款式所有组件公用", httpMethod = "GET")
    public JSONResult getModelInfoById(String token,Integer id){
        ModelCut modelCut = modelService.getModelInfoById(id);
        return new JSONResult("0","ok",modelCut);
    }
    /**
     * 裁片列表获取
     * @return
     */
    @RequestMapping("/getModelInfoList")
    @ApiOperation(value="裁片列表获取",notes="token,款式编号", httpMethod = "GET")
    public JSONResult getModelInfo(String token,Long num){
        List<ModelCut> modelInfo = modelService.getModelInfo(num);
        return new JSONResult("0","ok",modelInfo);
    }
    /**
     * 裁片配色表添加
     * @return
     */
    @Permission
    @RequestMapping("/addModelColor")
    @ApiOperation(value="裁片配色添加",notes="token,配色数据，如果是第一次添加将会生成款式编号，并返回,这个编号将会随着款式所有组件公用", httpMethod = "GET")
    public JSONResult addModelColor(String token, ModelColor modelColor){
        ModelColor modelColor1 = modelService.addModelColor(modelColor);
        return new JSONResult("0","ok",modelColor1.getModelId());
    }
    /**
     * 裁片配色表获取
     * @return
     */
    @RequestMapping("/getModelColor")
    @ApiOperation(value="裁片配色表获取",notes="token,款式编号", httpMethod = "GET")
    public JSONResult getModelColor(String token,Long num){
        List<ModelColor> modelColor1 = modelService.getModelColor(num);
        return new JSONResult("0","ok",modelColor1);
    }
    /**
     * 裁片配色表删除
     * @return
     */
    @Permission
    @RequestMapping("/deleteModelColor")
    @ApiOperation(value="裁片配色表删除",notes="token,面料色", httpMethod = "GET")
    public JSONResult deleteModelColor(String token,String color){
        modelService.deleteModelColor(color);
        return new JSONResult("0","ok");
    }

    /**
     * 款式信息添加
     * @return
     */
    @RequestMapping("/addModel")
    @ApiOperation(value="款式信息添加",notes="token,款式信息，如果是第一次添加将会生成款式编号，并返回,这个编号将会随着款式所有组件公用", httpMethod = "GET")
    public JSONResult addModel(String token,Model model){
        Model model1 = modelService.addModel(model);
        return new JSONResult("0","ok",model1.getNum());
    }
    /**
     * 款式信息修改
     * @return
     */
    @RequestMapping("/updateModel")
    @ApiOperation(value="款式信息添加",notes="token,款式信息，如果是第一次添加将会生成款式编号，并返回,这个编号将会随着款式所有组件公用", httpMethod = "GET")
    public JSONResult updateModel(String token,Model model){
        modelService.updateModel(model);
        return new JSONResult("0","ok");
    }
    /**
     * 款式信息获取
     * @return
     */
    @RequestMapping("/getModel")
    @ApiOperation(value="款式信息获取",notes="token,面料色", httpMethod = "GET")
    public JSONResult getModel(String token,Long num){
        Model model = modelService.getModel(num);
        return new JSONResult("0","ok",model);
    }
    /**
     * 款式日志添加
     * @return
     */
    @RequestMapping("/addModelNote")
    @Permission
    @ApiOperation(value="款式日志添加",notes="token,款式日志信息", httpMethod = "GET")
    public JSONResult getModel(String token, ModelNote modelNote){
        modelService.addModelNote(modelNote);
        return new JSONResult("0","ok");
    }
    /**
     * 款式日志删除
     * @return
     */
    @RequestMapping("/deleteModelNote")
    @Permission
    @ApiOperation(value="款式日志添加",notes="token,款式日志信息", httpMethod = "GET")
    public JSONResult deleteModelNote(String token, Integer id){
        modelService.deleteModelNote(id);
        return new JSONResult("0","ok");
    }

    /**
     * 款式日志获取
     * @return
     */
    @RequestMapping("/getModelNote")
    @ApiOperation(value="款式日志获取",notes="token,款式编号", httpMethod = "GET")
    public JSONResult getModelNote(String token,Long num){
        List<ModelNote> modelNotes = modelService.getModelNote(num);
        return new JSONResult("0","ok",modelNotes);
    }

    /**
     * 款式删除
     * @return
     */
    @Permission
    @RequestMapping("/deleteModel")
    @ApiOperation(value="款式删除",notes="token,款式编号", httpMethod = "GET")
    public JSONResult deleteModel(String token,Long num){
        modelService.deleteModel(num);
        return new JSONResult("0","ok");
    }

    /**
     * 款式附件添加
     * @return
     */
    @Permission
    @RequestMapping("/addModelFile")
    @ApiOperation(value="款式附件添加",notes="token,款式编号，url", httpMethod = "GET")
    public JSONResult addModelFile(String token,ModelFile modelFile){
        modelService.addModelFile(modelFile);
        return new JSONResult("0","ok");
    }
    /**
     * 款式附件删除
     * @return
     */
    @Permission
    @RequestMapping("/deleteModelFile")
    @ApiOperation(value="款式附件删除",notes="token,文件id", httpMethod = "GET")
    public JSONResult deleteModelFile(String token,Integer id){
        modelService.deleteModelFile(id);
        return new JSONResult("0","ok");
    }
    /**
     * 款式附件获取
     * @return
     */
    @RequestMapping("/getModelFile")
    @ApiOperation(value="款式附件获取",notes="token,款式id", httpMethod = "GET")
    public JSONResult getModelFile(String token,Long num){
        List<ModelFile> modelFiles = modelService.getModelFile(num);
        return new JSONResult("0","ok",modelFiles);
    }
    /**
     * 款式配色表修改
     * @return
     */
    @RequestMapping("/updateModelColor")
    @ApiOperation(value="款式附件获取",notes="token,款式id", httpMethod = "GET")
    public JSONResult updateModelColor(String token,ModelColor modelColor){
        modelService.updateModelColor(modelColor);
        return new JSONResult("0","操作成功");
    }
}
