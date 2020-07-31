package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderProgress;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderProgressParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderProgressResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 进度表信息 服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-04-29
 */
public interface OrderProgressService extends IService<OrderProgress> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    void add(OrderProgressParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    void delete(OrderProgressParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    void update(OrderProgressParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    OrderProgressResult findBySpec(OrderProgressParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    List<OrderProgressResult> findListBySpec(OrderProgressParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-04-29
     */
     LayuiPageInfo findPageBySpec(OrderProgressParam param);

}
