package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelImage;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminModelImageMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelImageParam;
import cn.stylefeng.guns.yinhua.admin.model.result.ModelImageResult;
import  cn.stylefeng.guns.yinhua.admin.service.ModelImageService;
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
public class ModelImageServiceImpl extends ServiceImpl<AdminModelImageMapper, ModelImage> implements ModelImageService {

    @Override
    public void add(ModelImageParam param){
        ModelImage entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ModelImageParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ModelImageParam param){
        ModelImage oldEntity = getOldEntity(param);
        ModelImage newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ModelImageResult findBySpec(ModelImageParam param){
        return null;
    }

    @Override
    public List<ModelImageResult> findListBySpec(ModelImageParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ModelImageParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ModelImageParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ModelImage getOldEntity(ModelImageParam param) {
        return this.getById(getKey(param));
    }

    private ModelImage getEntity(ModelImageParam param) {
        ModelImage entity = new ModelImage();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
