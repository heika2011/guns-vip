package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.PrintAre;
import cn.stylefeng.guns.yinhua.admin.mapper.PrintAreMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.PrintAreParam;
import cn.stylefeng.guns.yinhua.admin.model.result.PrintAreResult;
import  cn.stylefeng.guns.yinhua.admin.service.PrintAreService;
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
 * @since 2020-05-23
 */
@Service
public class PrintAreServiceImpl extends ServiceImpl<PrintAreMapper, PrintAre> implements PrintAreService {

    @Override
    public void add(PrintAreParam param){
        PrintAre entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(PrintAreParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(PrintAreParam param){
        PrintAre oldEntity = getOldEntity(param);
        PrintAre newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public PrintAreResult findBySpec(PrintAreParam param){
        return null;
    }

    @Override
    public List<PrintAreResult> findListBySpec(PrintAreParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(PrintAreParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(PrintAreParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private PrintAre getOldEntity(PrintAreParam param) {
        return this.getById(getKey(param));
    }

    private PrintAre getEntity(PrintAreParam param) {
        PrintAre entity = new PrintAre();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
