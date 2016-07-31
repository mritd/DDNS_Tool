package me.mritd.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Properties;

/**
 * DDNS_Tool
 * me.mritd.utils
 * Created by mritd on 16/7/31 12:50.
 */

public class PropertiesUtil {

    private static String confPath="dns.properties";
    private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);


    public static Properties loadConfig() {

        File configFile = new File(confPath);
        Properties properties = new Properties();
        InputStream is = null;


        try {
            is = new FileInputStream(configFile);
            properties.load(is);
        } catch (FileNotFoundException e) {
            logger.error("FileNotFoundException: ",e);
        } catch (IOException e) {
            logger.error("IOException: ",e);
        } finally {
            try {
                if(is!=null) {
                    is.close();
                }
            } catch (IOException e) {
                logger.error("资源释放失败: ",e);
            }
        }


        return properties;
    }


    /**
     * 根据 key 获取 value
     * @param key
     * @return
     */
    public static String getValue(String key){

        return loadConfig().getProperty(key);
    }


    /**
     * 设置 配置文件路径
     * @param args
     */
    public static void setConfPath(String args[]) {
        // 参数检查
        if(args.length >= 2){
            for (int i =0 ;i<args.length;i++) {
                if("-c".equals(args[i])&&i+1<=args.length){
                    confPath=args[i+1];
                }
            }
        }
    }
}
