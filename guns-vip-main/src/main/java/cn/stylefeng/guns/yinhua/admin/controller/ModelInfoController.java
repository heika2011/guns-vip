package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelInfo;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelInfoParam;
import cn.stylefeng.guns.yinhua.admin.service.ModelInfoService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 控制器
 *
 * @author xiexin
 * @Date 2020-03-14 20:44:39
 */
@Controller
@RequestMapping("/modelInfo")
public class ModelInfoController extends BaseController {

    private String PREFIX = "/admin/modelInfo";

    @Autowired
    private ModelInfoService modelInfoService;

    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/modelInfo.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/modelInfo_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/modelInfo_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ModelInfoParam modelInfoParam) {
        this.modelInfoService.add(modelInfoParam);
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
    public ResponseData editItem(ModelInfoParam modelInfoParam) {
        this.modelInfoService.update(modelInfoParam);
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
    public ResponseData delete(ModelInfoParam modelInfoParam) {
        this.modelInfoService.delete(modelInfoParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(ModelInfoParam modelInfoParam) {
        ModelInfo detail = this.modelInfoService.getById(modelInfoParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(ModelInfoParam modelInfoParam) {
        return this.modelInfoService.findPageBySpec(modelInfoParam);
    }

}


