package com.suixin.warteam.dragongui;

import com.suixin.warteam.WarTeam;
import com.suixin.warteam.entity.WarTeamEntity;
import com.suixin.warteam.entity.WarTeamMemberEntity;
import com.suixin.warteam.handler.WarTeamDatabaseHandler;
import com.suixin.warteam.handler.WarTeamMemBerDatabaseHandler;
import com.suixin.warteam.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import eos.moe.dragoncore.api.easygui.component.EasyTextField;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;


public class WarTeamWindow {
    //创建GUI
    public static EasyScreen getGui(Integer type) {
        String url = "";
        if (type == 4) {
            url =  ImageUrlEnum.window2.getUrl();
        }else{
            url =  ImageUrlEnum.window.getUrl();
        }
        YamlConfiguration window = VvGuiYml.getWindow();
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
        YamlConfiguration shurukuang = VvGuiYml.getShurukuang();
        final EasyTextField shurukuangTextField = new EasyTextField(shurukuang.getInt("x"), shurukuang.getInt("y"), shurukuang.getInt("width"), "请输入");
        //确定键
        YamlConfiguration confirm = VvGuiYml.getConfirm();
        EasyButton confirmButton = new EasyButton( confirm.getInt("x"), confirm.getInt("y"), confirm.getInt("width"), confirm.getInt("high"),  ImageUrlEnum.confirm.getUrl(), PImageUrlEnum.confirm.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                String typedText = shurukuangTextField.getText();
                if (type1 == 1){
                    player.chat("/wt create "+typedText);
                }else if (type1 == 2){
                    player.chat("/wt join "+typedText);
                }else if (type1 == 3){
                    WarTeamMemberEntity warTeamMemberEntity1 = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByUid(player.getName());
                    if (warTeamMemberEntity1.getId() == null) {
                        player.sendMessage(Message.not_join_oneTeam);
                        return;
                    }
                    WarTeamEntity warTeamEntity2 = WarTeamDatabaseHandler.selectWarTeamByCreator(player.getName());
                    if (warTeamEntity2.getId() == null) {
                        player.sendMessage(Message.no_permission);
                        return;
                    }

                    String teamName = typedText;
                    WarTeamEntity warTeamEntity = WarTeamDatabaseHandler.selectWarTeamByName(teamName);
                    if (warTeamEntity.getId() != null) {
                        player.sendMessage(Message.update_failure);
                        return;
                    }
                    double money = VaultAPI.getMoney(player.getName());
                    Double amount = new Double(WarTeam.getSystemConfig().getString("WarTeam.update_cost"));
                    if (money < amount) {
                        player.sendMessage(WarTeam.getSystemConfig().getString("WarTeam.prefix") + "§c您没有足够的金币！");
                        return;
                    }
                    WarTeamEntity warTeam = new WarTeamEntity();
                    warTeam.setName(teamName);
                    WarTeamDatabaseHandler.updateUserConfigDataNum(warTeamEntity2.getId(),warTeam);

                    List<WarTeamMemberEntity> warTeamMemberEntities = WarTeamMemBerDatabaseHandler.selectWarTeamMemBerByWarTeamId(warTeamEntity2.getId());
                    for (WarTeamMemberEntity warTeamMemberEntity : warTeamMemberEntities) {
                        warTeamMemberEntity.setWarTeamName(teamName);
                        WarTeamMemBerDatabaseHandler.updateUserConfigDataNum(warTeamMemberEntity.getId(),warTeamMemberEntity);
                    }

                    player.sendMessage(Message.update_successful);
                    VaultAPI.removeMoney(player.getName(),amount);
                }else {
                    player.chat("/wt out "+typedText);
                }
                WarTeamGui.openGameLobbyGui(player);
            }
        };

        gui.addComponent(shurukuangTextField);
        gui.addComponent(confirmButton);
        return gui;
    }
}