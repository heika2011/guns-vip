package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.admin.entity.OrderTeam;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderTeamParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderTeamResult;
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
 * @since 2020-03-14
 */
public interface AdminOrderTeamMapper extends BaseMapper<OrderTeam> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<OrderTeamResult> customList(@Param("paramCondition") OrderTeamParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") OrderTeamParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    Page<OrderTeamResult> customPageList(@Param("page") Page page, @Param("paramCondition") OrderTeamParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") OrderTeamParam paramCondition);

}
