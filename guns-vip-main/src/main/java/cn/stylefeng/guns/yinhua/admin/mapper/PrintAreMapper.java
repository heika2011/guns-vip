package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.admin.entity.PrintAre;
import cn.stylefeng.guns.yinhua.admin.model.params.PrintAreParam;
import cn.stylefeng.guns.yinhua.admin.model.result.PrintAreResult;
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
 * @since 2020-05-23
 */
public interface PrintAreMapper extends BaseMapper<PrintAre> {

    /**
     * 获取列表
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    List<PrintAreResult> customList(@Param("paramCondition") PrintAreParam paramCondition);

    /**
     * 获取map列表
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    List<Map<String, Object>> customMapList(@Param("paramCondition") PrintAreParam paramCondition);

    /**
     * 获取分页实体列表
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    Page<PrintAreResult> customPageList(@Param("page") Page page, @Param("paramCondition") PrintAreParam paramCondition);

    /**
     * 获取分页map列表
     *
     * @author xiexin
     * @Date 2020-05-23
     */
    Page<Map<String, Object>> customPageMapList(@Param("page") Page page, @Param("paramCondition") PrintAreParam paramCondition);

}
