package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelNote;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelNoteParam;
import cn.stylefeng.guns.yinhua.admin.model.result.ModelNoteResult;
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
public interface ModelNoteService extends IService<ModelNote> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void add(ModelNoteParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void delete(ModelNoteParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void update(ModelNoteParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    ModelNoteResult findBySpec(ModelNoteParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<ModelNoteResult> findListBySpec(ModelNoteParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
     LayuiPageInfo findPageBySpec(ModelNoteParam param);

}
