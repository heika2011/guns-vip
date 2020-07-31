package cn.stylefeng.guns.yinhua.controller;

import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.yinhua.util.CMYKUtil;
import cn.stylefeng.guns.yinhua.util.FileUtils;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

/**
 * 图片上传
 */
@Controller
public class MobileImageController {

    private static final String[] imageExtension= new String[]{"jpeg", "jpg", "gif", "bmp", "png"};

    @PostMapping("/fileUploads")
    @ResponseBody
    public JSONResult projectPictureUpload(@RequestParam(value = "file",required = true) MultipartFile file){
        //将图片上传到服务器
        if(file.isEmpty()){
            return new JSONResult("-1","图片不能为空");
        }
        //原始文件名
        String originalFilename = file.getOriginalFilename();
        //文件后缀
        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
        //图片名称为uuid+图片后缀防止冲突
        String fileName = UUID.randomUUID().toString()+"."+suffix;
        String os = System.getProperty("os.name");
        //文件保存路径
        String filePath="";
        if(os.toLowerCase().startsWith("win")){
            //windows下的路径
            filePath ="d:/pictureUpload/project/";
        }else {
            //linux下的路径
            filePath="/root/pictureUpload/project/";
        }
        try {
            /*for (String e : imageExtension) {
                if (suffix.toLowerCase().equals(e)) {
                    Thumbnails.of(CMYKUtil.readImage(file.getInputStream())).scale(1f)
                            .outputQuality(0.15f)
                            .toFile(filePath + fileName);
                    //上传成功后，将可以访问的完整路径返回
                    String fullImgpath = "/mobile/images/pictureUpload/project/"+fileName;
                    return new JSONResult("0","ok",fullImgpath);
                }
            }*/
            //写入图片
            Boolean writePictureflag = FileUtils.uploadFile(file.getBytes(),filePath,fileName);
            if(writePictureflag == false){
                //上传图片失败
                return new JSONResult("-1","图片上传失败");
            }
            //上传成功后，将可以访问的完整路径返回
            String fullImgpath = "/mobile/images/pictureUpload/project/"+fileName;
            return new JSONResult("0","ok",fullImgpath);
        } catch (Exception e) {
            e.printStackTrace();
            //上传图片失败
            return new JSONResult("-1","图片上传失败");
        }
    }

    //删除图片文件，只是删除文件，不删除数据库信息
    @PostMapping("/deleteFile")
    @ResponseBody
    public JSONResult delFile(String token,String path) {
        if(FileUtils.deleteFile(path) == 1){
            return new JSONResult("0","ok");
        }else{
            return new JSONResult("-1","文件不存在");
        }
    }
}
