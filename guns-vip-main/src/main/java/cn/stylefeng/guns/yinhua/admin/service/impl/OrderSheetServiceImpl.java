package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderSheet;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderSheetMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderSheetParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderSheetResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderSheetService;
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
public class OrderSheetServiceImpl extends ServiceImpl<AdminOrderSheetMapper, OrderSheet> implements OrderSheetService {

    @Override
    public void add(OrderSheetParam param){
        OrderSheet entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderSheetParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderSheetParam param){
        OrderSheet oldEntity = getOldEntity(param);
        OrderSheet newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderSheetResult findBySpec(OrderSheetParam param){
        return null;
    }

    @Override
    public List<OrderSheetResult> findListBySpec(OrderSheetParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderSheetParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderSheetParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderSheet getOldEntity(OrderSheetParam param) {
        return this.getById(getKey(param));
    }

    private OrderSheet getEntity(OrderSheetParam param) {
        OrderSheet entity = new OrderSheet();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
