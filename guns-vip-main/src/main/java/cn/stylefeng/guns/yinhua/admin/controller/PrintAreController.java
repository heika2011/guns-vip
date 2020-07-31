package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.PrintAre;
import cn.stylefeng.guns.yinhua.admin.model.params.PrintAreParam;
import cn.stylefeng.guns.yinhua.admin.service.PrintAreService;
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
 * @Date 2020-05-23 01:49:03
 */
@Controller
@RequestMapping("/printAre")
public class PrintAreController extends BaseController {

    private String PREFIX = "/admin/printAre";

    @Autowired
    private PrintAreService printAreService;

    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/printAre.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/printAre_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/printAre_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(PrintAreParam printAreParam) {
        this.printAreService.add(printAreParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(PrintAreParam printAreParam) {
        this.printAreService.update(printAreParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(PrintAreParam printAreParam) {
        this.printAreService.delete(printAreParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(PrintAreParam printAreParam) {
        PrintAre detail = this.printAreService.getById(printAreParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(PrintAreParam printAreParam) {
        return this.printAreService.findPageBySpec(printAreParam);
    }

}


