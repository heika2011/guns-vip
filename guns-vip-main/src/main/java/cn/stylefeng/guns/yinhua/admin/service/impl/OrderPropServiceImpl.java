package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderProp;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderPropMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderPropParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderPropResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderPropService;
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
public class OrderPropServiceImpl extends ServiceImpl<AdminOrderPropMapper, OrderProp> implements OrderPropService {

    @Override
    public void add(OrderPropParam param){
        OrderProp entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderPropParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderPropParam param){
        OrderProp oldEntity = getOldEntity(param);
        OrderProp newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderPropResult findBySpec(OrderPropParam param){
        return null;
    }

    @Override
    public List<OrderPropResult> findListBySpec(OrderPropParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderPropParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderPropParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderProp getOldEntity(OrderPropParam param) {
        return this.getById(getKey(param));
    }

    private OrderProp getEntity(OrderPropParam param) {
        OrderProp entity = new OrderProp();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
