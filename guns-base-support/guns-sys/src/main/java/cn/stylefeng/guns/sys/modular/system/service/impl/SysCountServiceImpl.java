package cn.stylefeng.guns.sys.modular.system.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.mapper.SysCountMapper;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysCount;
import cn.stylefeng.guns.sys.modular.system.model.params.SysCountParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SysCountResult;
import cn.stylefeng.guns.sys.modular.system.service.SysCountService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-24
 */
@Service
public class SysCountServiceImpl extends ServiceImpl<SysCountMapper, SysCount> implements SysCountService {

    @Override
    public void add(SysCountParam param){
        SysCount entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysCountParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysCountParam param){
        SysCount oldEntity = getOldEntity(param);
        SysCount newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysCountResult findBySpec(SysCountParam param){
        return null;
    }

    @Override
    public List<SysCountResult> findListBySpec(SysCountParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(SysCountParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    @Transactional
    public void updateByType(Map param) {
        for(int i=1;i<11;i++){
            String o =(String) param.get("value" + (i - 1));
            this.baseMapper.updateValueByType(Integer.valueOf(o),i);
        }
    }

    private Serializable getKey(SysCountParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private SysCount getOldEntity(SysCountParam param) {
        return this.getById(getKey(param));
    }

    private SysCount getEntity(SysCountParam param) {
        SysCount entity = new SysCount();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
