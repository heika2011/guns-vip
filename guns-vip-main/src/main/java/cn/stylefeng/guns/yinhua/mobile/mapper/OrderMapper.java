package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.admin.model.params.AdminUserParam;
import cn.stylefeng.guns.yinhua.entity.yinhua.Express;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderUser;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderNumCount;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 生产单日志
 */
public interface OrderMapper extends BaseMapper<Order> {
    //跟单数据汇总
    RestOrderCount getCreateData(@Param("px")String px,@Param("userId")Long userId);
    //制版数据汇总
    RestOrderCount getPMData(@Param("userid")String userid,@Param("px")String px);
    //晒版数据汇总
    RestOrderCount getPDData(@Param("px")String px);
    //丝印数据汇总
    RestOrderCount getSPata(@Param("userid")String userid,@Param("px")String px);
    //查询丝印的订单
    List<String> selectMyOrderForSP(@Param("type")String type,@Param("px")String px);
    //彩印数据
    RestOrderCount getDCDate(@Param("userid")String userid,@Param("px")String px);
    //打样
    RestOrderCount getPCDate(@Param("userid")String userid,@Param("px")String px);
    //搜索我小组的订单
    List<String> selectMyOrderByUserId(@Param("userId")String userId,@Param("px")String px);
    //生产
    RestOrderCount getPDCDate(@Param("userId")String userId,@Param("px")String px);
    //设置订单状态
    void setOverFlag(@Param("status")Integer status,@Param("orderNum")String orderNum);
    //报价数据
    RestOrderCount getBJDate(@Param("px")String px);
    //财务数据
    RestOrderCount getCWDate(@Param("px")String px);
    //返回数据
    Map selectDataFor();
    //返回数据
    List<String> selectForPMC(@Param("px")String px);

    Long selectZT(@Param("userId")Long userId,@Param("type")String type);
    //搜索月份数据
    List<OrderUser> selectMonth(@Param("timer") Integer timer,@Param("userId") Long userId,@Param("type")String type);
    //搜索用户id
    List<String> selectUserId(@Param("user") String user);
    //根据菜单id搜索用户
    List<AdminUserParam> selectRoleIdByMenuId(@Param("menuId") String menuId);
    //根据小组Id获取相关小组统计
    List<RestOrderNumCount> selectOrderNumCount(@Param("teamId") String teamId);
    //根据开始与结束时间获取小组数据
    List<RestOrderNumCount> selectOrderNumCountByTime(@Param("teamId") String teamId,@Param("startTime") Date startTime,@Param("overTime") Date overTime);
    //查找是否有赔偿订单
    List<String> selectHaveMakeUp(@Param("startTime")Date startTime,@Param("overTime")Date overTime);
    //分派打样数据搜搜
    List<String> selectForFPDY(@Param("userId") String userId,@Param("px") String px,@Param("type") String type);
    //打样数据搜索
    List<String> selectForDY(@Param("userId") String userId,@Param("px") String px,@Param("type") String type);
    //获取催单的对应的人员
    List<String> uragForPeople(@Param("orderNum") String orderNum);
    //更新快递信息
    void updateExpress(@Param("express")Express express);
    //获取快递信息
    Express getExpressInfo(@Param("orderNum") String orderNum);
    //结束订单
    void overOrder(@Param("orderNum") String orderNum);
    //根据款号搜素名字
    String findCustomerByNum(@Param("num")String num);
    //列表
    Page<Order> selectMyPage(Page page, @Param("userId")Long userId,@Param("keyWord")String keyWord,@Param("ew") QueryWrapper<Order> queryWrapper);

    //财务搜索
    Page<Order> selectCWMyPage(Page page, @Param("userId")Long userId,@Param("keyWord")String keyWord,@Param("ew") QueryWrapper<Order> queryWrapper);

    //搜索统计
    RestOrderCount selectQMData(@Param("startTime")Date startTime,@Param("overTime") Date overTime);

    //总检的编号
    List<String> selectZJData();
    //总检的数据
    RestOrderCount selectZJDataForMsg();
    //搜索某个小组得订单
    List<String> selectTeamData(@Param("teamId")Integer teamId);
    RestOrderCount selectTeamLearData(@Param("userId")String userId);
    RestOrderCount selectDriver(String px);

    Order selectOrderByNum(@Param("userId")String userId,@Param("orderNum")String orderNum);

    Integer selectTodayCount(@Param("ew")QueryWrapper<Order> queryWrapper);

    List<String> selectMyOrderByUserIdForToday(@Param("userId")String userId, @Param("px")String px);

    /**
     * 根据生产单编号查找类型编号
     * @param orderNum
     * @return
     */
    Integer selectTypeNumByOrderNum(String orderNum);

    /**
     * 结束分派
     * @param orderNum
     */
    void overFp(String orderNum);

    /**
     * 订单分派结束标志位
     * @param order_num
     * @return
     */
    Integer findOverFp(String order_num);

    List<String> selectMyOrderByUserIdForUn(@Param("userId")String userId,@Param("px")String px);

    List<String> selectByUserId(@Param("id")String id);

    @Select("select print_id from sys_user where user_id = #{userId}")
    Integer getMyPrintId(Long userId);

    @Update("update sys_user set print_id = #{printId} where user_id = #{userId}")
    void updatePrintId(@Param("printId")Integer printId,@Param("userId") Long userId);

    @Select("select url from order_sheet where order_num = #{orderNum} order by id desc  limit 1")
    String getOrderSheet(String orderNum);

    @Select("SELECT * FROM web_menu WHERE id IN (SELECT menu_id FROM web_menu_role WHERE (\n" +
            "SELECT role_id FROM sys_user WHERE user_id = #{userId}) LIKE CONCAT('%',role_id,'%')\n" +
            "GROUP BY menu_id)")
    List<Map> deleteGetAllMenu(String userId);
}
