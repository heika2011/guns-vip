package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.yinhua.admin.entity.Teams;
import cn.stylefeng.guns.yinhua.admin.mapper.AdminTeamsMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.AdminUserParam;
import cn.stylefeng.guns.yinhua.admin.model.params.TeamsParam;
import cn.stylefeng.guns.yinhua.admin.model.result.TeamsResult;
import cn.stylefeng.guns.yinhua.admin.service.OrderTeamService;
import  cn.stylefeng.guns.yinhua.admin.service.TeamsService;
import cn.stylefeng.guns.yinhua.entity.yinhua.order.OrderTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeam;
import cn.stylefeng.guns.yinhua.entity.yinhua.team.UserTeamU;
import cn.stylefeng.guns.yinhua.mobile.mapper.OrderTeamMapper;
import cn.stylefeng.guns.yinhua.mobile.mapper.UserTeamMapper;
import cn.stylefeng.guns.yinhua.mobile.mapper.UserTeamUMapper;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-03-14
 */
@Service
public class TeamsServiceImpl extends ServiceImpl<AdminTeamsMapper, Teams> implements TeamsService {

    @Autowired
    private UserTeamMapper userTeamMapper;

    @Autowired
    private UserTeamUMapper userTeamUMapper;

    @Autowired
    private OrderTeamMapper orderTeamMapper;

