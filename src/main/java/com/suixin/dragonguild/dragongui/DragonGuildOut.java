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


public class DragonGuildOut {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration mainGui = DragonGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player, Integer type,Integer dragonGuildId) {
        EasyScreen lhdGui = createGui(type, dragonGuildId);
        lhdGui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Integer type1,Integer dragonGuildId) {
        EasyScreen gui = getGui();
        //大厅
        YamlConfiguration lobby = DragonGuiYml.getLobby();
        EasyButton lobbyButton = new EasyButton(lobby.getInt("x"), lobby.getInt("y"), lobby.getInt("width"), lobby.getInt("high"), PImageUrlEnum.lobby.getUrl(), PImageUrlEnum.lobby.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
            }
        };
        //公告
        YamlConfiguration notice = DragonGuiYml.getNotice();
        EasyButton noticeButton = new EasyButton(notice.getInt("x"), notice.getInt("y"), notice.getInt("width"), notice.getInt("high"), ImageUrlEnum.notice.getUrl(), PImageUrlEnum.notice.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildNotice.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //聊天
        YamlConfiguration chat = DragonGuiYml.getChat();
        EasyButton chatButton = new EasyButton(chat.getInt("x"), chat.getInt("y"), chat.getInt("width"), chat.getInt("high"), ImageUrlEnum.chat.getUrl(), PImageUrlEnum.chat.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildChat.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //审批
        YamlConfiguration apply = DragonGuiYml.getApply();
        EasyButton applyButton = new EasyButton(apply.getInt("x"), apply.getInt("y"), apply.getInt("width"), apply.getInt("high"), ImageUrlEnum.apply.getUrl(), PImageUrlEnum.apply.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildApply.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //排行
        YamlConfiguration top = DragonGuiYml.getTop();
        EasyButton topButton = new EasyButton(top.getInt("x"), top.getInt("y"), top.getInt("width"), top.getInt("high"), ImageUrlEnum.apply.getUrl(), PImageUrlEnum.apply.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildTop.openGameLobbyGui(player,dragonGuildId);
            }
        };
        YamlConfiguration shurukuang = DragonGuiYml.getShurukuang();
        final EasyTextField shurukuangTextField = new EasyTextField(shurukuang.getInt("x"), shurukuang.getInt("y"), shurukuang.getInt("width"), "请输入");
        //确定键
        YamlConfiguration confirm = DragonGuiYml.getConfirm();
        EasyButton confirmButton = new EasyButton( confirm.getInt("x"), confirm.getInt("y"), confirm.getInt("width"), confirm.getInt("high"),  ImageUrlEnum.confirm.getUrl(), PImageUrlEnum.confirm.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                String teamName = shurukuangTextField.getText();
                if (type1 == 1){
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
                    player.chat("/gh out "+teamName);
                }
                DragonGuildGui.openGameLobbyGui(player);
            }
        };
        gui.addComponent(lobbyButton);
        gui.addComponent(noticeButton);
        gui.addComponent(chatButton);
        gui.addComponent(applyButton);
        gui.addComponent(topButton);
        gui.addComponent(shurukuangTextField);
        gui.addComponent(confirmButton);
        return gui;
    }
}