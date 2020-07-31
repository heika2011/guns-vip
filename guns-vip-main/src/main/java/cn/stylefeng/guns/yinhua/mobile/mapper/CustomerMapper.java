package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.yinhua.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 客户 mapper 接口
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {


    String selectNameByNum(String num);

    Long selectNextNum();

    void changeCustomerCreate(@Param("num") String num,@Param("userId") String userId);
}
