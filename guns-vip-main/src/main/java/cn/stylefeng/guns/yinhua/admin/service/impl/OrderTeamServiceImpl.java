package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderTeam;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminOrderTeamMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderTeamParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderTeamResult;
import  cn.stylefeng.guns.yinhua.admin.service.OrderTeamService;
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
public class OrderTeamServiceImpl extends ServiceImpl<AdminOrderTeamMapper, OrderTeam> implements OrderTeamService {

    @Override
    public void add(OrderTeamParam param){
        OrderTeam entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(OrderTeamParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(OrderTeamParam param){
        OrderTeam oldEntity = getOldEntity(param);
        OrderTeam newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public OrderTeamResult findBySpec(OrderTeamParam param){
        return null;
    }

    @Override
    public List<OrderTeamResult> findListBySpec(OrderTeamParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(OrderTeamParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(OrderTeamParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private OrderTeam getOldEntity(OrderTeamParam param) {
        return this.getById(getKey(param));
    }

    private OrderTeam getEntity(OrderTeamParam param) {
        OrderTeam entity = new OrderTeam();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
