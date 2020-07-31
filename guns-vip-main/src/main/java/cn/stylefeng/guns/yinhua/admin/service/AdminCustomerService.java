package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.Customer;
import cn.stylefeng.guns.yinhua.admin.model.params.CustomerParam;
import cn.stylefeng.guns.yinhua.admin.model.result.CustomerResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-19
 */
public interface AdminCustomerService extends IService<Customer> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    void add(CustomerParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    void delete(CustomerParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    void update(CustomerParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    CustomerResult findBySpec(CustomerParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    List<CustomerResult> findListBySpec(CustomerParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-19
     */
     LayuiPageInfo findPageBySpec(CustomerParam param);

    void getGZHUser() throws Exception;
}
