package cn.stylefeng.guns.yinhua.mobile.controller.customer;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.Customer;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestCustomer;
import cn.stylefeng.guns.yinhua.service.CustomerService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 客户类
 * 跟单员权限
 */
@RestController
@RequestMapping("/mobile/customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    /**
     * 分页获取用户列表
     * @return
     */
    @Permission
    @RequestMapping("/getCustomer")
    @ApiOperation(value="获取客户列表",notes="token,初始页Page,条数pageSize,客户重要级别，搜索关键字", httpMethod = "GET")
    public PageResult<Customer> getCustomerByList(String token,Long page, Long pageSize,Integer level,String name){
        String s = LoginContextHolder.getContext().getUserId().toString();
        Page<Customer> allCustomer = customerService.getAllCustomer(page, pageSize,level,name,s);
        return new PageResult<Customer>(page,pageSize,allCustomer.getTotal(),allCustomer.getRecords().size(),allCustomer.getRecords());
    }
    /**
     * 获取客户信息列表
     * @return
     */
    @Permission
    @RequestMapping("/getCustomerList")
    @ApiOperation(value="获取客户信息列表",notes="token", httpMethod = "GET")
    public JSONResult getCustomerList(String token){
        List<RestCustomer> restCustomerList= customerService.getCustomerList();
        return new JSONResult("0","OK",restCustomerList);
    }

    /**
     * 修改跟单
     * @return
     */
    @RequestMapping("/changeCustomerCreate")
    @ApiOperation(value="修改跟单",notes="token", httpMethod = "GET")
    public JSONResult changeCustomerCreate(String token,String num,String userId){
        customerService.changeCustomerCreate(num,userId);
        return new JSONResult("0","OK");
    }
    /**
     * 获取单个客户信息
     * @return
     */
    @Permission
    @RequestMapping("/getCustomerById")
    @ApiOperation(value="获取单个客户信息",notes="token,客户id", httpMethod = "GET")
    public JSONResult getCustomerById(String token,Integer id){
        Customer customerById = customerService.getCustomerById(id);
        return new JSONResult("0","OK",customerById);
    }
    /**
     * 获取最新客户编号
     * @return
     */
    @Permission
    @RequestMapping("/getCustomerNum")
    @ApiOperation(value="获取最新客户编号",notes="token,客户信息", httpMethod = "GET")
    public JSONResult getCustomerNum(String token){
        String nextNum = customerService.findNextNum();
        return new JSONResult("0","OK",nextNum);
    }

    /**
     * 添加客户
     * @return
     */
    @Permission
    @RequestMapping("/addCustomer")
    @ApiOperation(value="添加客户",notes="token,客户信息", httpMethod = "GET")
    public JSONResult addCustomer(String token,Customer customer){
        customerService.addCustomer(customer);
        return new JSONResult("0","OK");
    }
    /**
     * 修改客户信息
     * @return
     */
    @Permission
    @RequestMapping("/updateCustomer")
    @ApiOperation(value="修改客户",notes="token,客户信息", httpMethod = "GET")
    public JSONResult updateCustomer(String token,Customer customer){
        customerService.updateCustomer(customer);
        return new JSONResult("0","OK");
    }

    /**
     * 删除客户
     * @return
     */
    @Permission
    @RequestMapping("/deleteCustomer")
    @ApiOperation(value="删除客户",notes="token,客户编号", httpMethod = "GET")
    public JSONResult deleteCustomer(String token,String id){
        customerService.deleteCustomer(id);
        return new JSONResult("0","ok");
    }
}
