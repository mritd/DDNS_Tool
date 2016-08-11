package me.mritd.ddns.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.alidns.model.v20150109.*;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import me.mritd.ddns.UpdateDomainRecord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * DDNS_Tool
 * me.mritd.ddns.impl
 * Created by mritd on 16/7/31 16:11.
 */

public class AliyunUpdateDomainRecord implements UpdateDomainRecord {

    private static final Logger logger = LoggerFactory.getLogger(AliyunUpdateDomainRecord.class);

    @Override
    public boolean doUpdate(String domain, String type, String ip, String accessKey, String accessKeySecret) {

        // 阿里云 API 固定值
        String regionId = "cn-hangzhou";
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessKeySecret);
        // 若报Can not find endpoint to access异常，请添加以下此行代码
        // DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Alidns", "alidns.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);

        logger.info("创建域名解析请求...");

        DescribeDomainRecordsRequest request = new DescribeDomainRecordsRequest();
        DescribeDomainRecordsResponse response ;
        request.setDomainName(domain);
        request.setProtocol(ProtocolType.HTTPS); //指定访问协议
        request.setAcceptFormat(FormatType.JSON); //指定api返回格式
        request.setMethod(MethodType.POST); //指定请求方法

        logger.info("创建域名解析更新请求...");

        UpdateDomainRecordRequest updateRequest = new UpdateDomainRecordRequest();
        UpdateDomainRecordResponse updateResponse;
        updateRequest.setProtocol(ProtocolType.HTTPS);
        updateRequest.setAcceptFormat(FormatType.JSON);
        updateRequest.setMethod(MethodType.POST);
        updateRequest.setRR(domain);
        updateRequest.setType(type);

        try {
            logger.info("查询域名解析状态...");
            response = client.getAcsResponse(request);
            List<DescribeDomainRecordsResponse.Record> records = response.getDomainRecords();
            for (DescribeDomainRecordsResponse.Record record : records) {

                logger.info("当前域名解析 IP 为: " + record.getValue());
                logger.info("当前本地 IP 为: " + ip);
                if (!ip.equals(record.getValue())) {
                    logger.info("本地 IP 发生变动,即将更新域名解析!");

                    updateRequest.setRecordId(record.getRecordId());
                    updateRequest.setValue(ip);

                    updateResponse = client.getAcsResponse(updateRequest);

                    if (StringUtils.isNotBlank(updateResponse.getRecordId())) {
                        logger.info("更新域名解析记录成功,解析 ID: " + updateResponse.getRecordId());
                        return true;
                    } else {
                        logger.error("更新域名解析记录失败!");
                        return false;
                    }

                } else {
                    logger.info("本地 IP 未发生变动...");
                    return true;
                }

            }

        } catch (ServerException e) {
            logger.error("更新域名解析记录 服务器端 出现异常: ", e);
        } catch (ClientException e) {
            logger.error("更新域名解析记录 客户端 出现异常: ", e);
        }

        return false;
    }
}
