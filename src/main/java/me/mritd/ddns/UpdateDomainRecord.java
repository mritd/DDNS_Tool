package me.mritd.ddns;

/**
 * DDNS_Tool
 * me.mritd.ddns
 * Created by mritd on 16/7/31 16:09.
 */

public interface UpdateDomainRecord {

    /**
     * 更新 域名解析 记录
     * @param domain
     * @param type
     * @param ip
     * @param accessKey
     * @param accessKeySecret
     * @return
     */
    public boolean doUpdate(String domain,String type , String ip ,String accessKey, String accessKeySecret);

}
