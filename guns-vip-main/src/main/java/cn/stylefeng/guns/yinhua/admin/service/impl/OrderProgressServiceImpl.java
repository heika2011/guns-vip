package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderProgress;
import cn.stylefeng.guns.yinhua.admin.mapper.OrderProgressMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderProgressParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderProgressResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderProgressService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 进度表信息 服务实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-04-29
 */
@Service
public class OrderProgressServiceImpl extends ServiceImpl<OrderProgressMapper, OrderProgress> implements OrderProgressService {

    @Override
    public void add(OrderProgressParam param){
        OrderProgress entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderProgressParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderProgressParam param){
        OrderProgress oldEntity = getOldEntity(param);
        OrderProgress newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderProgressResult findBySpec(OrderProgressParam param){
        return null;
    }

    @Override
    public List<OrderProgressResult> findListBySpec(OrderProgressParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderProgressParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderProgressParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderProgress getOldEntity(OrderProgressParam param) {
        return this.getById(getKey(param));
    }

    private OrderProgress getEntity(OrderProgressParam param) {
        OrderProgress entity = new OrderProgress();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
