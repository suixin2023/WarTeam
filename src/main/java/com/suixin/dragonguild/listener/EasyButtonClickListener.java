package com.suixin.dragonguild.listener;

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
            DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUidAndApply(applyUserName,player.getName());
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
        }else {
            //申请加入
            String guildName = buttonEx.getGuildName();
            player.chat("/gh join " + guildName);
        }

    }
}
