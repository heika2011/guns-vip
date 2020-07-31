package cn.stylefeng.guns.yinhua.entity.yinhua.rest;


import cn.stylefeng.guns.base.pojo.node.PageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.ss.formula.functions.T;

import java.util.List;

/**
 * 返回数据与内容整合
 */
@Data
@Accessors(chain = true)
@AllArgsConstructor
public class RestPageAndData {

    //分页数据
    private PageResult pageData;

    //整合数据
    private List<RestType> restType;
}
