package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.Orders;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrdersMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrdersParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrdersResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrdersService;
import cn.stylefeng.guns.yinhua.mobile.mapper.OrderMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 款式 服务实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<AdminOrdersMapper, Orders> implements OrdersService {

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public void add(OrdersParam param){
        Orders entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrdersParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrdersParam param){
        Orders oldEntity = getOldEntity(param);
        Orders newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrdersResult findBySpec(OrdersParam param){
        return null;
    }

    @Override
    public List<OrdersResult> findListBySpec(OrdersParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrdersParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Integer getOrderType(String orderNum) {
        Integer integer = orderMapper.selectTypeNumByOrderNum(orderNum);
        return integer;
    }

    private Serializable getKey(OrdersParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Orders getOldEntity(OrdersParam param) {
        return this.getById(getKey(param));
    }

    private Orders getEntity(OrdersParam param) {
        Orders entity = new Orders();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
