package cn.stylefeng.guns.yinhua.service;

import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;
import java.util.Map;

/**
 * 新版统计
 * @Date 2020/7/15
 * @Auth xiexin
 */
public interface StatisService {
    List<Map> getDataCount();

    Page<Map> getDataByTypes(String keyWord,Integer progress,Integer sort,
                             String modelId,Integer flag,Long page,
                             Long pageSize, String type);

    Map getOrderInfo(String orderNum);

    Map getTypeDataCount(String type);
}
