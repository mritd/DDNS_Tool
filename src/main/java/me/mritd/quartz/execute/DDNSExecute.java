package me.mritd.quartz.execute;

import me.mritd.quartz.job.UpdateDomainJob;
import me.mritd.quartz.listener.UpdateDomainListener;
import me.mritd.quartz.vo.QuartzInfo;
import org.quartz.*;
import org.quartz.impl.matchers.KeyMatcher;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * DDNS_Tool
 * me.mritd.quartz.execute
 * Created by mritd on 16/7/31 14:18.
 */

public class DDNSExecute {

    private static final Logger logger = LoggerFactory.getLogger(DDNSExecute.class);
    

    public boolean exec(QuartzInfo quartzInfo){

        logger.info("启动 DDNS 定时更新...");

        Scheduler scheduler ;
        JobDetail job = JobBuilder.newJob(UpdateDomainJob.class).withIdentity(quartzInfo.getJobKey()).build();

        //创建触发器
        Trigger trigger = TriggerBuilder
                .newTrigger()
                .withIdentity(quartzInfo.getTiggerKey())
                .withSchedule(CronScheduleBuilder.cronSchedule(quartzInfo.getCron()))
                .build();

        // 创建监听器
        UpdateDomainListener listener = new UpdateDomainListener();


        try {
            // 创建调度器
            scheduler = new StdSchedulerFactory().getScheduler();
            // 加入任务和其匹配的触发器
            scheduler.scheduleJob(job, trigger);
            // 添加监听器
            scheduler.getListenerManager().addJobListener(listener, KeyMatcher.keyEquals(quartzInfo.getJobKey()));
            scheduler.start();
        } catch (SchedulerException e) {
            logger.error("DDNS 定时任务 调度器创建失败!");
            logger.error("异常信息: ", e);
            return false;
        }

        logger.info("DDNS 定时任务启动成功!");
        return true;

    }

}
