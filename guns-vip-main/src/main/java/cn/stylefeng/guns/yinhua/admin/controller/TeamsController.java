package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.Teams;
import cn.stylefeng.guns.yinhua.admin.model.params.AdminUserParam;
import cn.stylefeng.guns.yinhua.admin.model.params.TeamsParam;
import cn.stylefeng.guns.yinhua.admin.model.result.TeamsResult;
import cn.stylefeng.guns.yinhua.admin.service.TeamsService;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeamU;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 控制器
 *
 * @author xiexin
 * @Date 2020-03-14 20:44:40
 */
@Controller
@RequestMapping("/teams")
public class TeamsController extends BaseController {

    private String PREFIX = "/admin/teams";

    @Autowired
    private TeamsService teamsService;


    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/teams.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/teams_add.html";
    }

    /**
     * 修改页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/update")
    public String update(Model model) {
        List<AdminUserParam> allPeople = this.teamsService.findAllPeople();
        model.addAttribute("people",allPeople);
        return PREFIX + "/teams_update.html";
    }
    /**
     * 获取所有用户
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/getAllPeople")
    @ResponseBody
    public JSONResult getAllPeople() {
        List<AdminUserParam> allPeople = this.teamsService.findAllPeople();
        return new JSONResult("0","ok",allPeople);
    }
    /**
     * 删除小组
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/deleteTeam")
    @ResponseBody
    public JSONResult deleteTeam(Integer id) {
        teamsService.deleteTeam(id);
        return new JSONResult("0","ok");
    }
    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/edit")
    public String edit(Integer id,Model model) {
        UserTeam teamsResults = teamsService.findTeamAndMemberById(id);
        List<AdminUserParam> allPeople = this.teamsService.findAllPeople();
        model.addAttribute("team",teamsResults);
        model.addAttribute("people",allPeople);
        return PREFIX + "/teams_edit.html";
    }
    /**
     * 修改
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/updateTeam")
    @ResponseBody
    public JSONResult updateTeam(@RequestBody UserTeam userTeam, @RequestParam(required = true)Boolean flag) {
        teamsService.updateTeam(userTeam,flag);
        return new JSONResult("0","OK");
    }
    /**
     * 添加
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addTeamUser")
    @ResponseBody
    public JSONResult addTeamUser(@RequestBody UserTeam userTeam,@RequestParam(required = true)Boolean flag) {
        teamsService.addTeamUser(userTeam,flag);
        return new JSONResult("0","OK");
    }
    /**
     * 删除某一个成员
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/deleteTeamUser")
    @ResponseBody
    public JSONResult deleteTeamUser(Integer id) {
        teamsService.deleteTeamUser(id);
        return new JSONResult("0","OK");
    }
    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(TeamsParam teamsParam) {
        this.teamsService.add(teamsParam);
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
    public ResponseData editItem(TeamsParam teamsParam) {
        this.teamsService.update(teamsParam);
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
    public ResponseData delete(TeamsParam teamsParam) {
        this.teamsService.delete(teamsParam);
        return ResponseData.success();
    }

    /**
     * 查看单个小组所有人
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/detail")
    @ResponseBody
    public LayuiPageInfo detail(TeamsParam teamsParam) {
        LayuiPageInfo teamAndMember = teamsService.findTeamAndMember(teamsParam);
        return teamAndMember;
    }
    /**
     * 查看单个人
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @RequestMapping("/detailinfo")
    @ResponseBody
    public ResponseData detailinfo(TeamsParam teamsParam) {
        TeamsResult bySpec = teamsService.findBySpec(teamsParam);
        return ResponseData.success(bySpec);
    }
    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(TeamsParam teamsParam) {
        return this.teamsService.findPageBySpec(teamsParam);
    }
    /**
     * 查询用户列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    @ResponseBody
    @RequestMapping("/userlist")
    public ResponseData userlist() {
        return ResponseData.success(this.teamsService.findAllPeople());
    }
}


