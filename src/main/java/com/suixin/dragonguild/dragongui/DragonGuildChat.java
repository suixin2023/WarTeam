package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.entity.DragonGuildChatEntity;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.handler.DragonGuildChatDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;


public class DragonGuildChat {
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
        EasyScreen screen = getGui();
        //大厅
        YamlConfiguration lobby = DragonGuiYml.getLobby();
        EasyButton lobbyButton = new EasyButton(lobby.getInt("x"), lobby.getInt("y"), lobby.getInt("width"), lobby.getInt("high"), ImageUrlEnum.lobby.getUrl(), PImageUrlEnum.lobby.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildGui.openGameLobbyGui(player);
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
        EasyButton chatButton = new EasyButton(chat.getInt("x"), chat.getInt("y"), chat.getInt("width"), chat.getInt("high"), PImageUrlEnum.chat.getUrl(), PImageUrlEnum.chat.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
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
        EasyButton topButton = new EasyButton(top.getInt("x"), top.getInt("y"), top.getInt("width"), top.getInt("high"), ImageUrlEnum.top.getUrl(), PImageUrlEnum.top.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildTop.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //关闭
        YamlConfiguration close = DragonGuiYml.getClose();
        EasyButton closeButton = new EasyButton( close.getInt("x"), close.getInt("y"), close.getInt("width"), close.getInt("high"), ImageUrlEnum.close.getUrl(), PImageUrlEnum.close.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                openedScreen.closeGui(player);
            }
        };
        //聊天记录
        YamlConfiguration messageListYml = DragonGuiYml.getMessageList();
        EasyScrollingList scrollingList = new EasyScrollingList(messageListYml.getInt("x"), messageListYml.getInt("y"), messageListYml.getInt("width"), messageListYml.getInt("high"), "0,102,255,255");
        YamlConfiguration barYml = DragonGuiYml.getBar();
        scrollingList.setBar(barYml.getInt("w"), barYml.getInt("h"), barYml.getInt("high"), ImageUrlEnum.bar.getUrl());
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        Component component = userComponent.get(player.getName());
        chatList(scrollingList,player, component, 1, dragonGuildId,1);
        component.setScrollingList(scrollingList);
        //聊天内容
        YamlConfiguration content = DragonGuiYml.getChatContent();
        final EasyTextField contentTextField = new EasyTextField(content.getInt("x"), content.getInt("y"), content.getInt("width"));

        //发送
        YamlConfiguration send = DragonGuiYml.getSend();
        EasyButton shangyiyeButton = new EasyButton( send.getInt("x"), send.getInt("y"), send.getInt("width"), send.getInt("high"), ImageUrlEnum.send.getUrl(), PImageUrlEnum.send.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildChatEntity dragonGuildChatEntity = new DragonGuildChatEntity();
                dragonGuildChatEntity.setCreator(player.getName());
                dragonGuildChatEntity.setGuildId(dragonGuildId);
                dragonGuildChatEntity.setDesc(contentTextField.getText());
                dragonGuildChatEntity.setUid(player.getUniqueId().toString());
                dragonGuildChatEntity.setStatus(1);
                dragonGuildChatEntity.setCreated(new Date());
                DragonGuildChatDatabaseHandler.insert(dragonGuildChatEntity);
                List<DragonGuildMemberEntity> dragonGuildMemberEntities = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByDragonGuildId(dragonGuildId);
                for (DragonGuildMemberEntity dragonGuildMemberEntity : dragonGuildMemberEntities) {
                    String uid = dragonGuildMemberEntity.getUid();
                    Player member = Bukkit.getServer().getPlayer(uid);
                    if (member != null) {
                        member.sendMessage(DragonGuild.getSystemConfig().getString("DragonGuild.prefix") + "§a§l"+contentTextField.getText());
                    }
                }
            }
        };
        screen.addComponent(lobbyButton);
        screen.addComponent(noticeButton);
        screen.addComponent(chatButton);
        screen.addComponent(applyButton);
        screen.addComponent(topButton);
        screen.addComponent(scrollingList);
        screen.addComponent(closeButton);
        screen.addComponent(shangyiyeButton);
        return screen;
    }

    private static void chatList(EasyScrollingList scrollingList,Player player, Component component, Integer limit,Integer dragonGuildId,Integer type) {
        List<EasyComponent> list = new ArrayList<>();
        int currentPage = limit;
        //获取页数
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        Map<String, EasyComponent> components = new HashMap<>();
        if (openedScreen != null) {
            components = openedScreen.getComponents();
        }
        List<String> chatlist = component.getChatList();
        limit = (limit - 1) * 10;
        YamlConfiguration chatImg = DragonGuiYml.getChatImg();
        YamlConfiguration chatName = DragonGuiYml.getChatName();
        YamlConfiguration chatContent = DragonGuiYml.getChatContent();
        int chatImgY = chatImg.getInt("y");
        int chatNameY = chatName.getInt("y");
        int chatContentY = chatContent.getInt("y");
        List<DragonGuildChatEntity> dragonGuildChatEntities = DragonGuildChatDatabaseHandler.selectDragonGuildDataNum(limit, dragonGuildId);
        if (dragonGuildChatEntities.size() == 0) {
            return;
        }

        for (String easyComponent : chatlist) {
            components.remove(easyComponent);
        }
        chatlist.clear();
        for (DragonGuildChatEntity dragonGuildChatEntity: dragonGuildChatEntities) {
            String creator = dragonGuildChatEntity.getCreator();
            String desc = dragonGuildChatEntity.getDesc();
            DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByCreator(creator);
            EasyImage img = null;
            if (dragonGuildEntity.getId() == null) {
                img = new EasyImage(chatImg.getInt("x"), chatImgY, chatImg.getInt("width"), chatImg.getInt("high"),ImageUrlEnum.pictureFrame.getUrl());
            }else {
                img = new EasyImage(chatImg.getInt("x"), chatImgY, chatImg.getInt("width"), chatImg.getInt("high"),ImageUrlEnum.creator.getUrl());
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
            String format = simpleDateFormat.format(new Date());
            EasyLabel nameText = new EasyLabel(chatName.getInt("x"), chatNameY, 1,Arrays.asList(" §d"+creator+" "+format));
            EasyLabel chatContentText = new EasyLabel(chatContent.getInt("x"), chatContentY, 1,Arrays.asList(desc));

            list.add(img);
            list.add(nameText);
            list.add(chatContentText);
            scrollingList.addComponent(img);
            scrollingList.addComponent(nameText);
            scrollingList.addComponent(chatContentText);
            chatImgY = chatImgY + chatImg.getInt("high") + 3;
            chatNameY = chatNameY + chatImg.getInt("high")+ 3;
            chatContentY = chatContentY + chatImg.getInt("high")+ 3;
        }

        component.setCurrent(currentPage);
        for (EasyComponent easyComponent : list) {
            chatlist.add(easyComponent.getId());
        }
        component.setChatList(chatlist);
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        userComponent.put(player.getName(),component);
    }
}