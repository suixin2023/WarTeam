package com.suixin.warteam.dragongui;

import com.suixin.warteam.entity.WarTeamApplyEntity;
import com.suixin.warteam.gui.WarTeamGui;
import com.suixin.warteam.handler.WarTeamApplyDatabaseHandler;
import com.suixin.warteam.listener.EasyButtonClickListener;
import com.suixin.warteam.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class WarTeamApply {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration window = VvGuiYml.getApplyBackground();
        return new EasyScreen(ImageUrlEnum.backgroundOfApply.getUrl(), window.getInt("x"), window.getInt("y"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player,Integer warTeamId) {
        EasyScreen gui = createGui(player,warTeamId);
        gui.openGui(player);
    }

    //创建VV组件
    public static EasyScreen createGui(Player player, Integer warTeamId) {
        EasyScreen gui = getGui();

        //关闭
        YamlConfiguration applyClose = VvGuiYml.getApplyClose();
        EasyButton applyCloseButton = new EasyButton( applyClose.getInt("x"), applyClose.getInt("y"), applyClose.getInt("width"), applyClose.getInt("high"), ImageUrlEnum.applyClose.getUrl(), PImageUrlEnum.applyClose.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                WarTeamGui.openGameLobbyGui(player);
            }
        };
        List<EasyComponent> easyComponents = new ArrayList<>();
        Map<String, Component> userComponent = WarTeamGui.getUserComponent();
        Component component = userComponent.get(player.getName());
        easyComponents = applyList(player, component, 1, warTeamId,1);
        //上一页
        YamlConfiguration shangyiye = VvGuiYml.getApplyShangyiye();
        EasyButton shangyiyeButton = new EasyButton( shangyiye.getInt("x"), shangyiye.getInt("y"), shangyiye.getInt("width"), shangyiye.getInt("high"), ImageUrlEnum.applyShangyiye.getUrl(), PImageUrlEnum.applyShangyiye.getUrl()) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                Component component = WarTeamGui.getUserComponent().get(player.getName());
                Integer currentPage = component.getCurrent();
                if (currentPage == 1) {
                    return;
                }
                applyList(player,component,currentPage - 1,warTeamId,2);
            }
        };

        //下一页
        YamlConfiguration xiayiye = VvGuiYml.getApplyXiayiye();
        EasyButton xiayiyeButton = new EasyButton( xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"),ImageUrlEnum.applyXiayiye.getUrl(), PImageUrlEnum.applyXiayiye.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                Component component = WarTeamGui.getUserComponent().get(player.getName());
                Integer limit = component.getCurrent() + 1;
                applyList(player,component,limit,warTeamId,2);
            }
        };

        gui.addComponent(applyCloseButton);
        gui.addComponent(shangyiyeButton);
        gui.addComponent(xiayiyeButton);
        if (easyComponents.size()> 0) {
            for (EasyComponent easyComponent : easyComponents) {
                gui.addComponent(easyComponent);
            }
        }
        return gui;
    }

    private static List<EasyComponent> applyList(Player player, Component component, Integer limit,Integer warTeamId,Integer type) {
        List<EasyComponent> list = new ArrayList<>();
        int currentPage = limit;
        //获取页数
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        Map<String, EasyComponent> components = openedScreen.getComponents();
        List<EasyComponent> applylist = component.getApplylist();
        limit = (limit - 1) * 6;
        YamlConfiguration applyList = VvGuiYml.getApplyList();
        YamlConfiguration applyAgree = VvGuiYml.getApplyAgree();
        YamlConfiguration applyRepulse = VvGuiYml.getApplyRepulse();
        YamlConfiguration nameYml = VvGuiYml.getApplyPlayerName();
        int applyAgreeY = applyAgree.getInt("y");
        int applyRepulseY = applyRepulse.getInt("y");
        int nameYmlY = nameYml.getInt("y");
        int applyListY = applyList.getInt("y");
        List<WarTeamApplyEntity> warTeamApplyEntities = WarTeamApplyDatabaseHandler.selectWarTeamApplyByWarTeamId(limit, warTeamId);
        if (warTeamApplyEntities.size() == 0) {
            player.sendMessage(Message.no_more_apply);
            return list;
        }

        for (EasyComponent easyComponent : applylist) {
            components.remove(easyComponent.getId());
        }

        for (WarTeamApplyEntity warTeamApplyEntity: warTeamApplyEntities) {
            String apply = warTeamApplyEntity.getUid();
            EasyLabel nameText = new EasyLabel(nameYml.getInt("x"), nameYmlY, 1,Arrays.asList(apply));
            EasyImage img = new EasyImage( applyList.getInt("x"), applyListY, applyList.getInt("width"), applyList.getInt("high"),ImageUrlEnum.applyModel.getUrl());
            //同意
            EasyButton applyAgreeButton = new EasyButton( applyAgree.getInt("x"), applyAgreeY, applyAgree.getInt("width"), applyAgree.getInt("high"),ImageUrlEnum.applyAgree.getUrl(), PImageUrlEnum.applyAgree.getUrl() ) {
                @Override
                public void onClick(Player player, Type type) {
                }
            };
            //不同意
            EasyButton applyRepulseButton = new EasyButton(applyRepulse.getInt("x"), applyRepulseY, applyRepulse.getInt("width"), applyRepulse.getInt("high"), ImageUrlEnum.applyRepulse.getUrl(), PImageUrlEnum.applyRepulse.getUrl()) {
                @Override
                public void onClick(Player player, Type type) {
                }
            };

            if (type == 1) {
                list.add(nameText);
                list.add(applyAgreeButton);
                list.add(applyRepulseButton);
                list.add(img);
                applylist.add(nameText);
                applylist.add(applyAgreeButton);
                applylist.add(applyRepulseButton);
                applylist.add(img);
            }else {
                components.put("nameText",nameText);
                components.put("applyAgreeButton",applyAgreeButton);
                components.put("applyRepulseButton",applyRepulseButton);
                components.put("img",img);
                applylist.add(nameText);
                applylist.add(applyAgreeButton);
                applylist.add(applyRepulseButton);
                applylist.add(img);
            }
            applyAgreeY = applyAgreeY + applyList.getInt("high") + 3;
            applyRepulseY = applyRepulseY + applyList.getInt("high")+ 3;
            nameYmlY = nameYmlY + applyList.getInt("high")+ 3;
            applyListY = applyListY + applyList.getInt("high")+ 3;
        }

        component.setCurrent(currentPage);
        component.setApplylist(applylist);
        Map<String, Component> userComponent = WarTeamGui.getUserComponent();
        userComponent.put(player.getName(),component);
        openedScreen.updateGui(player);
        return list;
    }
}