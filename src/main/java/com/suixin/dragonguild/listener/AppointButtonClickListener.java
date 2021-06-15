package com.suixin.dragonguild.listener;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.dragongui.DragonGuildApply;
import com.suixin.dragonguild.dragongui.DragonGuildGui;
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

import java.security.Permission;
import java.util.Date;
import java.util.List;

import static com.suixin.dragonguild.util.PImageUrlEnum.vice_chairman;

public class AppointButtonClickListener implements Listener {
    @EventHandler
    public void buttonClick(CustomPacketEvent event){
        YamlConfiguration systemConfig = DragonGuild.getSystemConfig();
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
        String appointName = buttonEx.getAppointName();
        if (appointName == null) {
            return;
        }
        String type = buttonEx.getType();
        DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(appointName);
        DragonGuildMemberEntity playerDragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
        DragonGuildMemberEntity dragonGuildMemberEntity1 = new DragonGuildMemberEntity();
        String position = dragonGuildMemberEntity.getPosition();
        String position1 = playerDragonGuildMemberEntity.getPosition();
        if (position.equals(systemConfig.getString("DragonGuild.chairman.name", "会长")) && !type.equals("appointButton")) {
            player.sendMessage(Message.no_permission_appoint_chairman);
            return;
        }
        int permissionLevel = 0;
        if (position.equals(systemConfig.getString("DragonGuild.position.chairman.name", "会长"))) {
            permissionLevel = 5;
        }else if (position.equals(systemConfig.getString("DragonGuild.position.vice_chairman.name", "副会长"))) {
            permissionLevel = 4;
        }else if (position.equals(systemConfig.getString("DragonGuild.position.veteran.name", "元老"))) {
            permissionLevel = 3;
        }else if (position.equals(systemConfig.getString("DragonGuild.position.god_of_war.name", "战神"))) {
            permissionLevel = 2;
        }else if (position.equals(systemConfig.getString("DragonGuild.position.elite.name", "精英"))) {
            permissionLevel = 1;
        }

        int permissionLevelOfPlayer = 0;
        if (position1.equals(systemConfig.getString("DragonGuild.position.chairman.name", "会长"))) {
            permissionLevelOfPlayer = 5;
        }else if (position1.equals(systemConfig.getString("DragonGuild.position.vice_chairman.name", "副会长"))) {
            permissionLevelOfPlayer = 4;
        }else if (position1.equals(systemConfig.getString("DragonGuild.position.veteran.name", "元老"))) {
            permissionLevelOfPlayer = 3;
        }else if (position1.equals(systemConfig.getString("DragonGuild.position.god_of_war.name", "战神"))) {
            permissionLevelOfPlayer = 2;
        }else if (position1.equals(systemConfig.getString("DragonGuild.position.elite.name", "精英"))) {
            permissionLevelOfPlayer = 1;
        }
        if (permissionLevelOfPlayer <= permissionLevel) {
            player.sendMessage(Message.no_permission_appoint);
            return;
        }
        if (type.equals("appointButton")) {
            DragonGuildGui.positionButton(player,appointName);
        }else if (type.equals("vice_chairmanButton")){

            String vice_chairman = systemConfig.getString("DragonGuild.position.vice_chairman.name", "副会长");
            dragonGuildMemberEntity1.setPosition(vice_chairman);
            DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity.getId(),dragonGuildMemberEntity1);
            player.sendMessage("§a成功任命§e["+appointName+"]§a为§6"+vice_chairman);
            Player addressee = Bukkit.getServer().getPlayer(appointName);
            if (addressee != null) {
                addressee.sendMessage("§a您被任命为§6"+vice_chairman);
            }
        }else if (type.equals("veteranButton")){
            String veteran = systemConfig.getString("DragonGuild.position.veteran.name", "元老");
            dragonGuildMemberEntity1.setPosition(veteran);
            DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity.getId(),dragonGuildMemberEntity1);
            player.sendMessage("§a成功任命§e["+appointName+"]§a为§6"+veteran);
            Player addressee = Bukkit.getServer().getPlayer(appointName);
            if (addressee != null) {
                addressee.sendMessage("§a您被任命为§6"+veteran);
            }
        }else if (type.equals("god_of_warButton")){
            String god_of_war = systemConfig.getString("DragonGuild.position.god_of_war.name", "战神");
            dragonGuildMemberEntity1.setPosition(god_of_war);
            DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity.getId(),dragonGuildMemberEntity1);
            player.sendMessage("§a成功任命§e["+appointName+"]§a为§6"+god_of_war);
            Player addressee = Bukkit.getServer().getPlayer(appointName);
            if (addressee != null) {
                addressee.sendMessage("§a您被任命为§6"+god_of_war);
            }
        }else if (type.equals("eliteButton")){
            String elite = systemConfig.getString("DragonGuild.position.elite.name", "精英");
            dragonGuildMemberEntity1.setPosition(elite);
            DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity.getId(),dragonGuildMemberEntity1);
            player.sendMessage("§a成功任命§e["+appointName+"]§a为§6"+elite);
            Player addressee = Bukkit.getServer().getPlayer(appointName);
            if (addressee != null) {
                addressee.sendMessage("§a您被任命为§6"+elite);
            }
        }else if (type.equals("ordinaryButton")){
            String ordinary = systemConfig.getString("DragonGuild.position.ordinary.name", "普通成员");
            dragonGuildMemberEntity1.setPosition(ordinary);
            DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity.getId(),dragonGuildMemberEntity1);
            player.sendMessage("§a成功任命§e["+appointName+"]§a为§6"+ordinary);
            Player addressee = Bukkit.getServer().getPlayer(appointName);
            if (addressee != null) {
                addressee.sendMessage("§a您被任命为§6"+ordinary);
            }
        }

    }
}
