package cn.stylefeng.guns.yinhua.mobile.mapper;

import cn.stylefeng.guns.yinhua.entity.yinhua.model.Model;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.RestOrderCount;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderConst;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestOrderConstLog;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;

/**
 * 款式mapper接口
 */
@Mapper
public interface ModelMapper extends BaseMapper<Model> {

    void setSceenNum(@Param("num")Integer num,@Param("modelNum")Long modelNum);
    RestOrderCount getModelData();
    void updateImageId(@Param("num") Long num,@Param("modelInfoId") Integer modelInfoId,@Param("ids") Integer ids);
    RestOrderConst findModelMuchs(@Param("orderNum") String orderNum);
    List<RestOrderConstLog> findModelMuchsLog(@Param("num") String num);
    void updateModelMuchsNote(@Param("note") String note,@Param("orderNum") String orderNum);
    void updateModelMuchsChangeNote(@Param("note") String note,@Param("orderNum") String orderNum);
    void updateSceenLocal(@Param("num")String num, @Param("local")String local);
    String getSceenLocal(@Param("num")String num);
    void updateStatus(@Param("status")String status,@Param("num")String num);
    Page<Model> selectMyPage(Page page, @Param("userId")String userId,@Param("keyWord")String keyWord, @Param("ew") QueryWrapper<Model> queryWrapper);
    Model selectModelByNum(@Param("userId")String userId,@Param("num")Long num);

    String getModelNote(@Param("num") String num);
    String selectNameByNum(@Param("num") String num);
    void saveModelNote(@Param("num") String num,@Param("note") String note);

    String selectModelNumByNum(@Param("modelId")Long modelId);
}
