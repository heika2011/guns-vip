package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelColor;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelColorParam;
import cn.stylefeng.guns.yinhua.admin.model.result.ModelColorResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
public interface ModelColorService extends IService<ModelColor> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void add(ModelColorParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void delete(ModelColorParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void update(ModelColorParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    ModelColorResult findBySpec(ModelColorParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<ModelColorResult> findListBySpec(ModelColorParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
     LayuiPageInfo findPageBySpec(ModelColorParam param);

}
