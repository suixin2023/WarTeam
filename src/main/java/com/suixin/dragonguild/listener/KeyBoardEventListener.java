package com.suixin.dragonguild.listener;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.dragongui.DragonGuildGui;
import com.suixin.dragonguild.dragongui.DragonGuildNoTeam;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.util.KeyConversion;
import eos.moe.dragoncore.api.CoreAPI;
import eos.moe.dragoncore.api.KeyPressEvent;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;


public class KeyBoardEventListener implements Listener {
    @EventHandler
    public void onKey(KeyPressEvent e){
        YamlConfiguration systemConfig = DragonGuild.getSystemConfig();
        String hotkeys = systemConfig.getString("hotkeys").toLowerCase();
        String key = e.getKey().toLowerCase();
        if (hotkeys.equals(key)) {
            EasyScreen openedScreen = EasyScreen.getOpenedScreen(e.getPlayer());
            if (openedScreen == null) {
                DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(e.getPlayer().getName());
                if (dragonGuildMemberEntity.getId() == null) {
                    DragonGuildNoTeam.openGameLobbyGui(e.getPlayer());
                }else {
                    DragonGuildGui.openGameLobbyGui(e.getPlayer());
                }
            }else {
                e.getPlayer().closeInventory();
            }
        }
    }
}
