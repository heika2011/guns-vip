package cn.stylefeng.guns.yinhua.admin.service.impl;

import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.node.JSONResult;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.third.service.HttpUtil;
import cn.stylefeng.guns.util.word.beetl.IExportWord;
import cn.stylefeng.guns.util.word.beetl.impl.BTLExportWord;
import cn.stylefeng.guns.util.word.beetl.impl.Base64Util;
import cn.stylefeng.guns.yinhua.admin.entity.PrintAre;
import cn.stylefeng.guns.yinhua.admin.entity.PrintLog;
import cn.stylefeng.guns.yinhua.admin.mapper.PrintLogMapper;
import cn.stylefeng.guns.yinhua.admin.model.params.PrintLogParam;
import cn.stylefeng.guns.yinhua.admin.model.result.PrintLogResult;
import cn.stylefeng.guns.yinhua.admin.service.OrdersService;
import cn.stylefeng.guns.yinhua.admin.service.PrintAreService;
import  cn.stylefeng.guns.yinhua.admin.service.PrintLogService;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.ModelCut;
import cn.stylefeng.guns.yinhua.entity.yinhua.rest.RestAllOrder;
import cn.stylefeng.guns.yinhua.mobile.mapper.OrderPropMapper;
import cn.stylefeng.guns.yinhua.service.OrderService;
import cn.stylefeng.guns.yinhua.util.FileUtils;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.http.HttpEntity;
import org.apache.http.HttpVersion;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xiexin
 * @since 2020-04-18
 */
@Service
public class PrintLogServiceImpl extends ServiceImpl<PrintLogMapper, PrintLog> implements PrintLogService {
    /*@Autowired
    private OrderService orderService;*/
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-hh");

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private PrintAreService printAreService;
    @Autowired
    private OrderPropMapper orderPropMapper;
    /**
     * 打印相关
     * @param orderNum
     * @param name
     */
    @Async("taskExecutor")
    @Override
    public void print(String orderNum,Integer type, String name,OrderService orderService,Integer printId) {
        /* 获取订单数据 */
        RestAllOrder orderInfo = orderService.getOrderInfo(1, orderNum,true);
        /* 创建打印对象 */
        IExportWord exportWord;
        exportWord = new BTLExportWord();
        /* 创建待转发内容 */
        HashMap<String, Object> stringRestAllOrderHashMap = new HashMap<>();
        List<ModelCut> modelInfo = orderInfo.getRestAllModel().getModelInfo();
        /* 将图片信息转成 base64 内容 */
        for(int i=0;i<modelInfo.size();i++){
            if(modelInfo.get(i).getModelImage().size()>0&&
                    modelInfo.get(i).getModelImage().get(0) != null &&
                    (!"".equals(modelInfo.get(i).getModelImage().get(0).getUrl()))){
                modelInfo.get(i).getModelImage().get(0).
                        setUrl(Base64Util.getImgStr(FileUtils.getImageLocalUrl(modelInfo.get(i).getModelImage().get(0).getUrl().split(",")[0])));

            }
        }
        /* 设置数据 产生二维码*/
        String orderTypeName = orderPropMapper.selectOrderType(orderInfo.getRestOrder().getOrderType());
        stringRestAllOrderHashMap.put("order",orderInfo);
        stringRestAllOrderHashMap.put("orderType",orderTypeName);
        stringRestAllOrderHashMap.put("date",simpleDateFormat.format(new Date()));
        String s = Base64Util.creatRrCode(ConstantsContext.getWeChatKxUrl()+"/oauth/render/wechat?url="+orderNum+"&type=20", 500, 500);
        stringRestAllOrderHashMap.put("images",s);
        //文件保存路径
        String filePath="";
        String os = System.getProperty("os.name");
        String format = simpleDateFormat.format(new Date());
        if(os.toLowerCase().startsWith("win")){
            //windows下的路径
            filePath ="d:/pictureUpload/doc/"+format+"/";
        }else {
            //linux下的路径
            filePath="/root/pictureUpload/doc/"+format+"/";
        }
        String fileName = UUID.randomUUID().toString();
        PrintLog printLog = new PrintLog();
        Integer orderType = 0;
        try {
            if(type == 1){
                exportWord.exportWord("/templates/gongyidan_bak.btl",filePath+fileName+".doc",stringRestAllOrderHashMap);
            }else if(type == 2){
                orderType = ordersService.getOrderType(orderNum);
                if(orderType == 1){
                    exportWord.exportWord("/templates/yinhua.btl",filePath+fileName+".doc",stringRestAllOrderHashMap);
                }else if(orderType == 2){
                    exportWord.exportWord("/templates/jiguang.btl",filePath+fileName+".doc",stringRestAllOrderHashMap);
                }else if(orderType == 3){
                    exportWord.exportWord("/templates/yimo.btl",filePath+fileName+".doc",stringRestAllOrderHashMap);
                }
            }
            printLog.setCreatedTime(new Date());
            printLog.setName(name);
            printLog.setStatus(0);
            printLog.setOrderNum(orderNum);
            printLog.setUrl("/mobile/upload/doc/"+format+"/"+fileName+".doc");
            this.baseMapper.insert(printLog);
        } catch (Exception e) {
            e.printStackTrace();
        }

        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        String resultString = "";
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().
                    setConnectTimeout(2000000000).
                    setConnectionRequestTimeout(2000000000).
                    setSocketTimeout(2000000000).build();
            // 创建Http Post请求
            HttpPost httpPost = new HttpPost("http://cloud.wisiyilink.com/api/print/job");
            //httpPost.setHeader(ConstantUtil.CONTENT_TYPE, ConstantUtil.CONTENT_TYPE_VALUE);
            httpPost.setConfig(requestConfig);
            httpPost.setProtocolVersion(HttpVersion.HTTP_1_0);
            httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);

