package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.model.entity.Menu;
import cn.stylefeng.guns.sys.modular.system.model.entity.Role;
import cn.stylefeng.guns.yinhua.admin.entity.OrderRoleChild;
import cn.stylefeng.guns.yinhua.admin.model.params.AdminUserParam;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderRoleChildParam;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderRoleParam;
import cn.stylefeng.guns.yinhua.admin.service.OrderRoleChildService;
import cn.stylefeng.guns.yinhua.admin.service.OrderRoleService;
import cn.stylefeng.guns.yinhua.admin.service.TeamsService;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderRole;
import cn.stylefeng.guns.yinhua.mobile.mapper.OrderRoleMapper;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;


/**
 * 控制器
 *
 * @author xiexin
 * @Date 2020-03-16 18:07:05
 */
@Controller
@RequestMapping("/orderRoleChild")
public class OrderRoleChildController extends BaseController {

    private String PREFIX = "/admin/orderRoleChild";

    @Autowired
    private OrderRoleChildService orderRoleChildService;
    @Autowired
    private OrderRoleMapper orderRoleMapper;
    @Autowired
    private OrderRoleService orderRoleService;
    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/orderRoleChild.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/add")
    public String add(Model model) {
        List<OrderRole> name = orderRoleMapper.selectList(new QueryWrapper<OrderRole>().groupBy("name"));
        model.addAttribute("name",name);
        return PREFIX + "/orderRoleChild_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/edit")
    public String edit(Integer id, Model model) {
        List<OrderRoleChild> parent_id = orderRoleChildService.list(new QueryWrapper<OrderRoleChild>().eq("parent_id", id).orderByAsc("shunxu"));
        List<Menu> menu = orderRoleChildService.findMenu();
        List<Role> role = orderRoleChildService.findRole();
        List<OrderRole> name = orderRoleMapper.selectList(new QueryWrapper<OrderRole>().groupBy("name"));
        model.addAttribute("name",name);
        model.addAttribute("menu",menu);
        model.addAttribute("people",role);
        model.addAttribute("parent",parent_id);
        return PREFIX + "/orderRoleChild_edit.html";
    }

    /**
     * 修改进度
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/updateRoleChild")
    @ResponseBody
    public JSONResult updateRoleChild(@RequestBody List<OrderRoleChild> orderRoleChild) {
        orderRoleChildService.updateRoleChild(orderRoleChild);
        return new JSONResult("0","ok");
    }
    /**
     * 删除进度
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/deleteOrderRoleChild")
    @ResponseBody
    public JSONResult deleteOrderRoleChild(Integer id) {
        orderRoleChildService.deleteOrderRoleChild(id);
        return new JSONResult("0","ok");
    }
    /**
     * 获取菜单
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/getMenu")
    @ResponseBody
    public JSONResult getMenu() {
        List<Menu> menu = orderRoleChildService.findMenu();
        return new JSONResult("0","ok",menu);
    }
    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(OrderRoleChildParam orderRoleChildParam) {
        this.orderRoleChildService.add(orderRoleChildParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(OrderRoleChildParam orderRoleChildParam) {
        this.orderRoleChildService.update(orderRoleChildParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(OrderRoleChildParam orderRoleChildParam) {
        this.orderRoleChildService.delete(orderRoleChildParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(OrderRoleChildParam orderRoleChildParam) {
        OrderRoleChild detail = this.orderRoleChildService.getById(orderRoleChildParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(OrderRoleParam orderRoleChildParam) {
        return this.orderRoleService.findPageBySpec(orderRoleChildParam,null);
    }

    /**
     * 获取角色列表
     * @return
     */
    @ResponseBody
    @RequestMapping("/roleList")
    public JSONResult getRoleList(){
        List<Role> role = this.orderRoleChildService.findRole();
        return new JSONResult("0","ok",role);
    }

}


