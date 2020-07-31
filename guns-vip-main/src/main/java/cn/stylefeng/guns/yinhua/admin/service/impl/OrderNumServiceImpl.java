package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderNum;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderNumMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderNumParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderNumResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderNumService;
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
public class OrderNumServiceImpl extends ServiceImpl<AdminOrderNumMapper, OrderNum> implements OrderNumService {

    @Override
    public void add(OrderNumParam param){
        OrderNum entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderNumParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderNumParam param){
        OrderNum oldEntity = getOldEntity(param);
        OrderNum newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderNumResult findBySpec(OrderNumParam param){
        return null;
    }

    @Override
    public List<OrderNumResult> findListBySpec(OrderNumParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderNumParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderNumParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderNum getOldEntity(OrderNumParam param) {
        return this.getById(getKey(param));
    }

    private OrderNum getEntity(OrderNumParam param) {
        OrderNum entity = new OrderNum();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
