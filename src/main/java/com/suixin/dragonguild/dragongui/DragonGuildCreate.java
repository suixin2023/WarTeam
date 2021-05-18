package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class DragonGuildCreate {
    //创建GUI
    public static EasyScreen getGui(Integer type) {
        YamlConfiguration mainGui = DragonGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player, Integer type, String playerName) {
        EasyScreen lhdGui = createGui(playerName,type);
        lhdGui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(String playerName,Integer type1) {
        EasyScreen gui = getGui(type1);
        //关闭
        YamlConfiguration close = DragonGuiYml.getClose();
        EasyButton closeButton = new EasyButton( close.getInt("x"), close.getInt("y"), close.getInt("width"), close.getInt("high"), ImageUrlEnum.applyClose.getUrl(), PImageUrlEnum.applyClose.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };
        //取消
        YamlConfiguration cancel = DragonGuiYml.getCancel();
        EasyButton cancelButton = new EasyButton( cancel.getInt("x"), cancel.getInt("y"), cancel.getInt("width"), cancel.getInt("high"), ImageUrlEnum.applyClose.getUrl(), PImageUrlEnum.applyClose.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };

        //确定键
        YamlConfiguration confirm = DragonGuiYml.getConfirm();
        EasyButton confirmButton = new EasyButton( confirm.getInt("x"), confirm.getInt("y"), confirm.getInt("width"), confirm.getInt("high"),  ImageUrlEnum.confirm.getUrl(), PImageUrlEnum.confirm.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                if (type1 == 1){
                    player.chat("/gh create "+playerName);
                }else if (type1 == 2) {
                    player.chat("/gh join " + playerName);
                    DragonGuildGui.openGameLobbyGui(player);
                }
            }
        };
        gui.addComponent(closeButton);
        gui.addComponent(cancelButton);
        gui.addComponent(confirmButton);
        return gui;
    }
}