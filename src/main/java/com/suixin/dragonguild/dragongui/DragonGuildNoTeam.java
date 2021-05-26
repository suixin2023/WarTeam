package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.EasyButtonEx;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;


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
        Component component = new Component();
        //关闭
        YamlConfiguration close = DragonGuiYml.getClose();
        EasyButton closeButton = new EasyButton( close.getInt("x"), close.getInt("y"), close.getInt("width"), close.getInt("high"), ImageUrlEnum.close.getUrl(), PImageUrlEnum.close.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                player.closeInventory();
            }
        };
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        userComponent.put(player.getName(),component);
        YamlConfiguration topbgYml = DragonGuiYml.getTopbg();
        EasyScrollingList scrollingList = new EasyScrollingList(topbgYml.getInt("x"), topbgYml.getInt("y"), topbgYml.getInt("width"), topbgYml.getInt("high"), ImageUrlEnum.listbg.getUrl());
        YamlConfiguration barYml = DragonGuiYml.getBar();
        scrollingList.setBar(barYml.getInt("w"), barYml.getInt("h"), barYml.getInt("high"), ImageUrlEnum.bar.getUrl());
        easyScrollList(scrollingList,player, component, 1,1);
        component.setScrollingList(scrollingList);
        //上一页
        YamlConfiguration shangyiye = DragonGuiYml.getTopShangyiye();
        EasyButton shangyiyeButton = new EasyButton( shangyiye.getInt("x"), shangyiye.getInt("y"), shangyiye.getInt("width"), shangyiye.getInt("high"), ImageUrlEnum.shangyiye.getUrl(), PImageUrlEnum.shangyiye.getUrl()) {
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
        YamlConfiguration xiayiye = DragonGuiYml.getTopXiayiye();
        EasyButton xiayiyeButton = new EasyButton( xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"),ImageUrlEnum.xiayiye.getUrl(), PImageUrlEnum.xiayiye.getUrl() ) {
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
        YamlConfiguration name = DragonGuiYml.getName();
        EasyLabel nameText = new EasyLabel(name.getInt("x"), name.getInt("y"), 1, Arrays.asList(DragonGuild.getSystemConfig().getString("DragonGuild.noteam")));
        YamlConfiguration listBgkYml = DragonGuiYml.getListBgk();
        EasyImage listBgkImg = new EasyImage( listBgkYml.getInt("x"), listBgkYml.getInt("y"), listBgkYml.getInt("width"), listBgkYml.getInt("high"),ImageUrlEnum.listbgk.getUrl());
        screen.addComponent(nameText);
        screen.addComponent(listBgkImg);
        screen.addComponent(scrollingList);
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
        limit = (limit - 1) * 6;
        List<EasyComponent> list = new ArrayList<>();
        List<String> guildList = component.getGuildList();
        YamlConfiguration topYml = DragonGuiYml.getTopList();
        YamlConfiguration topImgYml = DragonGuiYml.getTopImg();
        YamlConfiguration nameYml = DragonGuiYml.getTopName();
        YamlConfiguration topLevelYml = DragonGuiYml.getTopLevel();
        int topYmlY = topYml.getInt("y");
        int topImgYmlY = topImgYml.getInt("y");
        int nameYmlY = nameYml.getInt("y");
        int topLevelYmlY = topLevelYml.getInt("y");
        YamlConfiguration joinApply = DragonGuiYml.getTopJoinApply();
        int joinApplyY = joinApply.getInt("y");
        List<DragonGuildEntity> dragonGuildEntities = DragonGuildDatabaseHandler.selectDragonGuildDataNum(limit);
        if (dragonGuildEntities.size() == 0) {
            player.sendMessage(Message.no_more_guild);
            return;
        }
        for (String easyComponent : guildList) {
            components.remove(easyComponent);
        }
        for (int i = 0; i < dragonGuildEntities.size(); i ++) {
            String name = dragonGuildEntities.get(i).getName();
            Integer level = dragonGuildEntities.get(i).getLevel();
            EasyImage topUmg = new EasyImage( topYml.getInt("x"), topYmlY, topYml.getInt("width"), topYml.getInt("high"),ImageUrlEnum.guildList.getUrl());
            EasyLabel nameText = new EasyLabel(nameYml.getInt("x"), nameYmlY, 1,Arrays.asList(name));
            EasyLabel topLevelText = new EasyLabel(topLevelYml.getInt("x"), topLevelYmlY, 1,Arrays.asList("LV: "+level));
            EasyImage img;
            if (currentPage == 1 && i == 0) {
                img = new EasyImage( topImgYml.getInt("x"), topImgYmlY, topImgYml.getInt("width"), topImgYml.getInt("high"),ImageUrlEnum.guild.getUrl());
            }else if (currentPage == 1 && i == 1) {
                img = new EasyImage( topImgYml.getInt("x"), topImgYmlY, topImgYml.getInt("width"), topImgYml.getInt("high"),ImageUrlEnum.guild2.getUrl());
            }else if (currentPage == 1 && i == 2) {
                img = new EasyImage( topImgYml.getInt("x"), topImgYmlY, topImgYml.getInt("width"), topImgYml.getInt("high"),ImageUrlEnum.guild3.getUrl());
            }else{
                img = new EasyImage( topImgYml.getInt("x"), topImgYmlY, topImgYml.getInt("width"), topImgYml.getInt("high"),ImageUrlEnum.guild4.getUrl());
            }
            //申请加入
            EasyButtonEx joinApplyButton = new EasyButtonEx( joinApply.getInt("x"), joinApplyY, joinApply.getInt("width"), joinApply.getInt("high"),ImageUrlEnum.applyJoin.getUrl(), PImageUrlEnum.applyJoin.getUrl() ) {
                @Override
                public void onClick(Player player, Type type) {
                }
            };

            list.add(topUmg);
            list.add(nameText);
            list.add(img);
            list.add(topLevelText);
            list.add(joinApplyButton);
            joinApplyButton.setGuildName(name);
            joinApplyButton.setType("joinApplyButton");
            scrollingList.addComponent(topUmg);
            scrollingList.addComponent(nameText);
            scrollingList.addComponent(img);
            scrollingList.addComponent(topLevelText);
            scrollingList.addComponent(joinApplyButton);
            topYmlY = topYmlY + topYml.getInt("interval");
            nameYmlY = nameYmlY + topYml.getInt("interval");
            topImgYmlY = topImgYmlY + topYml.getInt("interval");
            topLevelYmlY = topLevelYmlY + topYml.getInt("interval");
            joinApplyY = joinApplyY + topYml.getInt("interval");
        }
        for (EasyComponent easyComponent : list) {
            guildList.add(easyComponent.getId());
        }
        component.setCurrent(currentPage);
        component.setGuildList(guildList);
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        userComponent.put(player.getName(),component);
    }
}