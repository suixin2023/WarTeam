package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import eos.moe.dragoncore.api.easygui.component.EasyTextField;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;


public class DragonGuildWindow {
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
    public static void openGameLobbyGui(Player player, Integer type) {
        EasyScreen lhdGui = createGui(player,type);
        lhdGui.openGui(player);
    }

    //创建VV组件
    public static EasyScreen createGui(Player player,Integer type1) {
        EasyScreen gui = getGui(type1);
        YamlConfiguration shurukuang = DragonGuiYml.getShurukuang();
        final EasyTextField shurukuangTextField = new EasyTextField(shurukuang.getInt("x"), shurukuang.getInt("y"), shurukuang.getInt("width"), "请输入");
        //确定键
        YamlConfiguration confirm = DragonGuiYml.getConfirm();
        EasyButton confirmButton = new EasyButton( confirm.getInt("x"), confirm.getInt("y"), confirm.getInt("width"), confirm.getInt("high"),  ImageUrlEnum.confirm.getUrl(), PImageUrlEnum.confirm.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                String typedText = shurukuangTextField.getText();
                if (type1 == 1){
                    player.chat("/gh create "+typedText);
                }else if (type1 == 2){
                    player.chat("/gh join "+typedText);
                }else if (type1 == 3){
                    DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
                    if (dragonGuildMemberEntity1.getId() == null) {
                        player.sendMessage(Message.not_join_oneTeam);
                        return;
                    }
                    DragonGuildEntity dragonGuildEntity2 = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
                    if (dragonGuildEntity2.getId() == null) {
                        player.sendMessage(Message.no_permission);
                        return;
                    }

                    String teamName = typedText;
                    DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(teamName);
                    if (dragonGuildEntity.getId() != null) {
                        player.sendMessage(Message.update_failure);
                        return;
                    }
                    double money = VaultAPI.getMoney(player.getName());
                    Double amount = new Double(DragonGuild.getSystemConfig().getString("DragonGuild.update_cost"));
                    if (money < amount) {
                        player.sendMessage(DragonGuild.getSystemConfig().getString("DragonGuild.prefix") + "§c您没有足够的金币！");
                        return;
                    }
                    DragonGuildEntity dragonGuild = new DragonGuildEntity();
                    dragonGuild.setName(teamName);
                    DragonGuildDatabaseHandler.updateUserConfigDataNum(dragonGuildEntity2.getId(),dragonGuild);

                    List<DragonGuildMemberEntity> dragonGuildMemberEntities = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByDragonGuildId(dragonGuildEntity2.getId());
                    for (DragonGuildMemberEntity dragonGuildMemberEntity : dragonGuildMemberEntities) {
                        dragonGuildMemberEntity.setDragonGuildName(teamName);
                        DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity.getId(),dragonGuildMemberEntity);
                    }

                    player.sendMessage(Message.update_successful);
                    VaultAPI.removeMoney(player.getName(),amount);
                }else {
                    player.chat("/gh out "+typedText);
                }
                DragonGuildGui.openGameLobbyGui(player);
            }
        };

        gui.addComponent(shurukuangTextField);
        gui.addComponent(confirmButton);
        return gui;
    }
}