package me.mritd.ddns;

/**
 * DDNS_Tool
 * me.mritd.ddns
 * Created by mritd on 16/7/31 16:09.
 */

public interface UpdateDomainRecord {

    /**
     * 更新 域名解析 记录
     * @param domain 更新域名
     * @param type 域名类型
     * @param ip 更新IP
     * @param accessKey key
     * @param accessKeySecret keySecret
     * @return boolean 更新结果
     */
    boolean doUpdate(String domain, String pr,String type, String ip, String accessKey, String accessKeySecret);

}
