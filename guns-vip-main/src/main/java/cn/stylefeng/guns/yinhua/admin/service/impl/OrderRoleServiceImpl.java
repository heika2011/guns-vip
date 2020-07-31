package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.yinhua.admin.entity.OrderRole;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderRoleMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderRoleParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderRoleResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderRoleService;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderRoleChild;
import cn.stylefeng.guns.yinhua.mobile.mapper.OrderRoleChildMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class OrderRoleServiceImpl extends ServiceImpl<AdminOrderRoleMapper, OrderRole> implements OrderRoleService {

    @Autowired
    private OrderRoleChildMapper orderRoleChildMapper;

    @Override
    @Transactional
    public int add(OrderRole orderRole){
        if(orderRole.getType() == null){
            orderRole.setIsname(1);
        }else {
            orderRole.setIsname(0);
        }
        if(this.baseMapper.selectList(new QueryWrapper<OrderRole>().eq("name",orderRole.getName()).eq("type",orderRole.getType())).size() == 1){
            return 0;
        }
        if(orderRole.getId() == null){
            this.baseMapper.insert(orderRole);
            return orderRole.getId();
        }
        this.baseMapper.insertData(orderRole.getId(),orderRole.getName(),orderRole.getIsname());
        return orderRole.getId();
    }

    @Override
    public void delete(OrderRoleParam param){
        orderRoleChildMapper.delete(new QueryWrapper<OrderRoleChild>().eq("parent_id",param.getId()));
        this.baseMapper.delete(new QueryWrapper<OrderRole>().eq("id",param.getId()));
    }

    @Override
    @Transactional
    public void update(OrderRoleParam param){
        OrderRole oldEntity = getOldEntity(param);
        if(!param.getType().equals(oldEntity.getType())){
            if(this.baseMapper.selectList(new QueryWrapper<OrderRole>().eq("name",oldEntity.getName()).eq("type",oldEntity.getType())).size() == 1){
                throw new OutExcetion("该进度已配置");
            }
        }
        this.baseMapper.updateAll(oldEntity.getName(),param);
    }

    @Override
    public OrderRoleResult findBySpec(OrderRoleParam param){
        return null;
    }

    @Override
    public List<OrderRoleResult> findListBySpec(OrderRoleParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderRoleParam param,Integer flag){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param,flag);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderRoleParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderRole getOldEntity(OrderRoleParam param) {
        return this.getById(getKey(param));
    }

    private OrderRole getEntity(OrderRoleParam param) {
        OrderRole entity = new OrderRole();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
