package cn.stylefeng.guns.yinhua.mobile.controller;


import cn.stylefeng.guns.base.auth.annotion.Permission;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Map;

/**
 * 员工菜单获取
 */
@RestController
@RequestMapping("/mobile/menu")
public class WeChatMenuController {

    @Autowired
    private UserService userService;

    /**
     * 获取用户的菜单信息
     * @return
     */
    @RequestMapping("/getUserMenus")
    @ApiOperation(value="获取用户对应菜单",notes="无需参数，需带token访问", httpMethod = "GET")
    public JSONResult getUserMenus(String token){
        Map<String, Object> userIndexInfo = userService.getUserIndexInfo("typ");
        return new JSONResult("0","OK",userIndexInfo);
    }
    //删除图片文件，只是删除文件，不删除数据库信息
    @RequestMapping("/deleteFile")
    @ResponseBody
    public JSONResult delFile(String token,String path) {
        int lastIndexOf = path.lastIndexOf("/");
        String img_path = path.substring(lastIndexOf + 1, path.length());
        //System.out.println(img_path);
        String os = System.getProperty("os.name");
        //文件保存路径
        String filePath="";
        if(os.toLowerCase().startsWith("win")){
            //windows下的路径
            filePath ="d:/pictureUpload/project/" + img_path;
        }else {
            //linux下的路径
            filePath="/root/pictureUpload/project/" + img_path;
        }
        //System.out.println(img_path);//输出测试一下文件路径是否正确
        File file = new File(filePath);
        if (file.exists()) {//文件是否存在
            if (file.delete()) {//存在就删了，返回1
                return new JSONResult("0","ok");
            } else {
                return new JSONResult("-1","文件不存在");
            }
        } else {
            return new JSONResult("-1","文件不存在");
        }
    }
}
