package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysCount;
import cn.stylefeng.guns.sys.modular.system.model.params.SysCountParam;
import cn.stylefeng.guns.sys.modular.system.service.SysCountService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;


/**
 * 控制器
 *
 * @author xiexin
 * @Date 2020-03-24 14:29:57
 */
@Controller
@RequestMapping("/sysCount")
public class SysCountController extends BaseController {

    private String PREFIX = "/admin/sysCount";

    @Autowired
    private SysCountService sysCountService;

    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    @RequestMapping("")
    public String index(Model model) {
        List<SysCount> sysCounts = sysCountService.getBaseMapper().selectList(null);
        model.addAttribute("count",sysCounts);
        return PREFIX + "/sysCount_edit.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/sysCount_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/sysCount_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SysCountParam sysCountParam) {
        this.sysCountService.add(sysCountParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(@RequestParam(required = false) Map param) {
        sysCountService.updateByType(param);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(SysCountParam sysCountParam) {
        this.sysCountService.delete(sysCountParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(SysCountParam sysCountParam) {
        SysCount detail = this.sysCountService.getById(sysCountParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SysCountParam sysCountParam) {
        return this.sysCountService.findPageBySpec(sysCountParam);
    }

}


