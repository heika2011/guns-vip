package cn.stylefeng.guns.yinhua.admin.controller;


import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.sys.modular.system.model.entity.Role;
import cn.stylefeng.guns.sys.modular.system.model.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.RoleService;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 权限管理
 */
@Controller
@RequestMapping("/permission")
public class AdminPerController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    private String PREFIX = "/admin/config";

    @RequestMapping("")
    public String index(Model model){
        List<Role> pid = roleService.list(new QueryWrapper<Role>().eq("pid", "1234732820551712770"));
        List<User> list = userService.list(null);
        model.addAttribute("user",list);
        model.addAttribute("role",pid);
        return PREFIX + "/WhoSee.html";
    }

    @RequestMapping("updatePer")
    @ResponseBody
    public JSONResult updatePer(@RequestBody Map param){
        roleService.updateSee(param);
        return new JSONResult("0","OK");
    }
}
