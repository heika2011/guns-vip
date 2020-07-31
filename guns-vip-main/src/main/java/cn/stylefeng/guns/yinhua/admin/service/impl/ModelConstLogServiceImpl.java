package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelConstLog;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminModelConstLogMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelConstLogParam;
import cn.stylefeng.guns.yinhua.admin.model.result.ModelConstLogResult;
import  cn.stylefeng.guns.yinhua.admin.service.ModelConstLogService;
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
public class ModelConstLogServiceImpl extends ServiceImpl<AdminModelConstLogMapper, ModelConstLog> implements ModelConstLogService {

    @Override
    public void add(ModelConstLogParam param){
        ModelConstLog entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ModelConstLogParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ModelConstLogParam param){
        ModelConstLog oldEntity = getOldEntity(param);
        ModelConstLog newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ModelConstLogResult findBySpec(ModelConstLogParam param){
        return null;
    }

    @Override
    public List<ModelConstLogResult> findListBySpec(ModelConstLogParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ModelConstLogParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ModelConstLogParam param){
        return null;
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ModelConstLog getOldEntity(ModelConstLogParam param) {
        return this.getById(getKey(param));
    }

    private ModelConstLog getEntity(ModelConstLogParam param) {
        ModelConstLog entity = new ModelConstLog();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
