package cn.stylefeng.guns.yinhua.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.mobile.mapper.TeamsMapper;
import cn.stylefeng.guns.yinhua.service.StatisService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 新版统计
 * @Date 2020/7/15
 * @Auth xiexin
 */
@Service
public class StatisServiceImpl implements StatisService {

    @Autowired
    private TeamsMapper teamsMapper;

    @Override
    public List<Map> getDataCount() {
        /* 获取数据汇总 */
        List<Map> map = teamsMapper.selectStatisCount();
        return map;
    }

    @Override
    public Page<Map> getDataByTypes(String keyWord,Integer progress,Integer sort,
                                    String modelId,Integer flag,Long page,
                                    Long pageSize, String type) {
        Page<Map> dataByTypes = teamsMapper.getDataByTypes(
                new Page<Map>(page, pageSize), type,flag,modelId,keyWord,progress,sort
        );
        return dataByTypes;
    }


    @Override
    public Map getOrderInfo(String orderNum) {

        return teamsMapper.getOrderInfo(LoginContextHolder.getContext().getUserId(),orderNum);
    }

    @Override
    public Map getTypeDataCount(String type) {
        return teamsMapper.getTypeDataCount(type);
    }
}
