package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.admin.entity.OrderRoleChild;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderRoleChildParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderRoleChildResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xiexin
 * @since 2020-03-16
 */
public interface AdminOrderRoleChildMapper extends BaseMapper<OrderRoleChild> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    List<OrderRoleChildResult> customList(@Param("paramCondition") OrderRoleChildParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") OrderRoleChildParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    Page<OrderRoleChildResult> customPageList(@Param("page") Page page, @Param("paramCondition") OrderRoleChildParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-03-16
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") OrderRoleChildParam paramCondition);

}
