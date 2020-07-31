package cn.stylefeng.guns.yinhua.mobile.mapper;


import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderSheet;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;

/**
 * 工艺单Mapper接口
 */
@Mapper
public interface OrderSheetMapper extends BaseMapper<OrderSheet> {
}
