package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelNote;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelNoteParam;
import cn.stylefeng.guns.yinhua.admin.service.ModelNoteService;
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
@RequestMapping("/modelNote")
public class ModelNoteController extends BaseController {

    private String PREFIX = "/admin/modelNote";

    @Autowired
    private ModelNoteService modelNoteService;

    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/modelNote.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/modelNote_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/modelNote_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(ModelNoteParam modelNoteParam) {
        this.modelNoteService.add(modelNoteParam);
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
    public ResponseData editItem(ModelNoteParam modelNoteParam) {
        this.modelNoteService.update(modelNoteParam);
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
    public ResponseData delete(ModelNoteParam modelNoteParam) {
        this.modelNoteService.delete(modelNoteParam);
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
    public ResponseData detail(ModelNoteParam modelNoteParam) {
        ModelNote detail = this.modelNoteService.getById(modelNoteParam.getId());
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
    public LayuiPageInfo list(ModelNoteParam modelNoteParam) {
        return this.modelNoteService.findPageBySpec(modelNoteParam);
    }

}


