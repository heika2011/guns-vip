package cn.stylefeng.guns.sys.modular.consts.init;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.sys.modular.consts.entity.SysConfig;
import cn.stylefeng.guns.sys.modular.consts.service.SysConfigService;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysCount;
import cn.stylefeng.guns.sys.modular.system.model.entity.SysMessage;
import cn.stylefeng.guns.sys.modular.system.service.SysCountService;
import cn.stylefeng.guns.sys.modular.system.service.SysMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 参数配置 服务类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-20
 */
@Component
@Slf4j
public class SysConfigInit implements CommandLineRunner {

    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private SysMessageService sysMessageService;

    @Autowired
    private SysCountService sysCountService;

    @Override
    public void run(String... args) {

        //初始化所有的常量
        List<SysConfig> list = sysConfigService.list();
        List<SysMessage> msg = sysMessageService.list();
        List<SysCount> sysCount = sysCountService.list();
        if (list != null && list.size() > 0) {
            for (SysConfig sysConfig : list) {
                ConstantsContext.putConstant(sysConfig.getCode(), sysConfig.getValue());
            }
            log.info("初始化常量" + list.size() + "条！");
        }
        if (msg != null && msg.size() > 0) {
            for (SysMessage sysMessage : msg) {
                ConstantsContext.putConstant("msg"+sysMessage.getType(),sysMessage);
            }
            log.info("初始化消息推送常量" + msg.size() + "条！");
        }
        if (sysCount != null && sysCount.size() > 0) {
            for (SysCount s : sysCount) {
                ConstantsContext.putConstant("type"+s.getType(),s.getValue());
            }
            log.info("初始化业务模型常量" + sysCount.size() + "条！");
        }
    }
}
