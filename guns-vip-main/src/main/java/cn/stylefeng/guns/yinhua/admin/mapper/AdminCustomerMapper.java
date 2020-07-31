package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.admin.entity.Customer;
import cn.stylefeng.guns.yinhua.admin.model.params.CustomerParam;
import cn.stylefeng.guns.yinhua.admin.model.result.CustomerResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author xiexin
 * @since 2020-03-19
 */
public interface AdminCustomerMapper extends BaseMapper<Customer> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    List<CustomerResult> customList(@Param("paramCondition") CustomerParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") CustomerParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    Page<CustomerResult> customPageList(@Param("page") Page page, @Param("paramCondition") CustomerParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-03-19
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") CustomerParam paramCondition);


}