            // 设置Header信息
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            /* 设置文件 */
            builder.addPart("jobFile", new FileBody(new File(filePath+fileName+".doc")));
            /*设置打印参数 */
            Map<String,String> params = new HashMap<>();
            PrintAre byId = printAreService.getById(printId);
            params.put("deviceId",byId.getToken());
            params.put("deviceKey",byId.getPrintKey());
            params.put("devicePort",byId.getPrintPort().toString());
            params.put("printerModel",byId.getPrintName());
            params.put("dmPaperSize","9");
            if(type == 1){
                params.put("dmOrientation","1");
            }else if(type ==2){
                if(orderType == 1){
                    params.put("dmOrientation","1");
                }else {
                    params.put("dmOrientation","2");
                }
            }
            params.put("dmCopies","1");
            params.put("dmDefaultSource","261");
            params.put("dmColor","2");
            params.put("dmDuplex","1");
            params.put("dmMediaType","1");
            params.put("jpPageRange","");
            params.put("jpAutoRotate","0");
            params.put("jpAutoScale","4");
            params.put("jpAutoAlign","z5");
            params.put("isPreview","0");
            /*将参数送往打印端*/
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    StringBody contentBody = new StringBody(entry.getValue(),
                            "form-data", Charset.forName("UTF-8"));
                    builder.addPart(entry.getKey(), contentBody);
                }
            }
            /*创建client对象*/
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                /*发送数据*/
                resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
                /* 接收任务id*/
                JSONObject jsonObject = JSONObject.parseObject(resultString);
                jsonObject = jsonObject.getJSONObject("data");
                /*设置该订单状态为正在打印*/
                printLog.setStatus(2);
                System.out.println("===================================");
                this.baseMapper.updateById(printLog);
                /* 任务id*/
                String task_id;
                String task_id1 = jsonObject.getString("task_id");
                String task_state;
                Integer count = 0;
                System.out.println("==================================="+Thread.currentThread().getName());
                do {
                    /*睡眠五秒获取结果*/
                    Thread.sleep(5000);
                    System.out.println(count);
                    /* 获取是否成功*/
                    task_id = HttpUtil.get("http://cloud.wisiyilink.com/api/print/job?deviceId=ps02ca94970011&deviceKey=WGAjBretktVjIyOf&task_id=" + task_id1);
                    count = count +1;
                    jsonObject = JSONObject.parseObject(task_id);
                    JSONObject data = jsonObject.getJSONObject("data");
                    task_state = data.getString("task_state");
                    if("SUCCESS".equals(task_state)){
                        printLog.setStatus(1);
                        break;
                    }
                }while (count < 10);
                if(count >= 10){
                    printLog.setStatus(4);
                }
            }
            this.baseMapper.updateById(printLog);
        } catch (Exception e) {
            //logger.debug("超时异常,异常为{},异常信息为{}", e, e.getMessage());
            //MsgResponse.failMessage(ConstantUtil.OUT_OF_DATE);
        } finally {
            try {
                if (response != null) {
                    response.close();

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            httpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void add(PrintLogParam param){
        PrintLog entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void deleteAll() {
        this.baseMapper.delete(null);
    }

    @Override
    public void delete(PrintLogParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(PrintLogParam param){
        PrintLog oldEntity = getOldEntity(param);
        PrintLog newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public PrintLogResult findBySpec(PrintLogParam param){
        return null;
    }

    @Override
    public List<PrintLogResult> findListBySpec(PrintLogParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(PrintLogParam param){
        Page pageContext = getPageContext();
        IPage page = this.baseMapper.customPageList(pageContext, param);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(PrintLogParam param){
        return param.getId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private PrintLog getOldEntity(PrintLogParam param) {
        return this.getById(getKey(param));
    }

    private PrintLog getEntity(PrintLogParam param) {
        PrintLog entity = new PrintLog();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
