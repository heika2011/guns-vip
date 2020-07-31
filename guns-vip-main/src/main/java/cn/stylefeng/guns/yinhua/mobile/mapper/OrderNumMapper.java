package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderNum;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 订单数量mapper接口
 */
@Mapper
public interface OrderNumMapper extends BaseMapper<OrderNum> {
}
