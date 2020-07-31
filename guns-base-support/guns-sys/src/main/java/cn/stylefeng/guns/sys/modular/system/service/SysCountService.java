package cn.stylefeng.guns.sys.modular.system.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysCount;
import cn.stylefeng.guns.sys.modular.system.model.params.SysCountParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SysCountResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-24
 */
public interface SysCountService extends IService<SysCount> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    void add(SysCountParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    void delete(SysCountParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    void update(SysCountParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    SysCountResult findBySpec(SysCountParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    List<SysCountResult> findListBySpec(SysCountParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-24
     */
     LayuiPageInfo findPageBySpec(SysCountParam param);
    void updateByType(Map param);
}
