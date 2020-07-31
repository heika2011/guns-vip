package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.entity.SysRole;
import cn.stylefeng.guns.yinhua.entity.WebMenu;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 动态菜单 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-28
 */
public interface WebMenuService {

    List<WebMenu> getAllMenu();

    List<SysRole> getAllRole();

    void InsertRoldMenu(Map parm);

    LayuiPageInfo findPageBySpec();

     Integer  deleteByRoleAllMenu(String roleId);

    List<Map> selectList(String id);

    List<WebMenu> selectListAll(Object o);
}
