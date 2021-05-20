package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DragonGuildNoTeam {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration mainGui = DragonGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player) {
        EasyScreen gui = createGui(player);
        gui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Player player) {
        EasyScreen screen = getGui();
        //关闭
        YamlConfiguration close = DragonGuiYml.getClose();
        EasyButton closeButton = new EasyButton( close.getInt("x"), close.getInt("y"), close.getInt("width"), close.getInt("high"), ImageUrlEnum.close.getUrl(), PImageUrlEnum.close.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                openedScreen.closeGui(player);
            }
        };
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        Component component = userComponent.get(player.getName());
        YamlConfiguration messageListYml = DragonGuiYml.getMessageList();
        EasyScrollingList scrollingList = new EasyScrollingList(messageListYml.getInt("x"), messageListYml.getInt("y"), messageListYml.getInt("width"), messageListYml.getInt("high"), "0,102,255,255");
        scrollingList.setBar(10, 28, 500, ImageUrlEnum.bar.getUrl());

        easyScrollList(scrollingList,player, component, 1,1);
        //上一页
        YamlConfiguration shangyiye = DragonGuiYml.getShangyiye();
        EasyButton shangyiyeButton = new EasyButton( shangyiye.getInt("x"), shangyiye.getInt("y"), shangyiye.getInt("width"), shangyiye.getInt("high"), ImageUrlEnum.applyShangyiye.getUrl(), PImageUrlEnum.applyShangyiye.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                Component component = DragonGuildGui.getUserComponent().get(player.getName());
                Integer currentPage = component.getCurrent();
                if (currentPage == 1) {
                    return;
                }
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                EasyScrollingList scrollingList1 = component.getScrollingList();
                easyScrollList(scrollingList1,player, component, currentPage - 1, 2);
                openedScreen.addComponent(scrollingList1);
                userComponent.put(player.getName(),component);
                openedScreen.updateGui(player);
            }
        };

        //下一页
        YamlConfiguration xiayiye = DragonGuiYml.getXiayiye();
        EasyButton xiayiyeButton = new EasyButton( xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"),ImageUrlEnum.applyXiayiye.getUrl(), PImageUrlEnum.applyXiayiye.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                Component component = DragonGuildGui.getUserComponent().get(player.getName());
                Integer limit = component.getCurrent() + 1;
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                EasyScrollingList scrollingList1 = component.getScrollingList();
                easyScrollList(scrollingList1,player, component, limit, 2);
                openedScreen.addComponent(scrollingList1);
                userComponent.put(player.getName(),component);
                openedScreen.updateGui(player);
            }
        };
        //创建公会
        YamlConfiguration create = DragonGuiYml.getCreate();
        EasyButton createButton = new EasyButton(create.getInt("x"), create.getInt("y"), create.getInt("width"), create.getInt("high"), ImageUrlEnum.create.getUrl(), PImageUrlEnum.create.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                openedScreen.onClose(player);
                DragonGuildCreate.openGameLobbyGui(player, 1);
            }
        };
        //加入公会
        YamlConfiguration join = DragonGuiYml.getJoin();
        EasyButton joinButton = new EasyButton(join.getInt("x"), join.getInt("y"), join.getInt("width"), join.getInt("high"),ImageUrlEnum.join.getUrl(), PImageUrlEnum.join.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                openedScreen.onClose(player);
                DragonGuildCreate.openGameLobbyGui(player,2);
            }
        };
        screen.addComponent(closeButton);
        screen.addComponent(shangyiyeButton);
        screen.addComponent(xiayiyeButton);
        screen.addComponent(joinButton);
        screen.addComponent(createButton);
        return screen;
    }

    private static void easyScrollList(EasyScrollingList scrollingList,Player player, Component component, Integer limit,Integer type) {
        int currentPage = limit;
        //获取页数
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        Map<String, EasyComponent> components = new HashMap<>();
        if (openedScreen != null) {
            components = openedScreen.getComponents();
        }
        YamlConfiguration topImgYml = DragonGuiYml.getTopImg();
        YamlConfiguration nameYml = DragonGuiYml.getTopName();
        YamlConfiguration joinApply = DragonGuiYml.getTopJoinApply();
        int topImgYmlY = topImgYml.getInt("y");
        int nameYmlY = nameYml.getInt("y");
        int joinApplyY = joinApply.getInt("y");
        List<DragonGuildEntity> dragonGuildEntities = DragonGuildDatabaseHandler.selectDragonGuildDataNum(currentPage);
        if (dragonGuildEntities.size() == 0) {
            player.sendMessage(Message.no_more_apply);
            return;
        }
        Map<String, EasyComponent> components1 = scrollingList.getComponents();
        for (String key : components1.keySet()) {
            components.remove(key);
        }
        for (DragonGuildEntity dragonGuildEntity: dragonGuildEntities) {
            String name = dragonGuildEntity.getName();
            EasyLabel nameText = new EasyLabel(nameYml.getInt("x"), nameYmlY, 1,Arrays.asList(name));
            EasyImage img = new EasyImage( topImgYml.getInt("x"), topImgYmlY, topImgYml.getInt("width"), topImgYml.getInt("high"),ImageUrlEnum.guild.getUrl());
            //申请加入
            EasyButton joinApplyButton = new EasyButton( joinApply.getInt("x"), joinApplyY, joinApply.getInt("width"), joinApply.getInt("high"),ImageUrlEnum.apply.getUrl(), PImageUrlEnum.apply.getUrl() ) {
                @Override
                public void onClick(Player player, Type type) {
                    player.chat("/gh join " + name);
                }
            };
            scrollingList.addComponent(nameText);
            scrollingList.addComponent(img);
            scrollingList.addComponent(joinApplyButton);
            joinApplyY = joinApplyY + topImgYml.getInt("high") + 3;
            nameYmlY = nameYmlY + topImgYml.getInt("high")+ 3;
            topImgYmlY = topImgYmlY + topImgYml.getInt("high")+ 3;
        }

        component.setCurrent(currentPage);
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        userComponent.put(player.getName(),component);
    }
}