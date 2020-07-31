package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelColor;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelColorParam;
import cn.stylefeng.guns.yinhua.admin.service.ModelColorService;
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
@RequestMapping("/modelColor")
public class ModelColorController extends BaseController {

    private String PREFIX = "/admin/modelColor";

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
        return PREFIX + "/modelColor.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/modelColor_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/modelColor_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ModelColorParam modelColorParam) {
        this.modelColorService.add(modelColorParam);
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
    public ResponseData editItem(ModelColorParam modelColorParam) {
        this.modelColorService.update(modelColorParam);
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
    public ResponseData delete(ModelColorParam modelColorParam) {
        this.modelColorService.delete(modelColorParam);
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
    public ResponseData detail(ModelColorParam modelColorParam) {
        ModelColor detail = this.modelColorService.getById(modelColorParam.getId());
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
    public LayuiPageInfo list(ModelColorParam modelColorParam) {
        return this.modelColorService.findPageBySpec(modelColorParam);
    }

}


