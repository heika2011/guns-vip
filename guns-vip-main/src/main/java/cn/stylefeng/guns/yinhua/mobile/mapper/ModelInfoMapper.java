package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.yinhua.model.ModelInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

/**
 * 款式裁片mapper接口
 */
@Mapper
public interface ModelInfoMapper extends BaseMapper<ModelInfo> {
    @Update("update model_info set screen_type = '老版' where num = #{num}")
    void updateScreenModel(String num);
}
