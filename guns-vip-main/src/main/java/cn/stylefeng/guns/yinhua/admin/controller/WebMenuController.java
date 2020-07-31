package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.model.params.CustomerParam;
import cn.stylefeng.guns.yinhua.admin.service.WebMenuService;
import cn.stylefeng.guns.yinhua.entity.SysRole;
import cn.stylefeng.guns.yinhua.entity.WebMenu;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

/**
 * 动态菜单控制器
 *
 * @author fengshuonan
 * @Date 2017年1月11日 下午1:08:17
 */
@Controller
@RequestMapping("/webMenu")
public class WebMenuController {

    private String PREFIX = "/admin/config";

    @Autowired
    WebMenuService webMenuService;

    @RequestMapping("/list")
    public String list( Model model) {

        return PREFIX + "/webMenu_list.html";
    }

    @RequestMapping("")
    public String roleAssign( Model model) {
        List<WebMenu>  allMenu= webMenuService.getAllMenu();
        List<SysRole>  allRole= webMenuService.getAllRole();

        model.addAttribute("allMenu",allMenu);
        model.addAttribute("allRole",allRole);

        return PREFIX + "/WebMenu.html";
    }

    @RequestMapping("edit")
    public String edit( Model model,String id) {
        List<WebMenu> menus = webMenuService.selectListAll(null);
        List<Map> m = webMenuService.selectList(id);
        menus.forEach(webMenu -> {
            for(Map map:m){
                if(((Integer)map.get("menu_id")).equals(webMenu.getId())){
                    webMenu.setSee(1);
                }
            }
        });
        model.addAttribute("id",id);
        List<SysRole>  allRole= webMenuService.getAllRole();
        model.addAttribute("allRole",allRole);
        model.addAttribute("list",menus);

        return PREFIX + "/webMenu_update.html";
    }
    @RequestMapping("updatePer")
    @ResponseBody
    public JSONResult updatePer(@RequestBody Map param){
        webMenuService.InsertRoldMenu(param);
        return new JSONResult("0","OK");
    }

    @ResponseBody
    @RequestMapping("/getlist")
    public LayuiPageInfo list( ) {
        return this.webMenuService.findPageBySpec();
    }
    @ResponseBody
    @RequestMapping("/delete")
    public JSONResult DeleteList(String roleId) {
        webMenuService.deleteByRoleAllMenu(roleId);
        return new JSONResult("0","OK");

    }





}
