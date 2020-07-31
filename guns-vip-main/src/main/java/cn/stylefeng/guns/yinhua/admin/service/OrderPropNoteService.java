package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderPropNote;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderPropNoteParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderPropNoteResult;
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
public interface OrderPropNoteService extends IService<OrderPropNote> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void add(OrderPropNoteParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void delete(OrderPropNoteParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void update(OrderPropNoteParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    OrderPropNoteResult findBySpec(OrderPropNoteParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<OrderPropNoteResult> findListBySpec(OrderPropNoteParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
     LayuiPageInfo findPageBySpec(OrderPropNoteParam param);

}
