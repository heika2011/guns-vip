package cn.stylefeng.guns.yinhua.mobile.controller.statis;

import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.node.PageResult;
import cn.stylefeng.guns.util.word.beetl.impl.Base64Util;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.service.StatisService;
import cn.stylefeng.guns.yinhua.util.FileUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Date 2020/7/15
 * @Auth xiexin
 */
@RestController
    @RequestMapping("/mobile/statis")
public class StatisController {

    @Autowired
    private StatisService statisService;

    /**
     * 获取数据汇总
     * @return
     */
    @RequestMapping("/getDataCount")
    @Permission
    public JSONResult getDataCount(){
        return JSONResult.OK(statisService.getDataCount());
    }

    /**
     * 根据生产单类型返回列表
     * @param type
     * @return
     */
    @RequestMapping("/getDataByTypes")
    @Permission
    public PageResult<Map> getDataByTypes(String keyWord,Integer progress,Integer sort,String modelId,Integer flag,String type,Long page,Long pageSize){
        Page<Map> orderList = statisService.getDataByTypes(keyWord,progress,sort,modelId,flag,page, pageSize,type);

        return new PageResult(page,pageSize,orderList.getTotal(),orderList.getRecords().size(),orderList.getRecords());
    }

    /**
     * 获取单个信息
     */
    @RequestMapping("/getOrderInfo")
    @Permission
    public JSONResult getOrderInfo(String orderNum){
        return JSONResult.OK(statisService.getOrderInfo(orderNum));
    }

    /**
     * 获取进度统计
     */
    @RequestMapping("/getTypeDataCount")
    @Permission
    public JSONResult getTypeDataCount(String type){
        return JSONResult.OK(statisService.getTypeDataCount(type));
    }
}
