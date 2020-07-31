package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderFrom;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderFromMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderFromParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderFromResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderFromService;
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
public class OrderFromServiceImpl extends ServiceImpl<AdminOrderFromMapper, OrderFrom> implements OrderFromService {

    @Override
    public void add(OrderFromParam param){
        OrderFrom entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderFromParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderFromParam param){
        OrderFrom oldEntity = getOldEntity(param);
        OrderFrom newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderFromResult findBySpec(OrderFromParam param){
        return null;
    }

    @Override
    public List<OrderFromResult> findListBySpec(OrderFromParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderFromParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderFromParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderFrom getOldEntity(OrderFromParam param) {
        return this.getById(getKey(param));
    }

    private OrderFrom getEntity(OrderFromParam param) {
        OrderFrom entity = new OrderFrom();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
