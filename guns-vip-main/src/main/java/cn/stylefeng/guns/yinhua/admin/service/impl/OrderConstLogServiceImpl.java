package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderConstLog;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderConstLogMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderConstLogParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderConstLogResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderConstLogService;
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
public class OrderConstLogServiceImpl extends ServiceImpl<AdminOrderConstLogMapper, OrderConstLog> implements OrderConstLogService {

    @Override
    public void add(OrderConstLogParam param){
        OrderConstLog entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderConstLogParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderConstLogParam param){
        OrderConstLog oldEntity = getOldEntity(param);
        OrderConstLog newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderConstLogResult findBySpec(OrderConstLogParam param){
        return null;
    }

    @Override
    public List<OrderConstLogResult> findListBySpec(OrderConstLogParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderConstLogParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderConstLogParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderConstLog getOldEntity(OrderConstLogParam param) {
        return this.getById(getKey(param));
    }

    private OrderConstLog getEntity(OrderConstLogParam param) {
        OrderConstLog entity = new OrderConstLog();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
