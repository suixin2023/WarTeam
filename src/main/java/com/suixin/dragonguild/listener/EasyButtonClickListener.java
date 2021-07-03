package com.suixin.dragonguild.listener;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.dragongui.DragonGuildApply;
import com.suixin.dragonguild.entity.DragonGuildApplyEntity;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.entity.EasyButtonEx;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.util.Message;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyComponent;
import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Date;
import java.util.List;

public class EasyButtonClickListener implements Listener {
    @EventHandler
    public void buttonClick(CustomPacketEvent event){
        Player player = event.getPlayer();
        if (!"EasyScreenEvent".equals(event.getIdentifier())) return;
        List<String> data = event.getData();
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        if (openedScreen == null) {
            return;
        }
        EasyComponent component = openedScreen.getComponentById(data.get(0));
        if (!(component instanceof EasyButtonEx)) {
            return;
        }
        EasyButtonEx buttonEx = (EasyButtonEx) component;
        String applyUserName = buttonEx.getApplyName();
        String type = buttonEx.getType();
        if (type.equals("applyAgreeButton")) {
            DragonGuildMemberEntity dragonGuildMemberEntity2 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
            if (dragonGuildMemberEntity2.getId() == null) {
                player.sendMessage(Message.create_first);
                return;
            }
            YamlConfiguration systemConfig = DragonGuild.getSystemConfig();
            String position1 = dragonGuildMemberEntity2.getPosition();
            int hasPermission = 0;
            if (position1 == null) {
                dragonGuildMemberEntity2.setPosition(systemConfig.getString("DragonGuild.position.ordinary.name", "普通成员"));
                DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity2.getId(),dragonGuildMemberEntity2);
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.chairman.name", "会长"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.chairman.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.vice_chairman.name", "副会长"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.vice_chairman.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.veteran.name", "元老"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.veteran.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.god_of_war.name", "战神"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.god_of_war.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.elite.name", "精英"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.elite.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }
            if (hasPermission == 0) {
                player.sendMessage(Message.no_permission_apply);
                return;
            }
            DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUidAndApply(applyUserName,dragonGuildMemberEntity2.getDragonGuildId());
            DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(applyUserName);
            if (dragonGuildMemberEntity1.getId() != null) {
                player.sendMessage(Message.apply_join);
                //玩家加入其他公会后，此申请自动拒绝
                dragonGuildApplyEntity.setStatus(2);
                DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
                return;
            }
            if (dragonGuildApplyEntity.getId() == null) {
                player.sendMessage(Message.apply_inexistence);
                return;
            }
            DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildById(dragonGuildApplyEntity.getDragonGuildId());
            DragonGuildMemberEntity dragonGuildMemberEntity = new DragonGuildMemberEntity();
            dragonGuildMemberEntity.setDragonGuildId(dragonGuildEntity.getId());
            dragonGuildMemberEntity.setDragonGuildName(dragonGuildEntity.getName());
            dragonGuildMemberEntity.setPosition(systemConfig.getString("DragonGuild.ordinary.name", "普通成员"));
            dragonGuildMemberEntity.setExp(0);
            dragonGuildMemberEntity.setUid(dragonGuildApplyEntity.getUid());
            dragonGuildMemberEntity.setStatus(1);
            dragonGuildMemberEntity.setCreated(new Date());
            DragonGuildMemBerDatabaseHandler.insert(dragonGuildMemberEntity);
            dragonGuildEntity.setMaxMember(dragonGuildEntity.getMaxMember() + 1);
            DragonGuildDatabaseHandler.updateUserConfigDataNum(dragonGuildEntity.getId(),dragonGuildEntity);
            dragonGuildApplyEntity.setStatus(1);
            DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
            Player addressee = Bukkit.getServer().getPlayer(applyUserName);
            if (addressee != null) {
                addressee.sendMessage(Message.join_successful);
            }
            player.sendMessage(Message.apply_pass);
            DragonGuildApply.openGameLobbyGui(player,dragonGuildApplyEntity.getDragonGuildId());
        }else if (type.equals("applyRepulseButton")){
            DragonGuildMemberEntity dragonGuildMemberEntity2 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
            if (dragonGuildMemberEntity2.getId() == null) {
                player.sendMessage(Message.create_first);
                return;
            }
            YamlConfiguration systemConfig = DragonGuild.getSystemConfig();
            String position1 = dragonGuildMemberEntity2.getPosition();
            int hasPermission = 0;
            if (position1 == null) {
                dragonGuildMemberEntity2.setPosition(systemConfig.getString("DragonGuild.position.ordinary.name", "普通成员"));
                DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity2.getId(),dragonGuildMemberEntity2);
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.chairman.name", "会长"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.chairman.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.vice_chairman.name", "副会长"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.vice_chairman.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.veteran.name", "元老"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.veteran.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.god_of_war.name", "战神"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.god_of_war.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }else if (position1.equals(systemConfig.getString("DragonGuild.position.elite.name", "精英"))) {
                boolean aBoolean = systemConfig.getBoolean("DragonGuild.position.elite.permission.apply", false);
                if (aBoolean) {
                    hasPermission = 1;
                }
            }
            if (hasPermission == 0) {
                player.sendMessage(Message.no_permission_apply);
                return;
            }
            DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUidAndApply(applyUserName,dragonGuildMemberEntity2.getDragonGuildId());
            DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(applyUserName);
            if (dragonGuildMemberEntity1.getId() != null) {
                player.sendMessage(Message.apply_join);
                //玩家加入其他公会后，此申请自动拒绝
                dragonGuildApplyEntity.setStatus(2);
                DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
                return;
            }
            if (dragonGuildApplyEntity.getId() == null) {
                player.sendMessage(Message.apply_inexistence);
                return;
            }
            dragonGuildApplyEntity.setStatus(2);
            DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
            Player addressee = Bukkit.getServer().getPlayer(applyUserName);
            if (addressee != null) {
                addressee.sendMessage(Message.join_failure);
            }
            player.sendMessage(Message.apply_nopass);
            DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
            DragonGuildApply.openGameLobbyGui(player,dragonGuildMemberEntity.getDragonGuildId());
        }else if (type.equals("joinApplyButton")) {
            //申请加入
            String guildName = buttonEx.getGuildName();
            player.chat("/gh join " + guildName);
        }

    }
}
