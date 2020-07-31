package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.OrderRole;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderRoleParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderRoleResult;
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
public interface OrderRoleService extends IService<OrderRole> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    int add(OrderRole param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void delete(OrderRoleParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void update(OrderRoleParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    OrderRoleResult findBySpec(OrderRoleParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<OrderRoleResult> findListBySpec(OrderRoleParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
     LayuiPageInfo findPageBySpec(OrderRoleParam param,Integer flag);

}
