package cn.stylefeng.guns.sys.modular.system.mapper;

import cn.stylefeng.guns.sys.modular.system.model.entity.SysCount;
import cn.stylefeng.guns.sys.modular.system.model.params.SysCountParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SysCountResult;
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
 * @since 2020-03-24
 */
public interface SysCountMapper extends BaseMapper<SysCount> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    List<SysCountResult> customList(@Param("paramCondition") SysCountParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysCountParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    Page<SysCountResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysCountParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-03-24
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysCountParam paramCondition);

    int updateValueByType(Integer value,Integer type);
}
