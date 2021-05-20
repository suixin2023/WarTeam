package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import eos.moe.dragoncore.api.easygui.component.EasyTextField;
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
    public static void openGameLobbyGui(Player player, Integer type) {
        EasyScreen lhdGui = createGui(type);
        lhdGui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Integer type1) {
        EasyScreen gui = getGui(type1);
        //排行
        YamlConfiguration top = DragonGuiYml.getTop();
        EasyButton topButton = new EasyButton(top.getInt("x"), top.getInt("y"), top.getInt("width"), top.getInt("high"), ImageUrlEnum.top.getUrl(), PImageUrlEnum.top.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildNoTeam.openGameLobbyGui(player);
            }
        };
        //关闭
        YamlConfiguration close = DragonGuiYml.getClose();
        EasyButton closeButton = new EasyButton( close.getInt("x"), close.getInt("y"), close.getInt("width"), close.getInt("high"), ImageUrlEnum.close.getUrl(), PImageUrlEnum.close.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };
        //取消
        YamlConfiguration cancel = DragonGuiYml.getCancel();
        EasyButton cancelButton = new EasyButton( cancel.getInt("x"), cancel.getInt("y"), cancel.getInt("width"), cancel.getInt("high"), ImageUrlEnum.cancel.getUrl(), PImageUrlEnum.cancel.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };

        //聊天内容
        YamlConfiguration shurukuang = DragonGuiYml.getShurukuang();
        final EasyTextField contentTextField = new EasyTextField(shurukuang.getInt("x"), shurukuang.getInt("y"), shurukuang.getInt("width"));


        //确定键
        YamlConfiguration confirm = DragonGuiYml.getConfirm();
        EasyButton confirmButton = new EasyButton( confirm.getInt("x"), confirm.getInt("y"), confirm.getInt("width"), confirm.getInt("high"),  ImageUrlEnum.confirm.getUrl(), PImageUrlEnum.confirm.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                if (type1 == 1){
                    player.chat("/gh create "+contentTextField.getText());
                }else if (type1 == 2) {
                    player.chat("/gh join " + contentTextField.getText());
                    DragonGuildGui.openGameLobbyGui(player);
                }
            }
        };
        gui.addComponent(topButton);
        gui.addComponent(closeButton);
        gui.addComponent(cancelButton);
        gui.addComponent(confirmButton);
        return gui;
    }
}