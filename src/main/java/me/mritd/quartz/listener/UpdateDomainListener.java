package me.mritd.quartz.listener;

import me.mritd.utils.PropertiesUtil;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * DDNS_Tool
 * me.mritd.quartz.listener
 * Created by mritd on 16/7/31 14:31.
 */

public class UpdateDomainListener implements JobListener {


    private static final Logger logger = LoggerFactory.getLogger(UpdateDomainListener.class);

    public String getName() {
        return "UpdateDomainListener";
    }

    public void jobToBeExecuted(JobExecutionContext jobExecutionContext) {

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        logger.info("DDNS JOB EXEC...");

        logger.info("检查当前 IP ...");

        String myIP = "";

        InputStream ins = null;
        try {
            URL url = new URL("http://1212.ip138.com/ic.asp");
            URLConnection con = url.openConnection();
            ins = con.getInputStream();
            InputStreamReader isReader = new InputStreamReader(ins, "GB2312");
            BufferedReader bReader = new BufferedReader(isReader);
            StringBuilder webContent = new StringBuilder();
            String str;
            while ((str = bReader.readLine()) != null) {
                if (str.contains("您的IP是：[")) {
                    webContent.append(str);
                    break;
                }
            }
            int start = webContent.indexOf("[") + 1;
            int end = webContent.indexOf("]");
            myIP = webContent.substring(start, end);
        } catch (UnsupportedEncodingException e) {
            logger.error("不支持的字符集: ", e);
        } catch (IOException e) {
            logger.error("获取当前 IP 出现 I/O 异常: ", e);
        } finally {
            if (ins != null) {
                try {
                    ins.close();
                } catch (IOException e) {
                    logger.error("资源释放失败: ", e);
                }
            }
        }

        logger.info("加载配置文件...");

        String configType = PropertiesUtil.getValue("useConfig");
        dataMap.put("myIP", myIP);
        dataMap.put("domainType", PropertiesUtil.getValue("domainType"));
        dataMap.put("domain", PropertiesUtil.getValue("domain"));
        dataMap.put("RR",PropertiesUtil.getValue("RR"));
        dataMap.put("AccessKey", PropertiesUtil.getValue(configType + "_AccessKey"));
        dataMap.put("AccessKeySecret", PropertiesUtil.getValue(configType + "_AccessKeySecret"));

        try {
            Class clazz = Class.forName("me.mritd.ddns.impl." + configType + "UpdateDomainRecord");
            dataMap.put("updateDomainRecord", clazz.newInstance());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            logger.error("反射创建 更新域名解析对象失败: ", e);
        }

    }

    public void jobExecutionVetoed(JobExecutionContext jobExecutionContext) {


    }

    public void jobWasExecuted(JobExecutionContext jobExecutionContext, JobExecutionException e) {
        if (e != null) {
            logger.error("DDNS 更新出现异常:", e);
        }

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();

        Boolean updateFlag = (Boolean) dataMap.get("updateFlag");

        if (updateFlag) {
            logger.info("DDNS JOB 执行完毕...");
        } else {
            logger.info("DDNS JOB 执行失败,请查看日志...");
        }
    }
}
