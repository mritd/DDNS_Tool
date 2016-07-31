package me.mritd.bean;

/**
 * DDNS_Tool
 * me.mritd.bean
 * Created by mritd on 16/7/31 12:21.
 */

public class AccessBean {

    enum DOMAIN_TYPE {
        Aliyun,Godaddy;
    }

    private String accessKey;
    private String accessSecret;

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getAccessSecret() {
        return accessSecret;
    }

    public void setAccessSecret(String accessSecret) {
        this.accessSecret = accessSecret;
    }
}
