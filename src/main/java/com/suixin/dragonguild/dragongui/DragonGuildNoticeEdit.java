package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.entity.DragonGuildNoticeEntity;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildNoticeDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;

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
        EasyButton topButton = new EasyButton(top.getInt("x"), top.getInt("y"), top.getInt("width"), top.getInt("high"), ImageUrlEnum.top.getUrl(), PImageUrlEnum.top.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildTop.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //关闭
        YamlConfiguration applyClose = DragonGuiYml.getClose();
        EasyButton applyCloseButton = new EasyButton( applyClose.getInt("x"), applyClose.getInt("y"), applyClose.getInt("width"), applyClose.getInt("high"), ImageUrlEnum.close.getUrl(), PImageUrlEnum.close.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                player.closeInventory();
            }
        };

        //返回
        YamlConfiguration back = DragonGuiYml.getBack();
        EasyButton backButton = new EasyButton( back.getInt("x"), back.getInt("y"), back.getInt("width"), back.getInt("high"), ImageUrlEnum.back.getUrl(), PImageUrlEnum.back.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildNotice.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //内容
        YamlConfiguration backGroundNotice = DragonGuiYml.getBackGroundNotice();
        EasyImage img = new EasyImage( backGroundNotice.getInt("x"), backGroundNotice.getInt("y"), backGroundNotice.getInt("width"), backGroundNotice.getInt("high"),ImageUrlEnum.neirong.getUrl());
        DragonGuildNoticeEntity dragonGuildNoticeEntity1 = DragonGuildNoticeDatabaseHandler.selectDragonGuildByGuildId(dragonGuildId);
        String descs = dragonGuildNoticeEntity1.getDescs();
        String[] split = descs.split("#");
        Map<Integer, String> map = new HashMap<>();
        int a = 1;
        for (String s : split) {
            map.put(a,s);
            a++;
        }
        //标题
        YamlConfiguration title = DragonGuiYml.getTitle();
        final EasyTextField titleTextField = new EasyTextField(title.getInt("x"), title.getInt("y"), title.getInt("width"),dragonGuildNoticeEntity1.getTitle());
        YamlConfiguration content = DragonGuiYml.getContent();
        final EasyTextField contentTextField1 = new EasyTextField(content.getInt("x"), content.getInt("y"), content.getInt("width"),map.get(1));
        final EasyTextField contentTextField2 = new EasyTextField(content.getInt("x"), content.getInt("y")+content.getInt("interval"), content.getInt("width"),map.get(2));
        final EasyTextField contentTextField3 = new EasyTextField(content.getInt("x"), content.getInt("y")+content.getInt("interval")*2, content.getInt("width"),map.get(3));
        final EasyTextField contentTextField4 = new EasyTextField(content.getInt("x"), content.getInt("y")+content.getInt("interval")*3, content.getInt("width"),map.get(4));
        final EasyTextField contentTextField5 = new EasyTextField(content.getInt("x"), content.getInt("y")+content.getInt("interval")*4, content.getInt("width"),map.get(5));
        final EasyTextField contentTextField6 = new EasyTextField(content.getInt("x"), content.getInt("y")+content.getInt("interval")*5, content.getInt("width"),map.get(6));
        final EasyTextField contentTextField7 = new EasyTextField(content.getInt("x"), content.getInt("y")+content.getInt("interval")*6, content.getInt("width"),map.get(7));
        final EasyTextField contentTextField8 = new EasyTextField(content.getInt("x"), content.getInt("y")+content.getInt("interval")*7, content.getInt("width"),map.get(8));
        final EasyTextField contentTextField9 = new EasyTextField(content.getInt("x"), content.getInt("y")+content.getInt("interval")*8, content.getInt("width"),map.get(9));
        //保存
        YamlConfiguration save = DragonGuiYml.getSave();
        EasyButton saveButton = new EasyButton( save.getInt("x"), save.getInt("y"), save.getInt("width"), save.getInt("high"),ImageUrlEnum.save.getUrl(), PImageUrlEnum.save.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildNoticeEntity dragonGuildNoticeEntity = new DragonGuildNoticeEntity();
                dragonGuildNoticeEntity.setGuildId(dragonGuildId);
                dragonGuildNoticeEntity.setTitle(titleTextField.getText());
                String deacs = contentTextField1.getText()+"#"+contentTextField2.getText()+"#"+contentTextField3.getText()+"#"+contentTextField4.getText()+"#"+contentTextField5.getText()+"#"+contentTextField6.getText()+"#"
                +contentTextField7.getText()+"#"+contentTextField8.getText()+"#"+contentTextField9.getText();
                String aNull = deacs.replace("null", "");
                dragonGuildNoticeEntity.setDescs(aNull);
                dragonGuildNoticeEntity.setUid(player.getUniqueId().toString());
                dragonGuildNoticeEntity.setCreator(player.getName());
                dragonGuildNoticeEntity.setStatus(1);
                DragonGuildNoticeDatabaseHandler.updateUserConfigDataNum(dragonGuildNoticeEntity1.getId(),dragonGuildNoticeEntity);
                List<DragonGuildMemberEntity> dragonGuildMemberEntities = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByDragonGuildId(dragonGuildId);
                for (DragonGuildMemberEntity dragonGuildMemberEntity : dragonGuildMemberEntities) {
                    String uid = dragonGuildMemberEntity.getUid();
                    Player member = Bukkit.getServer().getPlayer(uid);
                    if (member != null) {
                        member.sendMessage(DragonGuild.getSystemConfig().getString("DragonGuild.prefix") + "§a§l会长发布了新公告");
                    }
                }
                DragonGuildNotice.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //清空
        YamlConfiguration clear = DragonGuiYml.getClear();
        EasyButton clearButton = new EasyButton( clear.getInt("x"), clear.getInt("y"), clear.getInt("width"), clear.getInt("high"),ImageUrlEnum.clear.getUrl(), PImageUrlEnum.clear.getUrl() ) {
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
                EasyTextField content9 = (EasyTextField)openedScreen.getComponent("content9");
                title.setText("");
                content.setText("");
                content2.setText("");
                content3.setText("");
                content4.setText("");
                content5.setText("");
                content6.setText("");
                content7.setText("");
                content8.setText("");
                content9.setText("");
                openedScreen.updateGui(player);
            }
        };
        DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildById(dragonGuildId);
        YamlConfiguration name = DragonGuiYml.getName();
        EasyLabel nameText = new EasyLabel(name.getInt("x"), name.getInt("y"), 1, Arrays.asList(dragonGuildEntity.getName()));
        gui.addComponent(img);
        gui.addComponent(nameText);
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
        gui.addComponent("content9",contentTextField9);
        gui.addComponent(clearButton);
        gui.addComponent(backButton);
        return gui;
    }

}