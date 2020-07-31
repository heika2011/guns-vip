package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.third.entity.WeiXinParamesUtil;
import cn.stylefeng.guns.yinhua.admin.entity.Customer;
import cn.stylefeng.guns.yinhua.admin.entity.WeChatPeople;
import cn.stylefeng.guns.yinhua.admin.model.params.CustomerParam;
import cn.stylefeng.guns.yinhua.admin.service.AdminCustomerService;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestCustomer;
import cn.stylefeng.guns.yinhua.service.CustomerService;
import cn.stylefeng.guns.yinhua.service.SendMessageUtilService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import com.alibaba.fastjson.JSON;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 客户表控制器
 *
 * @author xiexin
 * @Date 2020-03-19 20:54:18
 */
@Controller
@RequestMapping("/customer")
public class AdminCustomerController extends BaseController {

    private String PREFIX = "/admin/customer";

    @Autowired
    private AdminCustomerService adminCustomerService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private SendMessageUtilService sendMessageUtilService;
    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/customer.html";
    }
    /**
     * 微信粉丝配置页面
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("/weChatPeople")
    public String weChatPeople(Integer type,Integer id,Model model) {
        List<WeChatPeople> weChatPeopleList= customerService.getUnAttention();
        Customer byId = adminCustomerService.getById(id);
        if(byId.getOpenId() != ""){
            model.addAttribute("customer",byId);
        }
        model.addAttribute("weUser",weChatPeopleList);
        model.addAttribute("id",id);
        return PREFIX + "/weChatPeople.html";
    }

    /**
     * 更新客户的微信信息
     * @param customers
     * @return
     */
    @RequestMapping("/updateWeChatUser")
    @ResponseBody
    public JSONResult updateWeChatUser(Customer customers){
        adminCustomerService.updateById(customers);
        String[] custo = customers.getOpenId().split(",");
        String[] name = customers.getwName().split(",,,,");
        for(int i=0;i<custo.length;i++){
            List<WxMpTemplateData> wxMpTemplateData = new ArrayList<>();
            wxMpTemplateData.add(new WxMpTemplateData("first","恭喜您，您的账号已于平台对接。"));
            wxMpTemplateData.add(new WxMpTemplateData("keyword1",name[i]));
            wxMpTemplateData.add(new WxMpTemplateData("keyword2",new Date().toLocaleString()));
            wxMpTemplateData.add(new WxMpTemplateData("keyword3","成功"));
            wxMpTemplateData.add(new WxMpTemplateData("remark","本消息为提醒消息"));
            sendMessageUtilService.sendMessageForWe(custo[i],"#", WeiXinParamesUtil.care,wxMpTemplateData);
        }
        return new JSONResult("0","OK");
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("/add")
    public String add(Model model) {
        String num = customerService.findNextNum();
        model.addAttribute("num",num);
        return PREFIX + "/customer_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/customer_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(CustomerParam customerParam) {
        this.adminCustomerService.add(customerParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(CustomerParam customerParam) {
        this.adminCustomerService.update(customerParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("/delete")
    @ResponseBody
    @Permission
    public ResponseData delete(CustomerParam customerParam) {
        this.adminCustomerService.delete(customerParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(CustomerParam customerParam) {
        Customer detail = this.adminCustomerService.getById(customerParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(CustomerParam customerParam) {
        return this.adminCustomerService.findPageBySpec(customerParam);
    }
    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @ResponseBody
    @RequestMapping("/getCustomer")
    public JSONResult getCustomer() {
        List<RestCustomer> customerList = customerService.getCustomerList();
        return new JSONResult("0","OK",customerList);
    }
    /**
     * 获取所有公众号列表
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    @RequestMapping("/getGZHUser")
    @ResponseBody
    public JSONResult getGZHUser() throws Exception {
        adminCustomerService.getGZHUser();
        return new JSONResult("0","OK");
    }
}


