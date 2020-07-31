package cn.stylefeng.guns.yinhua.entity.yinhua.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * ModelNote
 */
@Data
@Accessors(chain = true)
public class ModelNote implements Serializable {

    //日志Id
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    //日志内容
    private String text;
    //款式编号
    private String num;
    //内容所带图片
    private String image;
    //日志发起者
    private String name;
    //日志创建时间
    private Date createdTime;
}
