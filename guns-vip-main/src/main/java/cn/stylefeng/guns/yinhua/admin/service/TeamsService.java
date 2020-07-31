package cn.stylefeng.guns.yinhua.admin.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.yinhua.admin.entity.Teams;
import cn.stylefeng.guns.yinhua.admin.model.params.AdminUserParam;
import cn.stylefeng.guns.yinhua.admin.model.params.TeamsParam;
import cn.stylefeng.guns.yinhua.admin.model.result.TeamsResult;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.Order;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeamU;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
public interface TeamsService extends IService<Teams> {

    /**
     * 新增
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void add(TeamsParam param);

    /**
     * 删除
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void delete(TeamsParam param);

    /**
     * 更新
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    void update(TeamsParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    TeamsResult findBySpec(TeamsParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
    List<TeamsResult> findListBySpec(TeamsParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author xiexin
     * @Date 2020-03-14
     */
     LayuiPageInfo findPageBySpec(TeamsParam param);

    LayuiPageInfo findTeamAndMember(TeamsParam param);

    List<AdminUserParam> findAllPeople();
    UserTeam findTeamAndMemberById(Integer id);

    void updateTeam(UserTeam userTeam,Boolean flag);

    void updateUser(UserTeam userTeam,Boolean flag);
    void deleteTeamUser(Integer id);
    void addTeamUser(UserTeam userTeam,Boolean flag);
    void deleteTeam(Integer id);

}
