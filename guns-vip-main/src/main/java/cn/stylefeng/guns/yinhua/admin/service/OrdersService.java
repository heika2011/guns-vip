package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.Orders;
import cn.stylefeng.guns.yinhua.admin.model.params.OrdersParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrdersResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 款式 服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
public interface OrdersService extends IService<Orders> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void add(OrdersParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void delete(OrdersParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void update(OrdersParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    OrdersResult findBySpec(OrdersParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<OrdersResult> findListBySpec(OrdersParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
     LayuiPageInfo findPageBySpec(OrdersParam param);

     Integer getOrderType(String orderNum);
}
