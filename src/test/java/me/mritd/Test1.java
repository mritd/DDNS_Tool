package me.mritd;

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
import me.mritd.utils.PropertiesUtil;

import java.util.List;

/**
 * DDNS_Tool
 * me.mritd
 * Created by mritd on 16/7/31 13:03.
 */

public class Test1 {
    public static void main(String[] args) {
        String cfg[] = {"-c","/Users/mritd/tmp/dns.properties"};
        PropertiesUtil.setConfPath(cfg);


        // 阿里云 API 固定值
        String regionId = "cn-hangzhou"; //必填固定值，必须为“cn-hanghou”
        String myIP = "114.114.114.114";
        String domainName = PropertiesUtil.getValue("domain");
        String accessKey = PropertiesUtil.getValue("Aliyun_AccessKey");
        String accessKeySecret = PropertiesUtil.getValue("Aliyun_AccessKeySecret");
        IClientProfile profile = DefaultProfile.getProfile(regionId, accessKey, accessKeySecret);
        // 若报Can not find endpoint to access异常，请添加以下此行代码
        // DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", "Alidns", "alidns.aliyuncs.com");
        IAcsClient client = new DefaultAcsClient(profile);


        DescribeSubDomainRecordsRequest request = new DescribeSubDomainRecordsRequest();
        DescribeSubDomainRecordsResponse response;
        request.setProtocol(ProtocolType.HTTPS); //指定访问协议
        request.setAcceptFormat(FormatType.JSON); //指定api返回格式
        request.setSubDomain(domainName);
        request.setMethod(MethodType.POST); //指定请求方法

        UpdateDomainRecordRequest updateRequest = new UpdateDomainRecordRequest();
        UpdateDomainRecordResponse updateResponse;
        updateRequest.setProtocol(ProtocolType.HTTPS);
        updateRequest.setAcceptFormat(FormatType.JSON);
        updateRequest.setMethod(MethodType.POST);
        updateRequest.setRR(domainName);
        updateRequest.setType("A");

        try {
            response = client.getAcsResponse(request);
            List<DescribeSubDomainRecordsResponse.Record> records = response.getDomainRecords();
            for (DescribeSubDomainRecordsResponse.Record record:records) {

                if (!myIP.equals(record.getValue())){
                    updateRequest.setRecordId(record.getRecordId());
                    updateRequest.setValue(myIP);

                    updateResponse = client.getAcsResponse(updateRequest);

                    System.out.println(updateResponse.getRecordId());

                }

            }

        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }






    }
}
