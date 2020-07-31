package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelImage;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelImageParam;
import cn.stylefeng.guns.yinhua.admin.service.ModelImageService;
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
@RequestMapping("/modelImage")
public class ModelImageController extends BaseController {

    private String PREFIX = "/admin/modelImage";

    @Autowired
    private ModelImageService modelImageService;

    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/modelImage.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/modelImage_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/modelImage_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ModelImageParam modelImageParam) {
        this.modelImageService.add(modelImageParam);
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
    public ResponseData editItem(ModelImageParam modelImageParam) {
        this.modelImageService.update(modelImageParam);
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
    public ResponseData delete(ModelImageParam modelImageParam) {
        this.modelImageService.delete(modelImageParam);
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
    public ResponseData detail(ModelImageParam modelImageParam) {
        ModelImage detail = this.modelImageService.getById(modelImageParam.getId());
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
    public LayuiPageInfo list(ModelImageParam modelImageParam) {
        return this.modelImageService.findPageBySpec(modelImageParam);
    }

}


