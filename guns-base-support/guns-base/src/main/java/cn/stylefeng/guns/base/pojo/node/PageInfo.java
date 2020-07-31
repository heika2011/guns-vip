package cn.stylefeng.guns.base.pojo.node;


import lombok.Data;

@Data
public class PageInfo{
    //总数
    private Long total;
    //总页数
    private Long pageCount;
    //当前取得个数
    private Integer hasMore;
    //当前页码
    private Long page;

    public PageInfo(Long total,Long pageCount,Integer hasMore,Long page){
        this.total = total;
        this.pageCount = pageCount;
        this.hasMore = hasMore;
        this.page = page;
    }
}
