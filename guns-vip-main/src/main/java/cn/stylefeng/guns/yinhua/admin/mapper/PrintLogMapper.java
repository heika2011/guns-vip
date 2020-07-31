package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.admin.entity.PrintLog;
import cn.stylefeng.guns.yinhua.admin.model.params.PrintLogParam;
import cn.stylefeng.guns.yinhua.admin.model.result.PrintLogResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiexin
 * @since 2020-04-18
 */
public interface PrintLogMapper extends BaseMapper<PrintLog> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    List<PrintLogResult> customList(@Param("paramCondition") PrintLogParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") PrintLogParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    Page<PrintLogResult> customPageList(@Param("page") Page page, @Param("paramCondition") PrintLogParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-04-18
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") PrintLogParam paramCondition);

}
