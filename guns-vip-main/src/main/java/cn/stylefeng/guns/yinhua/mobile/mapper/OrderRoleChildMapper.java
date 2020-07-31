package cn.stylefeng.guns.yinhua.mobile.mapper;


import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderRole;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderRoleChild;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 生产单类型Mapper接口
 */
@Mapper
public interface OrderRoleChildMapper extends BaseMapper<OrderRoleChild> {

    String selectNameByID(@Param("userId")String userId);

    List<OrderRoleChild> findAllRoleChild(@Param("parentId")String parentId);
}
