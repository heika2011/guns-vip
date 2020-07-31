package cn.stylefeng.guns.util.word.beetl.impl;

import cn.stylefeng.guns.util.word.beetl.IExportWord;
import cn.stylefeng.guns.util.word.beetl.constant.ConstConstant;
import org.beetl.core.Configuration;
import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.core.resource.ClasspathResourceLoader;

import java.io.*;
import java.util.Map;

/**
 * @author: Gibbons
 * @create: 2018/06/08 19:05
 * @description:
 **/
public class BTLExportWord implements IExportWord {

    private static Template t;

    private Template createGroupTemplate(String tempUrl) throws IOException {
        ClasspathResourceLoader resourceLoader = new ClasspathResourceLoader();
        Configuration config = Configuration.defaultConfiguration();
        GroupTemplate groupTemplate = new GroupTemplate(resourceLoader, config);
        Template template = groupTemplate.getTemplate(tempUrl);
        t = template;
        return template;
    }

    public void exportWord(String tempUrl,String outPath, Map<String, Object> dataMap) throws Exception {
        createGroupTemplate(tempUrl);
        File outFile = new File(outPath);
        if (!outFile.getParentFile().exists()) {
            outFile.getParentFile().mkdirs();
        }
        FileOutputStream fos = new FileOutputStream(outFile);
        OutputStreamWriter oWriter = new OutputStreamWriter(fos, "utf-8");
        Writer out = new BufferedWriter(oWriter);
        t.binding(dataMap);
        t.renderTo(out);
        out.close();
    }
}
