package cn.stylefeng.guns.yinhua.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.sys.modular.third.service.HttpHelper;
import cn.stylefeng.guns.yinhua.admin.entity.WeChatPeople;
import cn.stylefeng.guns.yinhua.entity.yinhua.Customer;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestCustomer;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.yinhua.mobile.mapper.CustomerMapper;
import cn.stylefeng.guns.yinhua.service.CustomerService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 客户业务类
 */
@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private WxMpService wxMpService;

    @Override
    public String findNextNum() {
        Long aLong = customerMapper.selectNextNum();
        String str = "QC20" + String.format("%04d", aLong);//字符串格式化.
        return str;
    }

    @Override
    public List<WeChatPeople> getUnAttention() {
        JSONObject jsonObject = null;
        try {
            jsonObject = HttpHelper.doGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + wxMpService.getAccessToken() + "&next_openid=");
        } catch (Exception e) {
            e.printStackTrace();
        }
        JSONObject data = (JSONObject) jsonObject.get("data");
        List<Map<String, String>> s = new ArrayList<>();
        for (Object d : data.getJSONArray("openid")) {
            Map m = new HashMap();
            m.put("openid", (String) d);
            s.add(m);
        }
        Map to = new HashMap();
        to.put("user_list", s);
        List<WeChatPeople> weChatPeoples = new ArrayList<>();
        try {
            JSONObject user = HttpHelper.doPost("https://api.weixin.qq.com/cgi-bin/user/info/batchget?access_token=" + wxMpService.getAccessToken(), to);
            for (Object json : user.getJSONArray("user_info_list")) {
                json = (JSONObject) json;
                WeChatPeople w = new WeChatPeople();
                w.setOpenId(((JSONObject) json).getString("openid"));
                w.setImageUrl(((JSONObject) json).getString("headimgurl"));
                w.setNickName(((JSONObject) json).getString("nickname"));
                weChatPeoples.add(w);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weChatPeoples;
    }

    /**
     * 修改跟单
     * @param num
     * @param userId
     */
    @Override
    public void changeCustomerCreate(String num, String userId) {
        Customer num1 = customerMapper.selectOne(new QueryWrapper<Customer>().eq("num", num));
        if(null!=num1.getCreatedUser()){
            if(!num1.getCreatedUser().equals(LoginContextHolder.getContext().getUserId())){
                throw new OutExcetion("该客户不是您负责");
            }
        }
        customerMapper.changeCustomerCreate(num,userId);
    }

    /**
     * 获取所有客户
     *
     * @return
     */
    @Override
    public Page<Customer> getAllCustomer(Long page, Long pageSize, Integer level, String name,String userId) {
        if (StringUtils.isEmpty(page) || StringUtils.isEmpty(pageSize)) {
            throw new OutExcetion("分页参数不能为空");
        }
        QueryWrapper<Customer> customerQueryWrapper = new QueryWrapper<>();
        customerQueryWrapper.eq("created_user",userId).or().isNull("created_user");
        if (level != null && level != 0) {
            customerQueryWrapper.eq("level", level);
        }
        if (name != null && name != "") {
            customerQueryWrapper = new QueryWrapper<>();
            customerQueryWrapper.like("name", name)
                    .or().like("num", name)
                    .or().like("contact", name)
                    .or().like("phone", name);
        }else {
            customerQueryWrapper.eq("created_user",userId).or().isNull("created_user");
        }
        Page<Customer> customerPage = customerMapper.selectPage(new Page<Customer>(page, pageSize), customerQueryWrapper);
        return customerPage;
    }

    /**
     * 添加客户
     *
     * @param customer
     */
    @Override
    @Transactional
    public void addCustomer(Customer customer) {
        if (StringUtils.isEmpty(customer)) {
            throw new OutExcetion("参数不能为空");
        }
        if (StringUtils.isEmpty(customer.getNum())) {
            throw new OutExcetion("客户编号不能为空");
        }
        customer.setCreatedTime(new Date());
        customer.setCreatedUser(LoginContextHolder.getContext().getUserId());
        Customer old = customerMapper.selectOne(new QueryWrapper<Customer>().eq("num", customer.getNum()).or().eq("name", customer.getName()));
        if(old != null){
            throw new OutExcetion("客户编号或者名字已存在!");
        }
        customerMapper.insert(customer);
    }

    /**
     * 删除客户
     *
     * @param num
     */
    @Override
    public void deleteCustomer(String num) {
        QueryWrapper<Customer> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("id", num);
        int delete = customerMapper.delete(queryWrapper);
        if (delete == 0) {
            throw new OutExcetion("不存在该客户");
        }
    }

    /**
     * 获取单个客户详情
     *
     * @param id
     * @return
     */
    @Override
    public Customer getCustomerById(Integer id) {
        Customer customer = customerMapper.selectById(id);
        if (customer == null) {
            throw new OutExcetion("该客户不存在");
        }
        return customer;
    }

    /**
     * 修改单个客户
     *
     * @param customer
     */
    @Override
    public void updateCustomer(Customer customer) {
        customer.setUpdateTime(new Date());
        int i = customerMapper.updateById(customer);
        if (i == 0) {
            throw new OutExcetion("修改失败");
        }
    }

    /**
     * 获取客户列表
     *
     * @return
     */
    @Override
    public List<RestCustomer> getCustomerList() {
        List<Customer> customers = customerMapper.selectList(null);
        List<RestCustomer> restCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            RestCustomer restCustomer = new RestCustomer();
            restCustomer.setName(customer.getName());
            restCustomer.setNum(customer.getNum());
            restCustomers.add(restCustomer);
        }
        return restCustomers;
    }
}

