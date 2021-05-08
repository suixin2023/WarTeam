package com.suixin.dragonguild.listener;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public final class PlayerJoinEventClass implements Listener {
    public static Map<String, DragonGuildMemberEntity> dragonGuildMemberMap = new HashMap<>();
    HashMap<Integer, Integer> levelMap = DragonGuild.getLevelMap();
    HashMap<Integer, Integer> maxNumMap = DragonGuild.getMaxNumMap();
    @EventHandler(priority = EventPriority.MONITOR)
    public void commandEvent(PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(name);
        if (dragonGuildMemberEntity.getId() != null) {
            dragonGuildMemberEntity.setJoinTime(new Date());
            dragonGuildMemberMap.put(name,dragonGuildMemberEntity);
        }
    }

    /**
     * 玩家退出服务器后计算经验值，不足一小时不计入，升级后溢出部分不计入下一级升级经验，但会计入总经验
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuitEvent(PlayerQuitEvent event) {
        String name = event.getPlayer().getName();
        DragonGuildMemberEntity dragonGuildMemberEntity = dragonGuildMemberMap.get(name);
        if (dragonGuildMemberEntity != null) {
            Date joinTime = dragonGuildMemberEntity.getJoinTime();
            Date outTime = new Date();
            long datePoor = getDatePoor(outTime.getTime(), joinTime.getTime());
            YamlConfiguration systemConfig = DragonGuild.getSystemConfig();
            Long exp = systemConfig.getLong("DragonGuild.exp");
            exp = exp * datePoor;
            int expInt = exp.intValue();
            String dragonGuildName = dragonGuildMemberEntity.getDragonGuildName();
            DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(dragonGuildName);
            if (dragonGuildEntity.getId() != null) {
                DragonGuildEntity dragonGuildEntity1 = new DragonGuildEntity();
                Integer expAll = dragonGuildEntity1.getExpAll();
                dragonGuildEntity1.setExpAll(expAll + expInt);
                Integer expCurrent = dragonGuildEntity1.getExpCurrent();
                if (expInt < expCurrent) {
                    dragonGuildEntity1.setExpCurrent(expCurrent - expInt);
                }else{
                    dragonGuildEntity1.setExpCurrent(expCurrent - expInt);
                    dragonGuildEntity1.setLevel(dragonGuildEntity.getLevel()+1);
                    //升级后最大成员数
                    dragonGuildEntity1.setMaxMember(maxNumMap.get(dragonGuildEntity.getLevel()+1));
                    //升级后下一级所需经验
                    dragonGuildEntity1.setExpCurrent(levelMap.get(dragonGuildEntity.getLevel()+2));
                }
                DragonGuildDatabaseHandler.updateUserConfigDataNum(dragonGuildEntity.getId(),dragonGuildEntity1);
            }
            DragonGuildMemberEntity entity = new DragonGuildMemberEntity();
            entity.setJoinTime(null);
            entity.setExp(dragonGuildMemberEntity.getExp()+expInt);
            DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity.getId(),entity);
        }
    }

    public static long getDatePoor(long endDate, long nowDate) {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate - nowDate;
        // 计算差多少小时
        long hour = diff % nd / nh;
        return hour;
    }
}
