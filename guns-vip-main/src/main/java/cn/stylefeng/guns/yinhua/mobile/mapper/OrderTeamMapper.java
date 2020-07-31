package cn.stylefeng.guns.yinhua.mobile.mapper;


import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderTeam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单小组mapper
 */
@Mapper
public interface OrderTeamMapper extends BaseMapper<OrderTeam> {
    /**
     * 根据生产单编号判断哪个组
     * @param orderNum
     * @return
     */
    @Select("select team_id from order_team where order_num = #{orderNum} group by order_num")
    Integer selectOrderTeamId(@Param("orderNum") String orderNum);

    @Select("select team_id from order_team where order_num = #{orderNum} and type = 0 group by order_num")
    Integer findOrderForTeamId(@Param("orderNum")String orderNum);

    @Select("select user_id from user_team_u where team_id = #{team}")
    List<String> selectAllUserTeamId(@Param("team")Integer team);

    @Select("select user_id from order_team where type is null and sure_flag = 1 and order_num =#{orderNum}")
    List<String> selectUserIdForOrderNum(@Param("orderNum")String orderNum);

    @Select("select account from sys_user where user_id = #{userId}")
    String selectAccountByUserId(@Param("userId")String userId);
}
