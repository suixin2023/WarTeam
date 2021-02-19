package com.suixin.warteam.listener;

import com.suixin.warteam.WarTeam;
import com.suixin.warteam.entity.WarTeamEntity;
import com.suixin.warteam.entity.WarTeamMemberEntity;
import com.suixin.warteam.handler.WarTeamDatabaseHandler;
import com.suixin.warteam.handler.WarTeamMemBerDatabaseHandler;
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
    public static Map<String, WarTeamMemberEntity> warTeamMemberMap = new HashMap<>();
    HashMap<Integer, Integer> levelMap = WarTeam.getLevelMap();
    HashMap<Integer, Integer> maxNumMap = WarTeam.getMaxNumMap();
    @EventHandler(priority = EventPriority.MONITOR)
    public void commandEvent(PlayerJoinEvent event) {
        String name = event.getPlayer().getName();
        WarTeamMemberEntity warTeamMemberEntity = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByUid(name);
        if (warTeamMemberEntity.getId() != null) {
            warTeamMemberEntity.setJoinTime(new Date());
            warTeamMemberMap.put(name,warTeamMemberEntity);
        }
    }

    /**
     * 玩家退出服务器后计算经验值，不足一小时不计入，升级后溢出部分不计入下一级升级经验，但会计入总经验
     * @param event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void playerQuitEvent(PlayerQuitEvent event) {
        String name = event.getPlayer().getName();
        WarTeamMemberEntity warTeamMemberEntity = warTeamMemberMap.get(name);
        if (warTeamMemberEntity != null) {
            Date joinTime = warTeamMemberEntity.getJoinTime();
            Date outTime = new Date();
            long datePoor = getDatePoor(outTime.getTime(), joinTime.getTime());
            YamlConfiguration systemConfig = WarTeam.getSystemConfig();
            Long exp = systemConfig.getLong("WarTeam.exp");
            exp = exp * datePoor;
            int expInt = exp.intValue();
            String warTeamName = warTeamMemberEntity.getWarTeamName();
            WarTeamEntity warTeamEntity = WarTeamDatabaseHandler.selectWarTeamByName(warTeamName);
            if (warTeamEntity.getId() != null) {
                WarTeamEntity warTeamEntity1 = new WarTeamEntity();
                Integer expAll = warTeamEntity1.getExpAll();
                warTeamEntity1.setExpAll(expAll + expInt);
                Integer expCurrent = warTeamEntity1.getExpCurrent();
                if (expInt < expCurrent) {
                    warTeamEntity1.setExpCurrent(expCurrent - expInt);
                }else{
                    warTeamEntity1.setExpCurrent(expCurrent - expInt);
                    warTeamEntity1.setLevel(warTeamEntity.getLevel()+1);
                    //升级后最大成员数
                    warTeamEntity1.setMaxMember(maxNumMap.get(warTeamEntity.getLevel()+1));
                    //升级后下一级所需经验
                    warTeamEntity1.setExpCurrent(levelMap.get(warTeamEntity.getLevel()+2));
                }
                WarTeamDatabaseHandler.updateUserConfigDataNum(warTeamEntity.getId(),warTeamEntity1);
            }
            WarTeamMemberEntity entity = new WarTeamMemberEntity();
            entity.setJoinTime(null);
            entity.setExp(warTeamMemberEntity.getExp()+expInt);
            WarTeamMemBerDatabaseHandler.updateUserConfigDataNum(warTeamMemberEntity.getId(),entity);
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
