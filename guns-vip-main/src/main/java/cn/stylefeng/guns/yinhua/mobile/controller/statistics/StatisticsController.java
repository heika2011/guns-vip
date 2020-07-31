package cn.stylefeng.guns.yinhua.mobile.controller.statistics;

import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.TeamsData;
import cn.stylefeng.guns.yinhua.service.OrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 统计
 */
@RestController
@RequestMapping("/mobile/statistics")
public class StatisticsController {

    @Autowired
    private OrderService orderService;
    //待前端测试
    /**
     * 小组数据统计
     * @return
     */
    @RequestMapping("/getTeamsData")
    @ApiOperation(value="小组数据统计",notes="token" , httpMethod = "GET")
    public JSONResult getTeamsData(String token, Date startTime,Date overTime){
        List<TeamsData> teamsDataList = orderService.getTeamsData(startTime,overTime);
        return new JSONResult("0","OK",teamsDataList);
    }
}
