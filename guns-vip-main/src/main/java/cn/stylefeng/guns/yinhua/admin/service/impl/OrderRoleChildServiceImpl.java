package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.model.entity.Menu;
import cn.stylefeng.guns.sys.modular.system.model.entity.Role;
import cn.stylefeng.guns.sys.modular.system.mapper.MenuMapper;
import cn.stylefeng.guns.sys.modular.system.mapper.RoleMapper;
import cn.stylefeng.guns.yinhua.admin.entity.OrderRoleChild;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderRoleChildMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderRoleChildParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderRoleChildResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderRoleChildService;
import cn.stylefeng.guns.yinhua.mobile.mapper.OrderRoleMapper;
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
 * @since 2020-03-16
 */
@Service
public class OrderRoleChildServiceImpl extends ServiceImpl<AdminOrderRoleChildMapper, OrderRoleChild> implements OrderRoleChildService {

    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private OrderRoleMapper orderRoleMapper;
    @Override
    public void add(OrderRoleChildParam param){
        OrderRoleChild entity = getEntity(param);
        this.save(entity);
    }

    @Override
    @Transactional
    public void delete(OrderRoleChildParam param){
        this.baseMapper.delete(new QueryWrapper<OrderRoleChild>().eq("parent_id",param.getId()));
        orderRoleMapper.deleteById(param.getId());
    }

    @Override
    public void update(OrderRoleChildParam param){
        OrderRoleChild oldEntity = getOldEntity(param);
        OrderRoleChild newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderRoleChildResult findBySpec(OrderRoleChildParam param){
        return null;
    }

    @Override
    public List<OrderRoleChildResult> findListBySpec(OrderRoleChildParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderRoleChildParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public List<Role> findRole() {
        List<Role> pid = roleMapper.selectList(new QueryWrapper<Role>().eq("pid", "1234732820551712770"));
        return pid;
    }

    /**
     * 菜单获取
     * @return
     */
    @Override
    public List<Menu> findMenu() {
        List<Menu> menus = menuMapper.selectList(new QueryWrapper<Menu>().like("code", "type"));
        return menus;
    }

    /**
     * 修改
     * @param orderRoleChild
     */
    @Override
    @Transactional
    public void updateRoleChild(List<OrderRoleChild> orderRoleChild) {
        this.baseMapper.delete(new QueryWrapper<OrderRoleChild>().eq("parent_id",orderRoleChild.get(0).getParentId()));
        for(OrderRoleChild orderRoleChild1:orderRoleChild){
            this.baseMapper.insert(orderRoleChild1);
        }
    }

    /**
     * 删除进度
     * @param id
     */
    @Override
    public void deleteOrderRoleChild(Integer id) {
        this.baseMapper.delete(new QueryWrapper<OrderRoleChild>().eq("parent_id",id));
        orderRoleMapper.deleteById(id);
    }

    private Serializable getKey(OrderRoleChildParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderRoleChild getOldEntity(OrderRoleChildParam param) {
        return this.getById(getKey(param));
    }

    private OrderRoleChild getEntity(OrderRoleChildParam param) {
        OrderRoleChild entity = new OrderRoleChild();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
