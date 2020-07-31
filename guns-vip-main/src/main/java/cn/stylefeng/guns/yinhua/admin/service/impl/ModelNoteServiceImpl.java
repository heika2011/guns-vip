package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelNote;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminModelNoteMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelNoteParam;
import cn.stylefeng.guns.yinhua.admin.model.result.ModelNoteResult;
import  cn.stylefeng.guns.yinhua.admin.service.ModelNoteService;
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
public class ModelNoteServiceImpl extends ServiceImpl<AdminModelNoteMapper, ModelNote> implements ModelNoteService {

    @Override
    public void add(ModelNoteParam param){
        ModelNote entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ModelNoteParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ModelNoteParam param){
        ModelNote oldEntity = getOldEntity(param);
        ModelNote newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ModelNoteResult findBySpec(ModelNoteParam param){
        return null;
    }

    @Override
    public List<ModelNoteResult> findListBySpec(ModelNoteParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ModelNoteParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ModelNoteParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ModelNote getOldEntity(ModelNoteParam param) {
        return this.getById(getKey(param));
    }

    private ModelNote getEntity(ModelNoteParam param) {
        ModelNote entity = new ModelNote();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
