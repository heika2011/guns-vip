package cn.stylefeng.guns.base;

import cn.stylefeng.guns.sys.modular.third.service.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 基础测试类
 *
 * @author stylefeng
 * @Date 2017/5/21 16:10
 */
@RunWith(SpringRunner.class)
@WebAppConfiguration
//@Transactional //打开的话测试之后数据可自动回滚
public class BaseJunit {

    @Autowired
    WebApplicationContext webApplicationContext;

    protected MockMvc mockMvc;

    @Before
    public void setupMockMvc() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void sendFile(){
        String targetURL = null;// TODO 指定URL
        File targetFile = null;// TODO 指定上传文件

        targetFile = new File("d://合同模板.doc");
        targetURL = "http://cloud.wisiyilink.com/api/print/job"; //servleturl
        PostMethod filePost = new PostMethod(targetURL);

        try

        {

            //通过以下方法可以模拟页面参数提交
            filePost.setParameter("deviceId", "ps02ca94970011");
            filePost.setParameter("deviceKey", "WGAjBretktVjIyOf");
            filePost.setParameter("devicePort", "2");
            filePost.setParameter("printerModel", "EPSON L310 Series");
            List<NameValuePair> nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("deviceId","ps02ca94970011"));
            //filePost.setRequestHeader("ApiKey","Sqja70itcdXSFTnkbYWHNhWvhC0x1AJq");
            filePost.setRequestHeader("Content-Disposition","form-data");

            System.out.println(filePost.getParameters());
            FilePart filePart = new FilePart(targetFile.getName(), targetFile);
            filePart.setContentType("application/msword");
            filePart.setName("jobFile");
            Part[] parts = { filePart };
            filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
            HttpClient client = new HttpClient();
            client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
            int status = client.executeMethod(filePost);

            if (status == HttpStatus.SC_OK)
            {
                HttpClientParams params = client.getParams();
                System.out.println("上传成功");
                // 上传成功
            }
            else
            {
                System.out.println("上传失败");
                // 上传失败
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        finally
        {
            filePost.releaseConnection();
        }
    }
    @Before
    public void initDatabase() {
    }

    @Test
    public void testPost(){
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
            /*httpPost.setProtocolVersion(HttpVersion.HTTP_1_0);
            httpPost.addHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);*/

            // 设置Header信息
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("jobFile", new FileBody(new File("d://合同模板.doc")));
            Map<String,String> params = new HashMap<>();
            params.put("deviceId","ps02ca94970011");
            params.put("deviceKey","WGAjBretktVjIyOf");
            params.put("devicePort","2");
            params.put("printerModel","EPSON L310 Series");
            params.put("dmPaperSize","9");
            params.put("dmOrientation","1");
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
            if (params != null && !params.isEmpty()) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    StringBody contentBody = new StringBody(entry.getValue(),
                            "form-data", Charset.forName("UTF-8"));
                    builder.addPart(entry.getKey(), contentBody);
                }
            }
            HttpEntity entity = builder.build();
            httpPost.setEntity(entity);
            response = httpClient.execute(httpPost);
            HttpEntity responseEntity = response.getEntity();
            if (responseEntity != null) {
                resultString = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
                JSONObject jsonObject = JSONObject.parseObject(resultString);
                jsonObject = jsonObject.getJSONObject("data");
                String task_id;
                String task_id1 = jsonObject.getString("task_id");
                String task_state;
                do {
                    task_id = HttpUtil.get("http://cloud.wisiyilink.com/api/print/job?deviceId=ps02ca94970011&deviceKey=WGAjBretktVjIyOf&task_id=" + task_id1);
                    jsonObject = JSONObject.parseObject(task_id);
                    JSONObject data = jsonObject.getJSONObject("data");
                    task_state = data.getString("task_state");
                }while (!"SUCCESS".equals(task_state));
            }
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
}
