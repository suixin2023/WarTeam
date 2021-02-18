package com.suixin.warteam.gui;

import com.suixin.warteam.WarTeam;
import com.suixin.warteam.entity.WarTeamEntity;
import com.suixin.warteam.entity.WarTeamMemberEntity;
import com.suixin.warteam.handler.WarTeamDatabaseHandler;
import com.suixin.warteam.handler.WarTeamMemBerDatabaseHandler;
import com.suixin.warteam.util.*;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.ButtonFunction;
import lk.vexview.gui.components.VexButton;
import lk.vexview.gui.components.expand.VexColorfulTextField;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.List;


public class WarTeamWindow {
    //创建GUI
    public static VexGui getGui(Integer type) {
        String url = "";
        if (type == 4) {
            url =  ImageUrlEnum.window2.getUrl();
        }else{
            url =  ImageUrlEnum.window.getUrl();
        }
        YamlConfiguration window = VvGuiYml.getWindow();
        VexGui vexGui = new VexGui(url, window.getInt("x"), window.getInt("y"), window.getInt("width"), window.getInt("high"));
        return vexGui;
    }

    //打开GUI
    public static void openGameLobbyGui(Player p, Integer type) {
        VexViewAPI.openGui(p, createGui(p,type));
    }

    //创建VV组件
    public static VexGui createGui(Player player,Integer type) {
        VexGui gui = getGui(type);
        YamlConfiguration shurukuang = VvGuiYml.getShurukuang();
        final VexColorfulTextField shurukuangTextField = new VexColorfulTextField(shurukuang.getInt("x"), shurukuang.getInt("y"), shurukuang.getInt("width"), shurukuang.getInt("high"),11,1,0x00FF3366,0x00663366,"请输入");
        //确定键
        YamlConfiguration confirm = VvGuiYml.getConfirm();
        VexButton confirmButton = new VexButton("confirmButton", "", ImageUrlEnum.confirm.getUrl(), PImageUrlEnum.confirm.getUrl(), confirm.getInt("x"), confirm.getInt("y"), confirm.getInt("width"), confirm.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                String typedText = shurukuangTextField.getTypedText();
                if (type == 1){
                    player.chat("/wt create "+typedText);
                }else if (type == 2){
                    player.chat("/wt join "+typedText);
                }else if (type == 3){
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
        });

        gui.addComponent(shurukuangTextField);
        gui.addComponent(confirmButton);
        return gui;
    }
}