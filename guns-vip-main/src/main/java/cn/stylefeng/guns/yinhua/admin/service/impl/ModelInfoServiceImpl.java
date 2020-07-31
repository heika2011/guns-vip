package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelInfo;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminModelInfoMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelInfoParam;
import cn.stylefeng.guns.yinhua.admin.model.result.ModelInfoResult;
import  cn.stylefeng.guns.yinhua.admin.service.ModelInfoService;
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
public class ModelInfoServiceImpl extends ServiceImpl<AdminModelInfoMapper, ModelInfo> implements ModelInfoService {

    @Override
    public void add(ModelInfoParam param){
        ModelInfo entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ModelInfoParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(ModelInfoParam param){
        ModelInfo oldEntity = getOldEntity(param);
        ModelInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ModelInfoResult findBySpec(ModelInfoParam param){
        return null;
    }

    @Override
    public List<ModelInfoResult> findListBySpec(ModelInfoParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(ModelInfoParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(ModelInfoParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ModelInfo getOldEntity(ModelInfoParam param) {
        return this.getById(getKey(param));
    }

    private ModelInfo getEntity(ModelInfoParam param) {
        ModelInfo entity = new ModelInfo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
