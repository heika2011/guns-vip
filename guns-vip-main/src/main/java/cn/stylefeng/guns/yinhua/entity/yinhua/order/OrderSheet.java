package cn.stylefeng.guns.yinhua.entity.yinhua.order;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 工艺单
 */
@Data
@Accessors(chain = true)
public class OrderSheet implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //生产单编号
    private String orderNum;
    //工艺单图片URL
    private String url;
}
