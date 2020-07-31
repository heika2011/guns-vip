package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.PrintAre;
import cn.stylefeng.guns.yinhua.admin.model.params.PrintAreParam;
import cn.stylefeng.guns.yinhua.admin.model.result.PrintAreResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-05-23
 */
public interface PrintAreService extends IService<PrintAre> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    void add(PrintAreParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    void delete(PrintAreParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    void update(PrintAreParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    PrintAreResult findBySpec(PrintAreParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    List<PrintAreResult> findListBySpec(PrintAreParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-05-23
     */
     LayuiPageInfo findPageBySpec(PrintAreParam param);

}
