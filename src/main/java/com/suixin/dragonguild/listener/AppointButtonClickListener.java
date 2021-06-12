package com.suixin.dragonguild.listener;

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
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Date;
import java.util.List;

public class AppointButtonClickListener implements Listener {
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
        String appointName = buttonEx.getApplyName();
        String type = buttonEx.getType();
        if (type.equals("appointButton")) {
            DragonGuildGui.positionButton(player,appointName);
        }else if (type.equals("vice_chairmanButton")){
            //todo
        }else if (type.equals("noticeButton")){

        }else if (type.equals("god_of_warButton")){

        }else if (type.equals("eliteButton")){

        }else if (type.equals("ordinaryButton")){

        }

    }
}
