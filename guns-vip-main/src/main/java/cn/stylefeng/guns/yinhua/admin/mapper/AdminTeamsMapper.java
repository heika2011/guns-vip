package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.admin.entity.Teams;
import cn.stylefeng.guns.yinhua.admin.model.params.AdminUserParam;
import cn.stylefeng.guns.yinhua.admin.model.params.TeamsParam;
import cn.stylefeng.guns.yinhua.admin.model.result.TeamsResult;
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
public interface AdminTeamsMapper extends BaseMapper<Teams> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<TeamsResult> customList(@Param("paramCondition") TeamsParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") TeamsParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    Page<TeamsResult> customPageList(@Param("page") Page page, @Param("paramCondition") TeamsParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") TeamsParam paramCondition);

    Page<TeamsResult> selectTeamsByLeaderId(@Param("page") Page page,Integer id);
    TeamsResult selectOneById(Integer id);

    List<AdminUserParam> selectPeople();

    void updateAllTeamName(String name,Integer teamNum);
    void updateAllTeamNum(Integer newTeamNum,Integer oldTeamNum);
    void updateLeader(String name,Integer id);
    void updateLeaderForOther(String name,Integer id,Integer teamNum);
    List<TeamsResult> selectTeamsByLeaderId(Integer id);
}
