package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderUser;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderUserMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderUserParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderUserResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderUserService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@Service
public class OrderUserServiceImpl extends ServiceImpl<AdminOrderUserMapper, OrderUser> implements OrderUserService {

    @Override
    public void add(OrderUserParam param){
        OrderUser entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderUserParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderUserParam param){
        OrderUser oldEntity = getOldEntity(param);
        OrderUser newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderUserResult findBySpec(OrderUserParam param){
        return null;
    }

    @Override
    public List<OrderUserResult> findListBySpec(OrderUserParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderUserParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderUserParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderUser getOldEntity(OrderUserParam param) {
        return this.getById(getKey(param));
    }

    private OrderUser getEntity(OrderUserParam param) {
        OrderUser entity = new OrderUser();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
