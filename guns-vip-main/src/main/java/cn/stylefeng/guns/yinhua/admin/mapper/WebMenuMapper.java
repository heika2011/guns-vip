package cn.stylefeng.guns.yinhua.admin.mapper;

import cn.stylefeng.guns.yinhua.entity.SysRole;
import cn.stylefeng.guns.yinhua.entity.WebMenu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 动态菜单 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-28
 */
public interface WebMenuMapper extends BaseMapper<WebMenu> {
    /**
     * 获取菜单列表
     *
     *
     */
    @Select("SELECT * FROM sys_role")
    List<SysRole> getAllRole();
    /**
     * 获取角色表
     *
     *
     */
    @Select("SELECT * FROM web_menu")
    List<WebMenu> getAllMenu();

    @Insert("insert into web_menu_role values(#{roleId},#{menuId},1) " )
    Integer  insertRoleMonu(@Param("roleId")String roleId,@Param("menuId")Integer menuId);
    @Delete("delete  from web_menu_role where role_id=#{roleId}")
    Integer  delectRoleMoneByid(String roleId);

    IPage customPageList(@Param("page")Page page);

    @Delete("delete  from web_menu_role where role_id=#{roleId}")
    void deleteByRoleAllMenu(String roleId);

    List<Map> selectListById(String id);
}
