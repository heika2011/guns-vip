package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelColorParam;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelParam;
import cn.stylefeng.guns.yinhua.admin.service.AdminModelService;
import cn.stylefeng.guns.yinhua.admin.service.ModelColorService;
import cn.stylefeng.guns.yinhua.entity.yinhua.model.*;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.ModelCut;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestCustomer;
import cn.stylefeng.guns.yinhua.service.CustomerService;
import cn.stylefeng.guns.yinhua.service.ModelService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * 控制器
 *
 * @author xiexin
 * @Date 2020-03-14 20:44:39
 */
@Controller
@RequestMapping("/model")
public class AdminModelController extends BaseController {

    private String PREFIX = "/admin/model";

    private String COLOR_PREFIX = "/admin/modelColor";

    @Autowired
    private AdminModelService adminmodelService;

    @Autowired
    private ModelService modelService;
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelColorService modelColorService;
    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/model.html";
    }

    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/add")
    public String add(org.springframework.ui.Model model) {
        List<RestCustomer> customerList = customerService.getCustomerList();
        model.addAttribute("num",new Long(simpleDateFormat.format(new Date()).substring(2,14)));
        model.addAttribute("customer",customerList);
        return PREFIX + "/model_add.html";
    }
    /**
     * 款式配色表页面跳转
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/color")
    public String color() {
        return COLOR_PREFIX + "/modelColor_add.html";
    }
    /**
     * 获取配色表列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/getColorList")
    @ResponseBody
    public LayuiPageInfo getColorList(ModelColorParam modelColorParam) {
        LayuiPageInfo pageBySpec = modelColorService.findPageBySpec(modelColorParam);
        return pageBySpec;
    }
    /**
     * 配色表添加
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addColor")
    @ResponseBody
    public JSONResult addColor(ModelColor modelColorParam) {
        ModelColor modelColor = modelService.addModelColor(modelColorParam);
        return new JSONResult("0","OK",modelColor.getModelId());
    }
    /**
     * 款式日志
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/getModelNote")
    public String getModelNote(Long num, org.springframework.ui.Model model) {
        List<ModelNote> modelNote = modelService.getModelNote(num);
        model.addAttribute("note",modelNote);
        return PREFIX + "/modelNote.html";
    }
    /**
     * 修改单个裁片页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/updateModelInfo")
    public String updateModelInfo(Integer id, org.springframework.ui.Model modelUtil) {
        ModelCut modelInfoById = modelService.getModelInfoById(id);
        modelUtil.addAttribute("modelInfos",modelInfoById);
        return PREFIX + "/modelInfo_update.html";
    }
    /**
     * 修改款式页面跳转
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/updateModel")
    public String updateModel(Long num, org.springframework.ui.Model modelUtil) {
        Model model = modelService.getModel(num);
        List<RestCustomer> customerList = customerService.getCustomerList();
        modelUtil.addAttribute("model",model);
        modelUtil.addAttribute("customer",customerList);
        return PREFIX + "/model_update.html";
    }
    /**
     * 删除款式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/deleteModel")
    @Permission
    @ResponseBody
    public JSONResult deleteModel(Long num) {
        modelService.deleteModel(num);
        return new JSONResult("0","Ok");
    }
    /**
     * 修改款式信息
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/updateModelData")
    @ResponseBody
    public JSONResult updateModelData(Model model) {
        modelService.updateModel(model);
        return new JSONResult("0","Ok");
    }
    /**
     * 新增裁片页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addInfo")
    public String addInfo() {
        return PREFIX + "/modelInfo_add.html";
    }
    /**
     * 新增裁片图片
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addInfoImage")
    public String addInfoImage() {
        return PREFIX + "/modelInfo_image_add.html";
    }
    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/model_edit.html";
    }
    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/colorEdit")
    public String colorEdit() {
        return COLOR_PREFIX + "/modelColor_edit.html";
    }
    /**
     * 编辑图片
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/updateImage")
    public String updateImage(Integer id,Integer type,org.springframework.ui.Model model) {
        ModelImage modelImage = modelService.getModelImage(id, type);
        model.addAttribute("images",modelImage);
        return PREFIX + "/modelInfo_image_update.html";
    }

    /**
     * 新增图片 返回图片id
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addModelInfoImage")
    @ResponseBody
    public JSONResult addModelInfoImage(ModelImage modelImage) {
        Integer integer = modelService.addModelImage(modelImage);
        return new JSONResult("0","ok",integer);
    }
    /**
     * 获取图片
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/getModelInfoImage")
    @ResponseBody
    public JSONResult getModelInfoImage(Integer type, Integer id) {
        ModelImage modelImage = modelService.getModelImage(id,type);
        return new JSONResult("0","ok",modelImage);
    }
    /**
     * 获取款式对应裁片信息
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/getModelInfoAndImage")
    @ResponseBody
    public LayuiPageInfo getModelInfoAndImage(Long num) {
        List<ModelCut> modelInfo = modelService.getModelInfo(num);
        LayuiPageInfo layuiPageInfo = new LayuiPageInfo();
        layuiPageInfo.setData(modelInfo);
        return layuiPageInfo;
    }
    /**
     * 修改图片
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/updateModelImage")
    @ResponseBody
    public JSONResult updateModelImage(ModelImage modelImage) {
        modelService.updateModelImage(modelImage);
        return new JSONResult("0","OK");
    }
    /**
     * 新增裁片信息
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addModelInfo")
    @ResponseBody
    public JSONResult addModelInfo(ModelInfo modelInfo,Integer imageId) {
        ModelInfo modelInfo1 = modelService.addModelInfo(modelInfo, imageId);
        return new JSONResult("0","OK",modelInfo1.getNum());
    }

    /**
     * 修改裁片信息
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/updateModelInfoData")
    @ResponseBody
    public JSONResult updateModelInfoData(ModelInfo modelInfo,Integer imageId) {
        ModelInfo modelInfo1 = modelService.updateModelInfo(modelInfo, imageId);
        return new JSONResult("0","OK",modelInfo1.getNum());
    }
    /**
     * 删除款式信息
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/deleteModelInfoAndImage")
    @ResponseBody
    public JSONResult deleteModelInfoAndImage(Integer id) {
        modelService.deleteModelInfoAndImage(id);
        return new JSONResult("0","OK");
    }
    /**
     * 删除配色表数据
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/deleteModelColor")
    @ResponseBody
    public JSONResult deleteModelColor(Integer id) {
        modelService.deleteModelColorForId(id);
        return new JSONResult("0","OK");
    }
    /**
     * 删除配色表数据
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/detailModelColor")
    @ResponseBody
    public ResponseData detailModelColor(Integer id) {
        cn.stylefeng.guns.yinhua.admin.entity.ModelColor byId = modelColorService.getById(id);
        return ResponseData.success(byId);
    }
    /**
     * 新增款式信息
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addModel")
    @ResponseBody
    public JSONResult addModel(Model model) {
        Model model1 = modelService.addModel(model);
        return new JSONResult("0","OK",model1.getNum());
    }
    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ModelParam modelParam) {
        this.adminmodelService.add(modelParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(ModelParam modelParam) {
        this.adminmodelService.update(modelParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(ModelParam modelParam) {
        this.adminmodelService.delete(modelParam);
        return ResponseData.success();
    }


    /*@RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ModelParam modelParam) {
        Model detail = this.adminmodelService.getById(modelParam.getId());
        return ResponseData.success(detail);
    }*/

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ModelParam modelParam) {
        return this.adminmodelService.findPageBySpec(modelParam);
    }

}


