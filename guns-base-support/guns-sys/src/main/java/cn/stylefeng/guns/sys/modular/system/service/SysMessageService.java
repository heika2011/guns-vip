package cn.stylefeng.guns.sys.modular.system.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysMessage;
import cn.stylefeng.guns.sys.modular.system.model.params.SysMessageParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SysMessageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-23
 */
public interface SysMessageService extends IService<SysMessage> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    void add(SysMessageParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    void delete(SysMessageParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    void update(SysMessageParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    SysMessageResult findBySpec(SysMessageParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    List<SysMessageResult> findListBySpec(SysMessageParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-23
     */
     LayuiPageInfo findPageBySpec(SysMessageParam param);

}
