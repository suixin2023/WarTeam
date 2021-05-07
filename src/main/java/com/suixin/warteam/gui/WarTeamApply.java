package com.suixin.warteam.gui;

import com.suixin.warteam.entity.WarTeamApplyEntity;
import com.suixin.warteam.handler.WarTeamApplyDatabaseHandler;
import com.suixin.warteam.util.*;
import lk.vexview.api.VexViewAPI;
import lk.vexview.gui.OpenedVexGui;
import lk.vexview.gui.VexGui;
import lk.vexview.gui.components.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class WarTeamApply {
    //创建GUI
    public static VexGui getGui() {
        YamlConfiguration window = VvGuiYml.getApplyBackground();
        VexGui vexGui = new VexGui(ImageUrlEnum.backgroundOfApply.getUrl(), window.getInt("x"), window.getInt("y"), window.getInt("width"), window.getInt("high"));
        return vexGui;
    }

    //打开GUI
    public static void openGameLobbyGui(Player p,Integer warTeamId) {
        VexViewAPI.openGui(p, createGui(p,warTeamId));
    }

    //创建VV组件
    public static VexGui createGui(Player player,Integer warTeamId) {
        VexGui gui = getGui();

        //关闭
        YamlConfiguration applyClose = VvGuiYml.getApplyClose();
        VexButton applyCloseButton = new VexButton("applyCloseButton", "", ImageUrlEnum.applyClose.getUrl(), PImageUrlEnum.applyClose.getUrl(), applyClose.getInt("x"), applyClose.getInt("y"), applyClose.getInt("width"), applyClose.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                WarTeamGui.openGameLobbyGui(player);
            }
        });
        List<VexComponents> vexComponents = new ArrayList<>();
        Map<String, Component2> userComponent = WarTeamGui.getUserComponent();
        Component2 component = userComponent.get(player.getName());
        vexComponents = applyList(player, component, 1, warTeamId,1);
        //上一页
        YamlConfiguration shangyiye = VvGuiYml.getApplyShangyiye();
        VexButton shangyiyeButton = new VexButton("shangyiyeButton", "", ImageUrlEnum.applyShangyiye.getUrl(), PImageUrlEnum.applyShangyiye.getUrl(), shangyiye.getInt("x"), shangyiye.getInt("y"), shangyiye.getInt("width"), shangyiye.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                Component2 component = WarTeamGui.getUserComponent().get(player.getName());
                Integer currentPage = component.getCurrent();
                if (currentPage == 1) {
                    return;
                }
                applyList(player,component,currentPage - 1,warTeamId,2);
            }
        });

        //下一页
        YamlConfiguration xiayiye = VvGuiYml.getApplyXiayiye();
        VexButton xiayiyeButton = new VexButton("xiayiyeButton", "", ImageUrlEnum.applyXiayiye.getUrl(), PImageUrlEnum.applyXiayiye.getUrl(), xiayiye.getInt("x"), xiayiye.getInt("y"), xiayiye.getInt("width"), xiayiye.getInt("high"), new ButtonFunction() {
            @Override
            public void run(Player player) {
                Component2 component = WarTeamGui.getUserComponent().get(player.getName());
                Integer limit = component.getCurrent() + 1;
                applyList(player,component,limit,warTeamId,2);
            }
        });

        gui.addComponent(applyCloseButton);
        gui.addComponent(shangyiyeButton);
        gui.addComponent(xiayiyeButton);
        if (vexComponents.size()> 0) {
            for (VexComponents vexComponent : vexComponents) {
                gui.addComponent(vexComponent);
            }
        }
        return gui;
    }

    private static List<VexComponents> applyList(Player player, Component2 component, Integer limit,Integer warTeamId,Integer type) {
        List<VexComponents> list = new ArrayList<>();
        int currentPage = limit;
        //获取页数
        OpenedVexGui opg = VexViewAPI.getPlayerCurrentGui(player);
        List<DynamicComponent> applylist = component.getApplylist();
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

        for (DynamicComponent dynamicComponent : applylist) {
            opg.removeDynamicComponent(dynamicComponent);
        }

        for (WarTeamApplyEntity warTeamApplyEntity: warTeamApplyEntities) {
            String apply = warTeamApplyEntity.getUid();
            VexText nameText = new VexText(nameYml.getInt("x"), nameYmlY, Arrays.asList(apply),1.0);
            VexImage img = new VexImage(ImageUrlEnum.applyModel.getUrl(), applyList.getInt("x"), applyListY, applyList.getInt("width"), applyList.getInt("high"));
            //同意
            VexButton applyAgreeButton = new VexButton("applyAgreeButton#"+apply, "", ImageUrlEnum.applyAgree.getUrl(), PImageUrlEnum.applyAgree.getUrl(), applyAgree.getInt("x"), applyAgreeY, applyAgree.getInt("width"), applyAgree.getInt("high"), new ButtonFunction() {
                @Override
                public void run(Player player) {
                }
            });
            //不同意
            VexButton applyRepulseButton = new VexButton("applyRepulseButton#"+apply, "", ImageUrlEnum.applyRepulse.getUrl(), PImageUrlEnum.applyRepulse.getUrl(), applyRepulse.getInt("x"), applyRepulseY, applyRepulse.getInt("width"), applyRepulse.getInt("high"), new ButtonFunction() {
                @Override
                public void run(Player player) {
                }
            });

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
                opg.addDynamicComponent(nameText);
                opg.addDynamicComponent(applyAgreeButton);
                opg.addDynamicComponent(applyRepulseButton);
                opg.addDynamicComponent(img);
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
        Map<String, Component2> userComponent = WarTeamGui.getUserComponent();
        userComponent.put(player.getName(),component);
        return list;
    }
}