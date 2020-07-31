package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.yinhua.Teams;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 小组接口
 */
public interface TeamsMapper  extends BaseMapper<Teams> {
    List<Map> selectStatisCount();

    Page<Map> getDataByTypes(Page<Map> mapPage,
                             @Param("type") String type,
                             @Param("flag") Integer flag,
                             @Param("modelId") String modelId,
                             @Param("keyWord") String keyWord,
                             @Param("progress") Integer progress,
                             @Param("sort") Integer sort);

    Map getOrderInfo(@Param("userId")Long userId, @Param("orderNum")String orderNum);


    Map getTypeDataCount(String type);
}
