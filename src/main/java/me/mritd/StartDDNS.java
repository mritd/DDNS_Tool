package me.mritd;

import me.mritd.quartz.execute.DDNSExecute;
import me.mritd.quartz.vo.QuartzInfo;
import me.mritd.utils.PropertiesUtil;

/**
 * DDNS_Tool
 * me.mritd
 * Created by mritd on 16/7/31 16:47.
 */

public class StartDDNS {



    public static void main(String[] args) {

        if (args.length==2){
            PropertiesUtil.setConfPath(args);
        }

        QuartzInfo quartzInfo = new QuartzInfo();
        quartzInfo.setCron(PropertiesUtil.loadConfig().getProperty("cron"));
        quartzInfo.setJobGroup("DDNS_GROUP");
        quartzInfo.setJobName("DDNS_JOB");
        quartzInfo.setTiggerGroup("DDNS_GROUP");
        quartzInfo.setTiggerName("DDNS_TRIGGER");

        new DDNSExecute().exec(quartzInfo);
    }
}
