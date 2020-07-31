package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.yinhua.FaConst;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderConstLog;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderProp;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrderConstLogMapper extends BaseMapper<OrderConstLog> {

    void saveFaConst(@Param("faConst")Double faConst,@Param("faNum")String faNum,@Param("orderNum")String orderNum);

    FaConst getOrderFaConst(@Param("orderNum")String orderNum);

    Map<String,Object> selectLastCountByNum(@Param("num")String num);

    Double selectNewConst(String num);

    List<OrderProp> selectOrderPropList();

    List<OrderProp> selectOrderPropListByOrders(Integer i);

    List<OrderProp> selectOrderPropListByOrdersConst();
}
