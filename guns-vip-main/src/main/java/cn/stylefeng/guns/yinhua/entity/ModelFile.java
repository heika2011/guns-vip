package cn.stylefeng.guns.yinhua.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件
 */
@Data
public class ModelFile implements Serializable {

    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Long num;
    private String url;
}
