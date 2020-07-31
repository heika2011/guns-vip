package cn.stylefeng.guns.yinhua.mobile.mapper;


import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 生产单类型Mapper接口
 */
@Mapper
public interface OrderRoleMapper extends BaseMapper<OrderRole> {
    /**
     * 根据订单类型和进度选择
     * @param orderType
     * @param orderProgress
     * @return
     */
    @Select("select * from order_role where name = (select name from order_role where id = #{orderType}) and type = #{orderProgress}")
    OrderRole selectByIdAndProp(@Param("orderType") Integer orderType,
                                @Param("orderProgress")Integer orderProgress);
}
