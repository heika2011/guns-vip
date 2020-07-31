package cn.stylefeng.guns.sys.modular.system.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysMessage;
import cn.stylefeng.guns.sys.modular.system.mapper.SysMessageMapper;
import cn.stylefeng.guns.sys.modular.system.model.params.SysMessageParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SysMessageResult;
import  cn.stylefeng.guns.sys.modular.system.service.SysMessageService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-23
 */
@Service
public class SysMessageServiceImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements SysMessageService {

    @Override
    public void add(SysMessageParam param){
        if(this.baseMapper.selectOne(new QueryWrapper<SysMessage>().eq("type",param.getType()))!=null){
            throw new OutExcetion("不允许重复添加类型");
        }
        param.setCreatedTime(new Date());
        if(param.getToName() == null || param.getToName() == ""){
            param.setToName("0");
        }
        if(param.getToJob() == null || param.getToJob() == ""){
            param.setToJob("0");
        }
        SysMessage entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(SysMessageParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(SysMessageParam param){
        if(param.getToName() == null || param.getToName() == ""){
            param.setToName("0");
        }
        if(param.getToJob() == null || param.getToJob() == ""){
            param.setToJob("0");
        }
        SysMessage oldEntity = getOldEntity(param);
        SysMessage newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SysMessageResult findBySpec(SysMessageParam param){
        return null;
    }

    @Override
    public List<SysMessageResult> findListBySpec(SysMessageParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(SysMessageParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(SysMessageParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private SysMessage getOldEntity(SysMessageParam param) {
        return this.getById(getKey(param));
    }

    private SysMessage getEntity(SysMessageParam param) {
        SysMessage entity = new SysMessage();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
