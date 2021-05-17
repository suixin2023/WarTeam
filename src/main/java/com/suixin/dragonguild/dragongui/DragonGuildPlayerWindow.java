package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class DragonGuildPlayerWindow {
    //创建GUI
    public static EasyScreen getGui(Integer type) {
        String url = "";
        if (type == 4) {
            url =  ImageUrlEnum.window2.getUrl();
        }else{
            url =  ImageUrlEnum.window.getUrl();
        }
        YamlConfiguration window = DragonGuiYml.getWindow();
        return new EasyScreen(url, window.getInt("width"), window.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player, Integer type, String playerName) {
        EasyScreen lhdGui = createGui(playerName,type);
        lhdGui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(String playerName,Integer type1) {
        EasyScreen gui = getGui(type1);
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

        gui.addComponent(confirmButton);
        return gui;
    }
}