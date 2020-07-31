package cn.stylefeng.guns.base.pojo.node;

import lombok.Data;

/**
 * 分页返回数据JSON
 */
@Data
public class PageResult<T> extends JSONResult {

    private PageInfo pageInfo;
    //返回分页数据
    public PageResult(Long page,Long pageSize,Long total,Integer dataCount,Object data) {
        super("0", "", data);
        Long pageCount = 1L;
        if((total%pageSize)>0){
            pageCount = (total/pageSize)+1;
        }else{
            pageCount = total/pageSize;
        }
        pageInfo = new PageInfo(total,pageCount,dataCount,page);
    }
}
