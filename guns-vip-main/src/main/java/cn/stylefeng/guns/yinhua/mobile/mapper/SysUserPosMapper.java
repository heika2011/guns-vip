package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.SysUserPos;
import cn.stylefeng.guns.yinhua.model.params.SysUserPosParam;
import cn.stylefeng.guns.yinhua.model.result.SysUserPosResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户职位关联表 Mapper 接口
 * </p>
 *
 * @author xiexin
 * @since 2020-03-03
 */
public interface SysUserPosMapper extends BaseMapper<SysUserPos> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-03-03
     */
    List<SysUserPosResult> customList(@Param("paramCondition") SysUserPosParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-03-03
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") SysUserPosParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-03-03
     */
    Page<SysUserPosResult> customPageList(@Param("page") Page page, @Param("paramCondition") SysUserPosParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-03-03
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") SysUserPosParam paramCondition);

}
