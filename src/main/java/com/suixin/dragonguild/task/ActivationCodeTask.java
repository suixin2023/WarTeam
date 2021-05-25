package com.suixin.dragonguild.task;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.util.HttpUtil;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;


public class ActivationCodeTask {
    public static void timeTask (final DragonGuild dragonGuild) {
        new BukkitRunnable() {
            @Override
            public void run() {
                //激活码验证
                String activationCode = DragonGuild.getSystemConfig().getString("ActivationCode",null);
                if (activationCode == null) {
                    //卸载插件
                    Bukkit.getConsoleSender().sendMessage("[DragonGuild] §c激活码未通过验证");
                    Bukkit.getPluginManager().disablePlugin(dragonGuild);
                }
                String res = HttpUtil.getActivationCode(activationCode);
                if (res.equals("noPass")) {
                    //卸载插件
                    Bukkit.getConsoleSender().sendMessage("[DragonGuild] §c激活码未通过验证");
                    Bukkit.getPluginManager().disablePlugin(dragonGuild);
                }
                cancel();  // 终止线程
                return;
            }
        }.runTaskTimer(DragonGuild.getInstance(), 0L, 20L);
        // 插件主类  延时  定时
    }
}
