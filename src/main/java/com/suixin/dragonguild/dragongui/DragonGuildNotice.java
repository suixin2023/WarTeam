package com.suixin.dragonguild.dragongui;


import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildNoticeEntity;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildNoticeDatabaseHandler;
import com.suixin.dragonguild.util.DragonGuiYml;
import com.suixin.dragonguild.util.ImageUrlEnum;
import com.suixin.dragonguild.util.PImageUrlEnum;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import eos.moe.dragoncore.api.easygui.component.EasyImage;
import eos.moe.dragoncore.api.easygui.component.EasyLabel;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DragonGuildNotice {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration mainGui = DragonGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player, Integer dragonGuildId) {
        EasyScreen gui = createGui(player,dragonGuildId);
        gui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Player player, Integer dragonGuildId) {
        EasyScreen gui = getGui();
        //大厅
        YamlConfiguration lobby = DragonGuiYml.getLobby();
        EasyButton lobbyButton = new EasyButton(lobby.getInt("x"), lobby.getInt("y"), lobby.getInt("width"), lobby.getInt("high"), ImageUrlEnum.lobby.getUrl(), PImageUrlEnum.lobby.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };
        //公告
        YamlConfiguration notice = DragonGuiYml.getNotice();
        EasyButton noticeButton = new EasyButton(notice.getInt("x"), notice.getInt("y"), notice.getInt("width"), notice.getInt("high"), PImageUrlEnum.notice.getUrl(), PImageUrlEnum.notice.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
            }
        };
        //聊天
        YamlConfiguration chat = DragonGuiYml.getChat();
        EasyButton chatButton = new EasyButton(chat.getInt("x"), chat.getInt("y"), chat.getInt("width"), chat.getInt("high"), ImageUrlEnum.chat.getUrl(), PImageUrlEnum.chat.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildChat.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //审批
        YamlConfiguration apply = DragonGuiYml.getApply();
        EasyButton applyButton = new EasyButton(apply.getInt("x"), apply.getInt("y"), apply.getInt("width"), apply.getInt("high"), ImageUrlEnum.apply.getUrl(), PImageUrlEnum.apply.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildApply.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //排行
        YamlConfiguration top = DragonGuiYml.getTop();
        EasyButton topButton = new EasyButton(top.getInt("x"), top.getInt("y"), top.getInt("width"), top.getInt("high"), ImageUrlEnum.top.getUrl(), PImageUrlEnum.top.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildTop.openGameLobbyGui(player,dragonGuildId);
            }
        };
        YamlConfiguration backGroundNotice = DragonGuiYml.getBackGroundNotice();
        EasyImage img = new EasyImage( backGroundNotice.getInt("x"), backGroundNotice.getInt("y"), backGroundNotice.getInt("width"), backGroundNotice.getInt("high"),ImageUrlEnum.neirong.getUrl());
        //编辑公告
        YamlConfiguration edit = DragonGuiYml.getEdit();
        EasyButton editButton = new EasyButton(edit.getInt("x"), edit.getInt("y"), edit.getInt("width"), edit.getInt("high"), ImageUrlEnum.edit.getUrl(), PImageUrlEnum.edit.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildNoticeEdit.openGameLobbyGui(player,dragonGuildId);
            }
        };
        DragonGuildNoticeEntity dragonGuildNoticeEntity = DragonGuildNoticeDatabaseHandler.selectDragonGuildByGuildId(dragonGuildId);
        YamlConfiguration title = DragonGuiYml.getTitle();
        EasyLabel titleText = new EasyLabel(title.getInt("x"), title.getInt("y"), 1, Arrays.asList(dragonGuildNoticeEntity.getTitle()));
        String desc = dragonGuildNoticeEntity.getDesc();
        String[] split = desc.split("#");
        YamlConfiguration chatContent = DragonGuiYml.getChatContent();
        EasyLabel chatContentText = new EasyLabel(chatContent.getInt("x"), chatContent.getInt("y"), 1, Arrays.asList(split));
        DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
        gui.addComponent(lobbyButton);
        gui.addComponent(noticeButton);
        gui.addComponent(chatButton);
        gui.addComponent(applyButton);
        gui.addComponent(topButton);
        gui.addComponent(img);
        //会长显示编辑公告按钮
        if (dragonGuildEntity.getId() != null) {
            gui.addComponent(editButton);
        }
        gui.addComponent(titleText);
        gui.addComponent(chatContentText);
        return gui;
    }
}