package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderProp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;

/**
 * 对应生产进度
 */
@Mapper
public interface OrderPropMapper extends BaseMapper<OrderProp> {

    /**
     * 更新还在报价的同类
     * @param orderNum
     * @param px
     */
    @Delete("update order_prop set do_over = 1 where order_num like concat('%',#{orderNum},'%') and do_over = 0 and type = #{px}")
    void updateAll(@Param("orderNum") String orderNum,@Param("px") String px);

    /**
     * 根据权限id 获取推送内容
     * @param px
     * @param orderNum
     * @return
     */
    @Select("select num from order_role_child where id = (select order_role_id from order_prop where type = #{px} and order_num = #{orderNum})")
    String selectText(@Param("px") String px, @Param("orderNum")String orderNum);

    @Select("select name from order_role where id = #{id}")
    String selectOrderType(@Param("id") Integer id);
}
