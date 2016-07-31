package me.mritd.quartz.vo;

import org.quartz.JobKey;
import org.quartz.TriggerKey;

/**
 * DDNS_Tool
 * me.mritd.quartz.vo
 * Created by mritd on 16/7/31 16:56.
 */

public class QuartzInfo {

    //cron定时表达式
    private String cron;
    //任务名称
    private String jobName;
    //任务组
    private String jobGroup;
    //触发器名称
    private String tiggerName;
    //触发器组
    private String tiggerGroup;
    //任务key
    private JobKey jobKey;
    //触发器key
    private TriggerKey tiggerKey;



    public String getCron() {
        return cron;
    }
    public void setCron(String cron) {
        this.cron = cron;
    }
    public String getJobName() {
        return jobName;
    }
    public void setJobName(String jobName) {
        this.jobName = jobName;
    }
    public String getJobGroup() {
        return jobGroup;
    }
    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }
    public String getTiggerName() {
        return tiggerName;
    }
    public void setTiggerName(String tiggerName) {
        this.tiggerName = tiggerName;
    }
    public String getTiggerGroup() {
        return tiggerGroup;
    }
    public void setTiggerGroup(String tiggerGroup) {
        this.tiggerGroup = tiggerGroup;
    }
    public JobKey getJobKey() {
        this.jobKey = new JobKey(jobName, jobGroup);
        return jobKey;
    }
    public TriggerKey getTiggerKey() {
        this.tiggerKey = new TriggerKey(tiggerName, tiggerGroup);
        return tiggerKey;
    }
    public QuartzInfo() {
        super();
    }
    public QuartzInfo(String cron, String jobName, String jobGroup, String tiggerName, String tiggerGroup) {
        super();
        this.cron = cron;
        this.jobName = jobName;
        this.jobGroup = jobGroup;
        this.tiggerName = tiggerName;
        this.tiggerGroup = tiggerGroup;
    }
    @Override
    public String toString() {
        return "QuartzInfo [cron=" + cron + ", jobName=" + jobName + ", jobGroup=" + jobGroup + ", tiggerName="
                + tiggerName + ", tiggerGroup=" + tiggerGroup + ", jobKey=" + jobKey + ", tiggerKey=" + tiggerKey + "]";
    }
}
