package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.mapper.WebMenuMapper;
import cn.stylefeng.guns.yinhua.admin.service.WebMenuService;
import cn.stylefeng.guns.yinhua.entity.SysRole;
import cn.stylefeng.guns.yinhua.entity.WebMenu;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  动态菜单实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-24
 */
@Service
public class WebMenuServiceImpl  implements WebMenuService {
    @Autowired
    WebMenuMapper webMenuMapper;

    @Override
    public List<WebMenu> getAllMenu() {
            List<WebMenu> allMenu = webMenuMapper.getAllMenu();
            return allMenu;

    }
    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }
    @Override
    public List<SysRole> getAllRole() {
        List<SysRole> allRole = webMenuMapper.getAllRole();
        return allRole;
    }

    @Override
    public void InsertRoldMenu(Map parm) {

            String roleId =  (String) parm.get("role");

            List  menuId = (List) parm.get("user");
              webMenuMapper.delectRoleMoneByid(roleId);

                for(int j=0; j<menuId.size();j++){
                    webMenuMapper.insertRoleMonu(roleId,Integer.parseInt(menuId.get(j).toString()));

                }


    }

    @Override
    public LayuiPageInfo findPageBySpec() {
        Page pageContext = getPageContext();
        IPage page = webMenuMapper.customPageList(pageContext);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public Integer deleteByRoleAllMenu( String RoleId) {
        webMenuMapper.deleteByRoleAllMenu(RoleId);

        return null;
    }

    @Override
    public List<Map> selectList(String id) {

        return webMenuMapper.selectListById(id);
    }

    @Override
    public List<WebMenu> selectListAll(Object o) {

        return webMenuMapper.selectList(null);
    }
}