    @Override
    public void add(TeamsParam param){
        Teams entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TeamsParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(TeamsParam param){
        Teams oldEntity = getOldEntity(param);
        Teams newEntity = getEntity(param);

        //如果小组编号不同
        if(!oldEntity.getTeamNum().equals(newEntity.getTeamNum())){
            this.baseMapper.updateAllTeamNum(Integer.valueOf(newEntity.getTeamNum()),Integer.valueOf(oldEntity.getTeamNum()));
        }
        //如果组名不同
        if(!oldEntity.getTeamName().equals(newEntity.getTeamName())){
            this.baseMapper.updateAllTeamName(newEntity.getTeamName(),Integer.valueOf(newEntity.getTeamNum()));
        }
        if(newEntity.getLeaderId() == null){
            this.baseMapper.updateLeader("leader_id",newEntity.getId());
            this.baseMapper.updateLeaderForOther("leader_id",newEntity.getId(),Integer.valueOf(newEntity.getTeamNum()));
        }
        if(newEntity.getViceId() == null){
            this.baseMapper.updateLeader("vice_id",newEntity.getId());
            this.baseMapper.updateLeaderForOther("vice_id",newEntity.getId(),Integer.valueOf(newEntity.getTeamNum()));
        }
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TeamsResult findBySpec(TeamsParam param){
        TeamsResult teamsResult = this.baseMapper.selectOneById(param.getId());
        return teamsResult;
    }

    @Override
    public List<AdminUserParam> findAllPeople() {
        List<AdminUserParam> adminUserParams = this.baseMapper.selectPeople();
        return adminUserParams;
    }

    @Override
    public List<TeamsResult> findListBySpec(TeamsParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(TeamsParam param){
        Page pageContext = getPageContext();
        Page<UserTeam> userTeamPage = userTeamMapper.selectAllTeam(pageContext, param.getCondition());
        return LayuiPageFactory.createPageInfo(userTeamPage);
    }

    private Serializable getKey(TeamsParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Teams getOldEntity(TeamsParam param) {
        return this.getById(getKey(param));
    }

    private Teams getEntity(TeamsParam param) {
        Teams entity = new Teams();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }
    @Override
    public LayuiPageInfo findTeamAndMember(TeamsParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.selectTeamsByLeaderId(pageContext, param.getId());
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 查找组
     * @param id
     * @return
     */
    @Override
    public UserTeam findTeamAndMemberById(Integer id) {
        List<UserTeamU> userTeamUS = userTeamMapper.selectUser(id);
        UserTeam id1 = userTeamMapper.selectOne(new QueryWrapper<UserTeam>().eq("id", id));
        id1.setUsers(userTeamUS);
        return id1;
    }

    /**
     * 修改
     * @param userTeam
     */
    @Override
    @Transactional
    public void updateTeam(UserTeam userTeam,Boolean flag) {
        userTeamMapper.updateById(userTeam);
        updateUser(userTeam,flag);
    }

    /**
     * 修改组员
     * @param userTeam
     */
    @Override
    @Transactional
    public void updateUser(UserTeam userTeam,Boolean flag) {
        /* 判断 是否组长副组长只有一位 */
        for(UserTeamU u: userTeam.getUsers()){
            u.setTeamId(userTeam.getId());
            u.setUserId(u.getUserId());
            int i = userTeamUMapper.updateById(u);
            if(i == 0){
                userTeamUMapper.insert(u);
            }
           /* *//* 判断是打样组还是生产组 *//*
            if(userTeam.getType() == 1 || userTeam.getType()==3 ||  userTeam.getType()==4){
                *//* 判断系统下是否有打版角色 *//*
                Long mem = userTeamMapper.selectRoleByName("打版");
                *//* 没有就报错 *//*
                if(mem == null){
                    throw new OutExcetion("请检查角色中是否存在打版");
                }
                *//* 取出待设置用户是否有打版岗位 *//*
                String s = userTeamMapper.selectUserHaveRole(u.getUserId().toString(), mem.toString());
                *//* 如果没有就设置一个*//*
                if(s==null||flag){
                    userTeamMapper.updateUserForRole(flag,u.getUserId().toString(),mem.toString());
                }
            }*/
            if(userTeam.getType()>0){
                /* 修改该用户下是否有生产组长或者生产组长权限 */
                if(u.getJob()==1 || u.getJob() ==2){
                    /* 判断系统下是否有生产组长角色 */
                    Long mem = userTeamMapper.selectRoleByName("生产组长");
                    /* 没有就报错 */
                    if(mem == null){
                        throw new OutExcetion("请检查角色中是否存在生产组长");
                    }
                    /* 取出待设置用户是否有生产组长岗位 */
                    String s = userTeamMapper.selectUserHaveRole(u.getUserId().toString(), mem.toString());
                    /* 如果没有就设置一个*/
                    if(s==null||flag){
                        userTeamMapper.updateUserForRole(flag,u.getUserId().toString(),mem.toString());
                    }
                    /* 修改该用户下是否有生产组员权限 */
                }else if(u.getJob() == 3){
                    /* 判断系统下是否有生产组员角色 */
                    Long mem = userTeamMapper.selectRoleByName("生产组员");
                    /* 没有就报错 */
                    if(mem == null){
                        throw new OutExcetion("请检查角色中是否存在生产组员");
                    }
                    /* 取出待设置用户是否有生产组员岗位 */
                    String s = userTeamMapper.selectUserHaveRole(u.getUserId().toString(), mem.toString());
                    /* 如果没有就设置一个*/
                    if(s==null||flag){
                        userTeamMapper.updateUserForRole(flag,u.getUserId().toString(),mem.toString());
                    }
                }
            }
        }
    }

    @Override
    public void deleteTeamUser(Integer id) {
        UserTeamU userTeamU = userTeamUMapper.selectById(id);
        orderTeamMapper.delete(new QueryWrapper<OrderTeam>().eq("user_id",userTeamU.getUserId()));
        userTeamUMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void addTeamUser(UserTeam userTeam,Boolean flag) {
        userTeam.setCreatedTime(new Date());
        userTeamMapper.insert(userTeam);
        updateUser(userTeam,flag);
    }

    @Override
    @Transactional
    public void deleteTeam(Integer id) {
        orderTeamMapper.delete(new QueryWrapper<OrderTeam>().eq("team_id",id));
        userTeamUMapper.delete(new QueryWrapper<UserTeamU>().eq("team_id",id));
        userTeamMapper.deleteById(id);
    }


}
