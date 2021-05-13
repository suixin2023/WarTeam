package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.entity.DragonGuildApplyEntity;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import eos.moe.dragoncore.api.easygui.component.EasyComponent;
import eos.moe.dragoncore.api.easygui.component.EasyImage;
import eos.moe.dragoncore.api.easygui.component.EasyLabel;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;


public class DragonGuildNoticeEdit {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration window = DragonGuiYml.getApplyBackground();
        return new EasyScreen(ImageUrlEnum.backgroundOfApply.getUrl(), window.getInt("width"), window.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player,Integer dragonGuildId) {
        EasyScreen gui = createGui(player,dragonGuildId);
        gui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Player player, Integer dragonGuildId) {
        EasyScreen gui = getGui();

        //关闭
        YamlConfiguration applyClose = DragonGuiYml.getApplyClose();
        EasyButton applyCloseButton = new EasyButton( applyClose.getInt("x"), applyClose.getInt("y"), applyClose.getInt("width"), applyClose.getInt("high"), ImageUrlEnum.applyClose.getUrl(), PImageUrlEnum.applyClose.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };
        //确定
        YamlConfiguration xiayiye = DragonGuiYml.getApplyXiayiye();
        EasyButton xiayiyeButton = new EasyButton( xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"),ImageUrlEnum.applyXiayiye.getUrl(), PImageUrlEnum.applyXiayiye.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {

            }
        };

        gui.addComponent(applyCloseButton);
        gui.addComponent(xiayiyeButton);

        return gui;
    }

}