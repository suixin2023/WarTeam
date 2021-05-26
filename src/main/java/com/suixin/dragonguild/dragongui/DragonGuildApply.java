package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.entity.DragonGuildApplyEntity;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.EasyButtonEx;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.*;


public class DragonGuildApply {
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
        EasyButton chatButton = new EasyButton(chat.getInt("x"), chat.getInt("y"), chat.getInt("width"), chat.getInt("high"), ImageUrlEnum.chat.getUrl(), PImageUrlEnum.chat.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildChat.openGameLobbyGui(player,dragonGuildId);
            }
        };
        //审批
        YamlConfiguration apply = DragonGuiYml.getApply();
        EasyButton applyButton = new EasyButton(apply.getInt("x"), apply.getInt("y"), apply.getInt("width"), apply.getInt("high"), PImageUrlEnum.apply.getUrl(), PImageUrlEnum.apply.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
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
                player.closeInventory();
            }
        };
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        Component component = userComponent.get(player.getName());
        YamlConfiguration applyBackgroundYml = DragonGuiYml.getApplyBackground();
        EasyScrollingList scrollingList = new EasyScrollingList(applyBackgroundYml.getInt("x"), applyBackgroundYml.getInt("y"), applyBackgroundYml.getInt("width"), applyBackgroundYml.getInt("high"), ImageUrlEnum.listbg.getUrl());
        YamlConfiguration barYml = DragonGuiYml.getBar();
        scrollingList.setBar(barYml.getInt("w"), barYml.getInt("h"), barYml.getInt("high"), ImageUrlEnum.bar.getUrl());
        applyList(scrollingList,player, component, 1, dragonGuildId,1);
        component.setScrollingList(scrollingList);
        //上一页
        YamlConfiguration shangyiye = DragonGuiYml.getShangyiye();
        EasyButton shangyiyeButton = new EasyButton( shangyiye.getInt("x"), shangyiye.getInt("y"), shangyiye.getInt("width"), shangyiye.getInt("high"), ImageUrlEnum.shangyiye.getUrl(), PImageUrlEnum.shangyiye.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                Component component = DragonGuildGui.getUserComponent().get(player.getName());
                Integer currentPage = component.getCurrent();
                if (currentPage == 1) {
                    return;
                }
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                EasyScrollingList scrollingList1 = component.getScrollingList();
                applyList(scrollingList1,player, component, currentPage - 1, dragonGuildId, 2);
                openedScreen.addComponent(scrollingList1);
                userComponent.put(player.getName(),component);
                openedScreen.updateGui(player);
            }
        };

        //下一页
        YamlConfiguration xiayiye = DragonGuiYml.getXiayiye();
        EasyButton xiayiyeButton = new EasyButton( xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"),ImageUrlEnum.xiayiye.getUrl(), PImageUrlEnum.xiayiye.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                Component component = DragonGuildGui.getUserComponent().get(player.getName());
                Integer limit = component.getCurrent() + 1;
                EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
                EasyScrollingList scrollingList1 = component.getScrollingList();
                applyList(scrollingList1,player, component, limit, dragonGuildId, 2);
                openedScreen.addComponent(scrollingList1);
                userComponent.put(player.getName(),component);
                openedScreen.updateGui(player);
            }
        };
        YamlConfiguration listBgkYml = DragonGuiYml.getListBgk();
        EasyImage listBgkImg = new EasyImage( listBgkYml.getInt("x"), listBgkYml.getInt("y"), listBgkYml.getInt("width"), listBgkYml.getInt("high"),ImageUrlEnum.listbgk.getUrl());
        YamlConfiguration name = DragonGuiYml.getName();
        DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildById(dragonGuildId);
        EasyLabel nameText = new EasyLabel(name.getInt("x"), name.getInt("y"), 1, Arrays.asList(dragonGuildEntity.getName()));
        Integer count = DragonGuildMemBerDatabaseHandler.selectCount(dragonGuildId);
        YamlConfiguration renshu = DragonGuiYml.getRenshu();
        YamlConfiguration level = DragonGuiYml.getLevel();
        EasyLabel renshuText = new EasyLabel(renshu.getInt("x"), renshu.getInt("y"), 1, Arrays.asList(Message.member+count + "/"+dragonGuildEntity.getMaxMember()));
        EasyLabel levelText = new EasyLabel( level.getInt("x"), level.getInt("y"),1, Arrays.asList(Message.level+dragonGuildEntity.getLevel()+""));

        //图标
        YamlConfiguration guildImgYml = DragonGuiYml.getGuildImg();
        EasyImage guildImg = new EasyImage( guildImgYml.getInt("x"), guildImgYml.getInt("y"), guildImgYml.getInt("width"), guildImgYml.getInt("high"),ImageUrlEnum.guildImg.getUrl());
        screen.addComponent(listBgkImg);
        screen.addComponent(scrollingList);
        screen.addComponent(guildImg);
        screen.addComponent(nameText);
        screen.addComponent(renshuText);
        screen.addComponent(levelText);
        screen.addComponent(lobbyButton);
        screen.addComponent(noticeButton);
        screen.addComponent(chatButton);
        screen.addComponent(applyButton);
        screen.addComponent(topButton);
        screen.addComponent(closeButton);
        screen.addComponent(shangyiyeButton);
        screen.addComponent(xiayiyeButton);
        return screen;
    }

    private static void applyList(EasyScrollingList scrollingList,Player player, Component component, Integer limit,Integer dragonGuildId,Integer type) {
        List<EasyComponent> list = new ArrayList<>();
        int currentPage = limit;
        //获取页数
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        Map<String, EasyComponent> components = new HashMap<>();
        if (openedScreen != null) {
            components = openedScreen.getComponents();
        }
        List<String> applylist = component.getApplyList();
        limit = (limit - 1) * 6;
        YamlConfiguration applyList = DragonGuiYml.getApplyList();
        YamlConfiguration applyAgree = DragonGuiYml.getApplyAgree();
        YamlConfiguration applyRepulse = DragonGuiYml.getApplyRepulse();
        YamlConfiguration nameYml = DragonGuiYml.getApplyPlayerName();
        YamlConfiguration applyTimeYml = DragonGuiYml.getApplyTime();
        int applyAgreeY = applyAgree.getInt("y");
        int applyRepulseY = applyRepulse.getInt("y");
        int nameYmlY = nameYml.getInt("y");
        int applyTimeYmlY = applyTimeYml.getInt("y");
        int applyListY = applyList.getInt("y");
        List<DragonGuildApplyEntity> dragonGuildApplyEntities = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByDragonGuildId(limit, dragonGuildId);
        if (dragonGuildApplyEntities.size() == 0) {
            player.sendMessage(Message.no_more_apply);
            return;
        }

        for (String easyComponent : applylist) {
            components.remove(easyComponent);
        }
        applylist.clear();
        for (DragonGuildApplyEntity dragonGuildApplyEntity: dragonGuildApplyEntities) {
            String apply = dragonGuildApplyEntity.getUid();
            Date created = dragonGuildApplyEntity.getCreated();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
            String format = simpleDateFormat.format(created);
            EasyLabel nameText = new EasyLabel(nameYml.getInt("x"), nameYmlY, 1,Arrays.asList("申请人： "+apply));
            EasyLabel timeText = new EasyLabel(applyTimeYml.getInt("x"), applyTimeYmlY, 1,Arrays.asList(format));
            EasyImage applyListImg = new EasyImage( applyList.getInt("x"), applyListY, applyList.getInt("width"), applyList.getInt("high"),ImageUrlEnum.applyList.getUrl());
            //同意
            EasyButtonEx applyAgreeButton = new EasyButtonEx( applyAgree.getInt("x"), applyAgreeY, applyAgree.getInt("width"), applyAgree.getInt("high"),ImageUrlEnum.applyAgree.getUrl(), PImageUrlEnum.applyAgree.getUrl() ) {
                @Override
                public void onClick(Player player, Type type) {

                }
            };
            //不同意
            EasyButtonEx applyRepulseButton = new EasyButtonEx(applyRepulse.getInt("x"), applyRepulseY, applyRepulse.getInt("width"), applyRepulse.getInt("high"), ImageUrlEnum.applyRepulse.getUrl(), PImageUrlEnum.applyRepulse.getUrl()) {
                @Override
                public void onClick(Player player, Type type) {

                }
            };
            list.add(applyListImg);
            list.add(applyRepulseButton);
            list.add(applyAgreeButton);
            list.add(nameText);
            list.add(timeText);
            applyRepulseButton.setApplyName(apply);
            applyRepulseButton.setType("applyRepulseButton");
            applyAgreeButton.setApplyName(apply);
            applyAgreeButton.setType("applyAgreeButton");
            scrollingList.addComponent(applyListImg);
            scrollingList.addComponent(applyRepulseButton);
            scrollingList.addComponent(applyAgreeButton);
            scrollingList.addComponent(nameText);
            scrollingList.addComponent(timeText);
            applyAgreeY = applyAgreeY + applyList.getInt("interval");
            applyRepulseY = applyRepulseY + applyList.getInt("interval");
            nameYmlY = nameYmlY + applyList.getInt("interval");
            applyListY = applyListY + applyList.getInt("interval");
            applyTimeYmlY = applyTimeYmlY + applyList.getInt("interval");
        }
        for (EasyComponent easyComponent : list) {
            applylist.add(easyComponent.getId());
        }
        component.setCurrent(currentPage);
        component.setApplyList(applylist);
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        userComponent.put(player.getName(),component);
    }
}