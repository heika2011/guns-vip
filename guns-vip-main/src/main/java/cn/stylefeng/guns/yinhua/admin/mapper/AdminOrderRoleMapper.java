package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.admin.entity.OrderRole;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderRoleParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderRoleResult;
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
 * @since 2020-03-14
 */
public interface AdminOrderRoleMapper extends BaseMapper<OrderRole> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<OrderRoleResult> customList(@Param("paramCondition") OrderRoleParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") OrderRoleParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    Page<OrderRoleResult> customPageList(@Param("page") Page page, @Param("paramCondition") OrderRoleParam paramCondition,@Param("flag")Integer flag);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") OrderRoleParam paramCondition);

    int insertData(@Param("id") Integer id,@Param("name")String name,@Param("isname")Integer isname);

    /**
     * 修改名称
     * @param name
     * @param param
     */
    void updateAll(@Param("name") String name, @Param("param") OrderRoleParam param);
}
