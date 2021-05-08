package com.suixin.dragonguild.listener;

import com.suixin.dragonguild.dragongui.DragonGuildApply;
import com.suixin.dragonguild.entity.DragonGuildApplyEntity;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.util.Message;
import eos.moe.dragoncore.api.gui.event.CustomPacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Date;

public class EasyButtonClickListener implements Listener {
    @EventHandler
    public void buttonClick(CustomPacketEvent event){
        String id = event.getIdentifier();
        String[] split = id.split("#");
        String applyUserName = null;
        String buttonId = null;
        if (split.length > 1) {
            buttonId = split[0];
            applyUserName = split[1];
        }else {
            return;
        }
        Player player = event.getPlayer();
        if (buttonId.equals("applyAgreeButton")) {
            DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUidAndApply(applyUserName,player.getName());
            DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(applyUserName);
            if (dragonGuildMemberEntity1.getId() != null) {
                player.sendMessage(Message.apply_join);
                //玩家加入其他公会后，此申请自动拒绝
                dragonGuildApplyEntity.setStatus(2);
                DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
                return;
            }
            DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(dragonGuildApplyEntity.getDragonGuildName());

            DragonGuildMemberEntity dragonGuildMemberEntity = new DragonGuildMemberEntity();
            dragonGuildMemberEntity.setDragonGuildId(dragonGuildEntity.getId());
            dragonGuildMemberEntity.setDragonGuildName(dragonGuildEntity.getName());
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
        }else {

            DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUidAndApply(applyUserName,player.getName());
            DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(applyUserName);
            if (dragonGuildMemberEntity1.getId() != null) {
                player.sendMessage(Message.apply_join);
                //玩家加入其他公会后，此申请自动拒绝
                dragonGuildApplyEntity.setStatus(2);
                DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
                return;
            }
            dragonGuildApplyEntity.setStatus(2);
            DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
            Player addressee = Bukkit.getServer().getPlayer(applyUserName);
            if (addressee != null) {
                addressee.sendMessage(Message.join_failure);
            }
            player.sendMessage(Message.apply_nopass);
            DragonGuildApply.openGameLobbyGui(player,dragonGuildApplyEntity.getDragonGuildId());
        }

    }
}
