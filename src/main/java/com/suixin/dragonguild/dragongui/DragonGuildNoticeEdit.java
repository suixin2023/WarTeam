package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.entity.DragonGuildNoticeEntity;
import com.suixin.dragonguild.handler.DragonGuildNoticeDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Date;

public class DragonGuildNoticeEdit {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration mainGui = DragonGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player,Integer dragonGuildId) {
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
        EasyButton topButton = new EasyButton(top.getInt("x"), top.getInt("y"), top.getInt("width"), top.getInt("high"), ImageUrlEnum.apply.getUrl(), PImageUrlEnum.apply.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildTop.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //关闭
        YamlConfiguration applyClose = DragonGuiYml.getClose();
        EasyButton applyCloseButton = new EasyButton( applyClose.getInt("x"), applyClose.getInt("y"), applyClose.getInt("width"), applyClose.getInt("high"), ImageUrlEnum.applyClose.getUrl(), PImageUrlEnum.applyClose.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };
        //标题
        YamlConfiguration title = DragonGuiYml.getTitle();
        final EasyTextField titleTextField = new EasyTextField(title.getInt("x"), title.getInt("y"), title.getInt("width"));
        YamlConfiguration content = DragonGuiYml.getContent();
        final EasyTextField contentTextField1 = new EasyTextField(content.getInt("x"), content.getInt("y"), content.getInt("width"));
        final EasyTextField contentTextField2 = new EasyTextField(content.getInt("x"), content.getInt("y")+5, content.getInt("width"));
        final EasyTextField contentTextField3 = new EasyTextField(content.getInt("x"), content.getInt("y")+10, content.getInt("width"));
        final EasyTextField contentTextField4 = new EasyTextField(content.getInt("x"), content.getInt("y")+15, content.getInt("width"));
        final EasyTextField contentTextField5 = new EasyTextField(content.getInt("x"), content.getInt("y")+20, content.getInt("width"));
        final EasyTextField contentTextField6 = new EasyTextField(content.getInt("x"), content.getInt("y")+25, content.getInt("width"));
        final EasyTextField contentTextField7 = new EasyTextField(content.getInt("x"), content.getInt("y")+30, content.getInt("width"));
        final EasyTextField contentTextField8 = new EasyTextField(content.getInt("x"), content.getInt("y")+35, content.getInt("width"));
        //保存
        YamlConfiguration save = DragonGuiYml.getSave();
        EasyButton saveButton = new EasyButton( save.getInt("x"), save.getInt("y"), save.getInt("width"), save.getInt("high"),ImageUrlEnum.applyXiayiye.getUrl(), PImageUrlEnum.applyXiayiye.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildNoticeEntity dragonGuildNoticeEntity = new DragonGuildNoticeEntity();
                dragonGuildNoticeEntity.setGuildId(dragonGuildId);
                dragonGuildNoticeEntity.setTitle(titleTextField.getText());
                dragonGuildNoticeEntity.setDesc(contentTextField1.getText()+"#"+contentTextField2.getText()+"#"+contentTextField3.getText()+"#"+contentTextField4.getText()+"#"+contentTextField5.getText()+"#"+contentTextField6.getText()+"#"
                +contentTextField7.getText()+"#"+contentTextField8.getText());
                dragonGuildNoticeEntity.setCreated(new Date());
                dragonGuildNoticeEntity.setUid(player.getUniqueId().toString());
                dragonGuildNoticeEntity.setCreator(player.getName());
                dragonGuildNoticeEntity.setStatus(1);
                DragonGuildNoticeEntity dragonGuildNoticeEntity1 = DragonGuildNoticeDatabaseHandler.selectDragonGuildByGuildId(dragonGuildId);
                DragonGuildNoticeDatabaseHandler.updateUserConfigDataNum(dragonGuildNoticeEntity1.getId(),dragonGuildNoticeEntity);
            }
        };
        //清空
        YamlConfiguration clear = DragonGuiYml.getClear();
        EasyButton clearButton = new EasyButton( clear.getInt("x"), clear.getInt("y"), clear.getInt("width"), clear.getInt("high"),ImageUrlEnum.applyAgree.getUrl(), PImageUrlEnum.applyAgree.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                EasyTextField title = (EasyTextField)openedScreen.getComponent("title");
                EasyTextField content = (EasyTextField)openedScreen.getComponent("content1");
                EasyTextField content2 = (EasyTextField)openedScreen.getComponent("content2");
                EasyTextField content3 = (EasyTextField)openedScreen.getComponent("content3");
                EasyTextField content4 = (EasyTextField)openedScreen.getComponent("content4");
                EasyTextField content5 = (EasyTextField)openedScreen.getComponent("content5");
                EasyTextField content6 = (EasyTextField)openedScreen.getComponent("content6");
                EasyTextField content7 = (EasyTextField)openedScreen.getComponent("content7");
                EasyTextField content8 = (EasyTextField)openedScreen.getComponent("content8");
                title.setText("");
                content.setText("");
                content2.setText("");
                content3.setText("");
                content4.setText("");
                content5.setText("");
                content6.setText("");
                content7.setText("");
                content8.setText("");
                openedScreen.updateGui(player);
            }
        };
        gui.addComponent(applyCloseButton);
        gui.addComponent(saveButton);
        gui.addComponent(lobbyButton);
        gui.addComponent(noticeButton);
        gui.addComponent(chatButton);
        gui.addComponent(applyButton);
        gui.addComponent(topButton);
        gui.addComponent("title",titleTextField);
        gui.addComponent("content1",contentTextField1);
        gui.addComponent("content2",contentTextField2);
        gui.addComponent("content3",contentTextField3);
        gui.addComponent("content4",contentTextField4);
        gui.addComponent("content5",contentTextField5);
        gui.addComponent("content6",contentTextField6);
        gui.addComponent("content7",contentTextField7);
        gui.addComponent("content8",contentTextField8);
        gui.addComponent(clearButton);
        return gui;
    }

}