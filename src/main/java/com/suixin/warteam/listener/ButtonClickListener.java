package com.suixin.warteam.listener;

import com.suixin.warteam.entity.WarTeamApplyEntity;
import com.suixin.warteam.entity.WarTeamEntity;
import com.suixin.warteam.entity.WarTeamMemberEntity;
import com.suixin.warteam.handler.WarTeamApplyDatabaseHandler;
import com.suixin.warteam.handler.WarTeamDatabaseHandler;
import com.suixin.warteam.handler.WarTeamMemBerDatabaseHandler;
import com.suixin.warteam.util.Message;
import lk.vexview.event.ButtonClickEvent;
import lk.vexview.gui.components.VexButton;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Date;

public class ButtonClickListener implements Listener {
    @EventHandler
    public void buttonClick(ButtonClickEvent e){
        VexButton button = e.getButton();
        String id = (String) button.getId();
        String[] split = id.split("#");
        String applyUserName = null;
        String buttonId = null;
        if (split.length > 2) {
            buttonId = split[0];
            applyUserName = split[1];
        }else {
            return;
        }
        Player player = e.getPlayer();
        if (buttonId.equals("applyAgreeButton")) {
            WarTeamApplyEntity warTeamApplyEntity = WarTeamApplyDatabaseHandler.selectWarTeamApplyByUidAndApply(applyUserName,player.getName());
            WarTeamMemberEntity warTeamMemberEntity1 = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByUid(applyUserName);
            if (warTeamMemberEntity1.getId() != null) {
                player.sendMessage(Message.apply_join);
                //玩家加入其他战队后，此申请自动拒绝
                warTeamApplyEntity.setStatus(2);
                WarTeamApplyDatabaseHandler.updateUserConfigDataNum(warTeamApplyEntity.getId(),warTeamApplyEntity);
                return;
            }
            WarTeamEntity warTeamEntity = WarTeamDatabaseHandler.selectWarTeamByName(warTeamApplyEntity.getWarTeamName());

            WarTeamMemberEntity warTeamMemberEntity = new WarTeamMemberEntity();
            warTeamMemberEntity.setWarTeamId(warTeamEntity.getId());
            warTeamMemberEntity.setWarTeamName(warTeamEntity.getName());
            warTeamMemberEntity.setExp(0);
            warTeamMemberEntity.setUid(warTeamApplyEntity.getUid());
            warTeamMemberEntity.setStatus(1);
            warTeamMemberEntity.setCreated(new Date());
            WarTeamMemBerDatabaseHandler.insert(warTeamMemberEntity);
            warTeamEntity.setMaxMember(warTeamEntity.getMaxMember() + 1);
            WarTeamDatabaseHandler.updateUserConfigDataNum(warTeamEntity.getId(),warTeamEntity);
            warTeamApplyEntity.setStatus(1);
            WarTeamApplyDatabaseHandler.updateUserConfigDataNum(warTeamApplyEntity.getId(),warTeamApplyEntity);
            Player addressee = Bukkit.getServer().getPlayer(applyUserName);
            if (addressee != null) {
                addressee.sendMessage(Message.join_successful);
            }
            player.sendMessage(Message.apply_pass);
        }else {

            WarTeamApplyEntity warTeamApplyEntity = WarTeamApplyDatabaseHandler.selectWarTeamApplyByUidAndApply(applyUserName,player.getName());
            WarTeamMemberEntity warTeamMemberEntity1 = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByUid(applyUserName);
            if (warTeamMemberEntity1.getId() != null) {
                player.sendMessage(Message.apply_join);
                //玩家加入其他战队后，此申请自动拒绝
                warTeamApplyEntity.setStatus(2);
                WarTeamApplyDatabaseHandler.updateUserConfigDataNum(warTeamApplyEntity.getId(),warTeamApplyEntity);
                return;
            }
            warTeamApplyEntity.setStatus(2);
            WarTeamApplyDatabaseHandler.updateUserConfigDataNum(warTeamApplyEntity.getId(),warTeamApplyEntity);
            Player addressee = Bukkit.getServer().getPlayer(applyUserName);
            if (addressee != null) {
                addressee.sendMessage(Message.join_failure);
            }
            player.sendMessage(Message.apply_nopass);
        }

    }
}
