package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.ModelInfo;
import cn.stylefeng.guns.yinhua.admin.model.params.ModelInfoParam;
import cn.stylefeng.guns.yinhua.admin.model.result.ModelInfoResult;
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
public interface ModelInfoService extends IService<ModelInfo> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void add(ModelInfoParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void delete(ModelInfoParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void update(ModelInfoParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    ModelInfoResult findBySpec(ModelInfoParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<ModelInfoResult> findListBySpec(ModelInfoParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
     LayuiPageInfo findPageBySpec(ModelInfoParam param);

}
