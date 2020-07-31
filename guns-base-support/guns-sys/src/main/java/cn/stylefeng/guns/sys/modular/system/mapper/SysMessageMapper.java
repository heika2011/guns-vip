package cn.stylefeng.guns.sys.modular.system.mapper;

import cn.stylefeng.guns.sys.modular.system.model.entity.SysMessage;
import cn.stylefeng.guns.sys.modular.system.model.params.SysMessageParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SysMessageResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
 * @since 2020-03-23
 */
public interface SysMessageMapper extends BaseMapper<SysMessage> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    List<SysMessageResult> customList(@Param("paramCondition") SysMessageParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysMessageParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    Page<SysMessageResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysMessageParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-03-23
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysMessageParam paramCondition);

}
