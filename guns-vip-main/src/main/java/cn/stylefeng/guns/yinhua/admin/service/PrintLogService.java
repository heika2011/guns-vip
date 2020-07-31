package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.PrintLog;
import cn.stylefeng.guns.yinhua.admin.model.params.PrintLogParam;
import cn.stylefeng.guns.yinhua.admin.model.result.PrintLogResult;
import cn.stylefeng.guns.yinhua.service.OrderService;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-04-18
 */
public interface PrintLogService extends IService<PrintLog> {

    void print(String orderNum,Integer type, String name,OrderService orderService,Integer printId);
    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    void add(PrintLogParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    void delete(PrintLogParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    void update(PrintLogParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    PrintLogResult findBySpec(PrintLogParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    List<PrintLogResult> findListBySpec(PrintLogParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-04-18
     */
     LayuiPageInfo findPageBySpec(PrintLogParam param);


    void deleteAll();
}
