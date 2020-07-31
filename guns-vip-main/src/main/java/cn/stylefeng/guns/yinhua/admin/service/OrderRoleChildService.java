package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.model.entity.Menu;
import cn.stylefeng.guns.sys.modular.system.model.entity.Role;
import cn.stylefeng.guns.yinhua.admin.entity.OrderRoleChild;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderRoleChildParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderRoleChildResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-16
 */
public interface OrderRoleChildService extends IService<OrderRoleChild> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    void add(OrderRoleChildParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    void delete(OrderRoleChildParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    void update(OrderRoleChildParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    OrderRoleChildResult findBySpec(OrderRoleChildParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    List<OrderRoleChildResult> findListBySpec(OrderRoleChildParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-16
     */
     LayuiPageInfo findPageBySpec(OrderRoleChildParam param);

     List<Role> findRole();

     List<Menu> findMenu();

    void updateRoleChild(List<OrderRoleChild> orderRoleChild);
    void deleteOrderRoleChild(Integer id);
}
