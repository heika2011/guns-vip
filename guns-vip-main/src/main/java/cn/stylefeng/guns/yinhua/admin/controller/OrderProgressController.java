package cn.stylefeng.guns.yinhua.admin.controller;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderProgress;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderProgressParam;
import cn.stylefeng.guns.yinhua.admin.service.OrderProgressService;
import cn.stylefeng.roses.core.base.controller.BaseController;
import cn.stylefeng.roses.kernel.model.response.ResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * 进度表信息控制器
 *
 * @author xiexin
 * @Date 2020-04-29 11:09:07
 */
@Controller
@RequestMapping("/orderProgress")
public class OrderProgressController extends BaseController {

    private String PREFIX = "/admin/orderProgress";

    @Autowired
    private OrderProgressService orderProgressService;

    /**
     * 跳转到主页面
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    @RequestMapping("")
    public String index() {
        return PREFIX + "/orderProgress.html";
    }

    /**
     * 新增页面
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    @RequestMapping("/add")
    public String add() {
        return PREFIX + "/orderProgress_add.html";
    }

    /**
     * 编辑页面
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    @RequestMapping("/edit")
    public String edit() {
        return PREFIX + "/orderProgress_edit.html";
    }

    /**
     * 新增接口
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    @RequestMapping("/addItem")
    @ResponseBody
    public ResponseData addItem(OrderProgressParam orderProgressParam) {
        this.orderProgressService.add(orderProgressParam);
        return ResponseData.success();
    }

    /**
     * 编辑接口
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    @RequestMapping("/editItem")
    @ResponseBody
    public ResponseData editItem(OrderProgressParam orderProgressParam) {
        this.orderProgressService.update(orderProgressParam);
        return ResponseData.success();
    }

    /**
     * 删除接口
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    @RequestMapping("/delete")
    @ResponseBody
    public ResponseData delete(OrderProgressParam orderProgressParam) {
        this.orderProgressService.delete(orderProgressParam);
        return ResponseData.success();
    }

    /**
     * 查看详情接口
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    @RequestMapping("/detail")
    @ResponseBody
    public ResponseData detail(OrderProgressParam orderProgressParam) {
        OrderProgress detail = this.orderProgressService.getById(orderProgressParam.getId());
        return ResponseData.success(detail);
    }

    /**
     * 查询列表
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    @ResponseBody
    @RequestMapping("/list")
    public LayuiPageInfo list(OrderProgressParam orderProgressParam) {
        return this.orderProgressService.findPageBySpec(orderProgressParam);
    }

}


