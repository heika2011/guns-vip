package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderTeamLog;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.TeamJob;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeamU;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;

/**
 * 小组mapper接口
 */
@Mapper
public interface UserTeamMapper extends BaseMapper<UserTeam> {

    Page<UserTeam> selectAllTeam(@Param("page") Page page, @Param("condition") String condition);
    List<UserTeam> selectAllTeamFor();
    List<TeamJob> selectLeaderName(@Param("id")Integer id);
    List<UserTeamU> selectUser(@Param("id")Integer id);
    List<String> ourTeamOrder(@Param("teamId")Integer teamId, @Param("startTime")Date startTime,@Param("overTime")Date overTime);
    int insertTeamLog(@Param("orderTeamLog")OrderTeamLog orderTeamLog);
    String findOrderConst(@Param("teamId")Integer teamId);
    String findOrderConstByOrderNum(@Param("teamId")Integer teamId,@Param("orderNum")String orderNum);
    void updateOrderTeamLog(@Param("orderConst")String orderConst,@Param("teamId")Integer teamId,@Param("orderNum")String orderNum);
    List<String> findOrderConstByTime(@Param("teamId")String teamId,@Param("startTime")Date startTime,@Param("overTime")Date overTime);
    String findOrderTypeForUserId(@Param("startTime")Date startTime,
                                  @Param("overTime")Date overTime,
                                  @Param("teamId")String teamId,
                                  @Param("userId")String userId);

    List<String> selectTeamData(@Param("teamId")Integer teamId);

    Long selectRoleByName(@Param("name")String name);

    String selectUserHaveRole(@Param("userId")String userId,@Param("roleId")String roleId);

    int updateUserForRole(@Param("flag")Boolean flag,@Param("userId")String userId,@Param("roleId")String roleId);
    String selectOrderPropByName(@Param("type")Integer type,@Param("id")Integer id,@Param("name")String name);

    /**
     * 获取teamid
     * @param toString
     * @return
     */
    String selectTeamId(@Param("userId") String userId);
}
