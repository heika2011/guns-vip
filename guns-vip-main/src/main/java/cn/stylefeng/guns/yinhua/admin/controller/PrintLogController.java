package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.consts.service.SysConfigService;
import cn.stylefeng.guns.yinhua.admin.entity.PrintLog;
import cn.stylefeng.guns.yinhua.admin.model.params.PrintLogParam;
import cn.stylefeng.guns.yinhua.admin.service.PrintLogService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 控制器
 *
 * @author xiexin
 * @Date 2020-04-18 21:30:46
 */
@Controller
@RequestMapping("/printLog")
public class PrintLogController extends BaseController {

    private String PREFIX = "/admin/printLog";

    @Autowired
    private PrintLogService printLogService;

    @Autowired
    private SysConfigService sysConfigService;
    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @RequestMapping("")
    public String index(Model model) {
        Boolean autoPrintFlag = ConstantsContext.getAutoPrintFlag();
        model.addAttribute("auto",autoPrintFlag);
        return PREFIX + "/printLog.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/printLog_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/printLog_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(PrintLogParam printLogParam) {
        this.printLogService.add(printLogParam);
        return ResponseData.success();
    }


    /**
     * 控制自动打印
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @RequestMapping("/updateAutoPrint")
    @ResponseBody
    public ResponseData updateAutoPrint(Integer flag) {
        ConstantsContext.putConstant("AUTO_PRINT_FLAG",flag.toString());
        return ResponseData.success();
    }

    /**
     * 清空记录
     */
    @RequestMapping("/removeAll")
    public void removeAll(HttpServletResponse response){
        printLogService.deleteAll();
        try {
            response.sendRedirect("/printLog/");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(PrintLogParam printLogParam) {
        this.printLogService.update(printLogParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(PrintLogParam printLogParam) {
        this.printLogService.delete(printLogParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(PrintLogParam printLogParam) {
        PrintLog detail = this.printLogService.getById(printLogParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(PrintLogParam printLogParam) {
        return this.printLogService.findPageBySpec(printLogParam);
    }

}


