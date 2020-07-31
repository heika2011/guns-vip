package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.third.service.HttpHelper;
import cn.stylefeng.guns.yinhua.admin.entity.Customer;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminCustomerMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.CustomerParam;
import cn.stylefeng.guns.yinhua.admin.model.result.CustomerResult;
import cn.stylefeng.guns.yinhua.admin.service.AdminCustomerService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-19
 */
@Service
public class AdminCustomerServiceImpl extends ServiceImpl<AdminCustomerMapper, Customer> implements AdminCustomerService {

    @Autowired
    private WxMpService wxMpService;

    @Override
    public void add(CustomerParam param){
        param.setCreatedTime(new Date());
        param.setCreatedUser(LoginContextHolder.getContext().getUserId());
        Customer entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(CustomerParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(CustomerParam param){
        Customer oldEntity = getOldEntity(param);
        Customer newEntity = getEntity(param);
        newEntity.setUpdateTime(new Date());
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CustomerResult findBySpec(CustomerParam param){
        return null;
    }

    @Override
    public List<CustomerResult> findListBySpec(CustomerParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(CustomerParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public void getGZHUser() throws Exception {
        JSONObject jsonObject = HttpHelper.doGet("https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + wxMpService.getAccessToken() + "&next_openid=");
        JSONObject data = (JSONObject)jsonObject.get("data");
        System.out.println(data.getJSONArray("openid"));

    }

    private Serializable getKey(CustomerParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Customer getOldEntity(CustomerParam param) {
        return this.getById(getKey(param));
    }

    private Customer getEntity(CustomerParam param) {
        Customer entity = new Customer();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
