package cn.stylefeng.guns.yinhua.entity.yinhua;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户
 */
@Data
@TableName("customer")
public class Customer implements Serializable {
    //客户id
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;
    //客户编号
    private String num;
    //客户名
    private String name;
    //联系人
    private String contact;
    //联系电话
    private String phone;
    //联系地址
    private String addr;
    //客户等级
    private Integer level;
    //客户要求
    private Integer requires;
    //客户结算方式
    private Integer settle;
    //客户备注
    private String note;
    //创建时间
    private Date createdTime;
    //修改时间
    private Date updateTime;
    private Long createdUser;
}
