package cn.stylefeng.guns.yinhua.service.impl;

import cn.stylefeng.guns.base.auth.jwt.JwtTokenUtil;
import cn.stylefeng.guns.base.auth.jwt.payload.JwtPayLoad;
import cn.stylefeng.guns.base.auth.model.LoginUser;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.sys.core.auth.cache.SessionManager;
import cn.stylefeng.guns.sys.core.exception.OutExcetion;
import cn.stylefeng.guns.yinhua.entity.yinhua.Customer;
import cn.stylefeng.guns.yinhua.mobile.mapper.CustomerMapper;
import cn.stylefeng.guns.yinhua.service.LoginService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private SessionManager sessionManager;
    @Override
    public void doLoginUser(String code) {
        if(StringUtils.isEmpty(code)){
            throw new OutExcetion("微信参数不能为空");
        }
    }

    /**
     * 公众号登录
     * @param wxMpUser
     * @param response
     */
    @Override
    public void doWeChatGHZLogin(WxMpUser wxMpUser, HttpServletResponse response,String url) throws IOException {
        Integer z = customerMapper.selectCount(new QueryWrapper<Customer>().like("open_id", wxMpUser.getOpenId()));

        if(z == 0){
            response.sendRedirect(ConstantsContext.getWebUrl()+"/#/?jumpOrderNum="+url+"&type=21&token=-1");
            return;
        }
        JwtPayLoad jwtPayLoad = new JwtPayLoad();
        jwtPayLoad.setAccount(wxMpUser.getOpenId());
        jwtPayLoad.setUserKey("customer");

        String s = JwtTokenUtil.generateToken(jwtPayLoad);

        LoginUser loginUser = new LoginUser();

        loginUser.setAccount(wxMpUser.getOpenId());
        Set<String> strings = new HashSet<>();
        strings.add("/mobile/model");
        strings.add("/mobile/create_order");
        loginUser.setSee(0);
        loginUser.setPermissions(strings);
        sessionManager.createSession(s,loginUser);

        Boolean aBoolean = JwtTokenUtil.checkToken(s);

        JwtPayLoad jwtPayLoad1 = JwtTokenUtil.getJwtPayLoad(s);

        System.out.println(jwtPayLoad1.getUserKey());

        System.out.println(aBoolean);
        response.sendRedirect(ConstantsContext.getWebUrl()+"/#/?jumpOrderNum="+url+"&type=21&token="+s);
    }
}
