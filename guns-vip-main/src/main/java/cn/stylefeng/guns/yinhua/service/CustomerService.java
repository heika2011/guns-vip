package cn.stylefeng.guns.yinhua.service;

import cn.stylefeng.guns.yinhua.admin.entity.WeChatPeople;
import cn.stylefeng.guns.yinhua.entity.yinhua.Customer;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestCustomer;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;

import java.util.List;


public interface CustomerService {
    Page<Customer> getAllCustomer(Long page, Long pageSize,Integer level,String name,String userId);
    void addCustomer(Customer customer);
    void deleteCustomer(String num);
    Customer getCustomerById(Integer id);
    void updateCustomer(Customer customer);
    List<RestCustomer> getCustomerList();
    String findNextNum();
    List<WeChatPeople> getUnAttention();

    void changeCustomerCreate(String num, String userId);
}
