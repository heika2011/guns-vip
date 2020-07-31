package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.admin.entity.OrderProgress;
import cn.stylefeng.guns.yinhua.admin.model.params.OrderProgressParam;
import cn.stylefeng.guns.yinhua.admin.model.result.OrderProgressResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 进度表信息 Mapper 接口
 * </p>
 *
 * @author xiexin
 * @since 2020-04-29
 */
public interface OrderProgressMapper extends BaseMapper<OrderProgress> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    List<OrderProgressResult> customList(@Param("paramCondition") OrderProgressParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") OrderProgressParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    Page<OrderProgressResult> customPageList(@Param("page") Page page, @Param("paramCondition") OrderProgressParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-04-29
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") OrderProgressParam paramCondition);

}
