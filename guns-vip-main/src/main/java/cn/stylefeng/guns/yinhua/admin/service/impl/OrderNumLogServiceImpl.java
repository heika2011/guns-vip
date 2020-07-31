package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderNumLog;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderNumLogMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderNumLogParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderNumLogResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderNumLogService;
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
public class OrderNumLogServiceImpl extends ServiceImpl<AdminOrderNumLogMapper, OrderNumLog> implements OrderNumLogService {

    @Override
    public void add(OrderNumLogParam param){
        OrderNumLog entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderNumLogParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderNumLogParam param){
        OrderNumLog oldEntity = getOldEntity(param);
        OrderNumLog newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderNumLogResult findBySpec(OrderNumLogParam param){
        return null;
    }

    @Override
    public List<OrderNumLogResult> findListBySpec(OrderNumLogParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderNumLogParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderNumLogParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderNumLog getOldEntity(OrderNumLogParam param) {
        return this.getById(getKey(param));
    }

    private OrderNumLog getEntity(OrderNumLogParam param) {
        OrderNumLog entity = new OrderNumLog();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
