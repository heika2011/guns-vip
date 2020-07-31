package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderPropNote;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderPropNoteMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderPropNoteParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderPropNoteResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderPropNoteService;
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
public class OrderPropNoteServiceImpl extends ServiceImpl<AdminOrderPropNoteMapper, OrderPropNote> implements OrderPropNoteService {

    @Override
    public void add(OrderPropNoteParam param){
        OrderPropNote entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderPropNoteParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderPropNoteParam param){
        OrderPropNote oldEntity = getOldEntity(param);
        OrderPropNote newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderPropNoteResult findBySpec(OrderPropNoteParam param){
        return null;
    }

    @Override
    public List<OrderPropNoteResult> findListBySpec(OrderPropNoteParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderPropNoteParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderPropNoteParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderPropNote getOldEntity(OrderPropNoteParam param) {
        return this.getById(getKey(param));
    }

    private OrderPropNote getEntity(OrderPropNoteParam param) {
        OrderPropNote entity = new OrderPropNote();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
