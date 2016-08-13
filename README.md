## DDNS_Tool

> 基于域名服务商的 DDNS 工具，目前由于在国内所以首先完成阿里云域名解析记录修改API的对接，
> 其次考虑 Godaddy。由于准备入手一台 NAS，并且大天朝全部是动态 IP，故写了一个 DDNS 的工具，
> 准备放在树莓派2上跑(终于不用吃灰了)，花生壳不花钱很不稳定，感觉还是自己动手来的实在.

**项目 `dist` 目录下存在编译完的可执行jar和样例配置文件,DDNS 解析记录更新周期由配置文件中
的 `cron` 表达式决定，运行截图如下:**

![DDNS_TOOL](http://cnd.mritd.me/markdown/DDNS_TOOL.png)


