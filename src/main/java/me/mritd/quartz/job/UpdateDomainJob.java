package me.mritd.quartz.job;

import me.mritd.ddns.UpdateDomainRecord;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

/**
 * DDNS_Tool
 * me.mritd.quartz.job
 * Created by mritd on 16/7/31 14:21.
 */

public class UpdateDomainJob implements Job {


    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        JobDataMap dataMap = jobExecutionContext.getJobDetail().getJobDataMap();


        String myIP = (String) dataMap.get("myIP");
        String domainType = (String) dataMap.get("domainType");
        String domainName = (String) dataMap.get("domain");
        String accessKey = (String) dataMap.get("AccessKey");
        String accessKeySecret = (String) dataMap.get("AccessKeySecret");

        UpdateDomainRecord updateDomainRecord = (UpdateDomainRecord)dataMap.get("updateDomainRecord");

        boolean updateFlag = updateDomainRecord.doUpdate(domainName,domainType,myIP,accessKey,accessKeySecret);

        dataMap.put("updateFlag",updateFlag);


    }
}
