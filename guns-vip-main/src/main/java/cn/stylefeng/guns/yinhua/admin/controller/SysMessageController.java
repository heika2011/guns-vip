package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.node.MenuNode;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.model.entity.Menu;
import cn.stylefeng.guns.sys.modular.system.model.entity.Role;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysMessage;
import cn.stylefeng.guns.sys.modular.system.model.entity.User;
import cn.stylefeng.guns.sys.modular.system.model.params.SysMessageParam;
import cn.stylefeng.guns.sys.modular.system.service.MenuService;
import cn.stylefeng.guns.sys.modular.system.service.RoleService;
import cn.stylefeng.guns.sys.modular.system.service.SysMessageService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 控制器
 *
 * @author xiexin
 * @Date 2020-03-23 16:56:33
 */
@Controller
@RequestMapping("/sysMessage")
public class SysMessageController extends BaseController {

    private String PREFIX = "/admin/sysMessage";

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private MenuService menuService;
    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/sysMessage.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    @RequestMapping("/add")
    public String add(Model model) {
        List<MenuNode> outMenusByRoleIds = menuService.getOutMenusByRoleIds();
        List<Role> pid = roleService.list(new QueryWrapper<Role>().eq("pid", "1234732820551712770"));
        model.addAttribute("menu",outMenusByRoleIds);
        model.addAttribute("role",pid);
        return PREFIX + "/sysMessage_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    @RequestMapping("/edit")
    public String edit(Integer id,Model model) {
        List<Role> pid = roleService.list(new QueryWrapper<Role>().eq("pid", "1234732820551712770"));
        List<User> list = userService.list(null);
        SysMessage msg = sysMessageService.getById(id);
        model.addAttribute("user",list);
        model.addAttribute("role",pid);
        model.addAttribute("message",msg);
        return PREFIX + "/sysMessage_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(SysMessageParam sysMessageParam) {
        this.sysMessageService.add(sysMessageParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(SysMessageParam sysMessageParam) {
        this.sysMessageService.update(sysMessageParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(SysMessageParam sysMessageParam) {
        this.sysMessageService.delete(sysMessageParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(SysMessageParam sysMessageParam) {
        SysMessage detail = this.sysMessageService.getById(sysMessageParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(SysMessageParam sysMessageParam) {
        return this.sysMessageService.findPageBySpec(sysMessageParam);
    }

}


