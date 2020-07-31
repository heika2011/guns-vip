package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelColor;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminModelColorMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelColorParam;
import cn.stylefeng.guns.yinhua.admin.model.result.ModelColorResult;
import  cn.stylefeng.guns.yinhua.admin.service.ModelColorService;
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
public class ModelColorServiceImpl extends ServiceImpl<AdminModelColorMapper, ModelColor> implements ModelColorService {

    @Override
    public void add(ModelColorParam param){
        ModelColor entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ModelColorParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ModelColorParam param){
        ModelColor oldEntity = getOldEntity(param);
        ModelColor newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ModelColorResult findBySpec(ModelColorParam param){
        return null;
    }

    @Override
    public List<ModelColorResult> findListBySpec(ModelColorParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ModelColorParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ModelColorParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ModelColor getOldEntity(ModelColorParam param) {
        return this.getById(getKey(param));
    }

    private ModelColor getEntity(ModelColorParam param) {
        ModelColor entity = new ModelColor();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
