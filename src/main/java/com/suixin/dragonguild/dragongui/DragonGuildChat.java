package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.entity.DragonGuildApplyEntity;
import com.suixin.dragonguild.entity.DragonGuildChatEntity;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.*;


public class DragonGuildChat {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration window = DragonGuiYml.getApplyBackground();
        return new EasyScreen(ImageUrlEnum.backgroundOfApply.getUrl(), window.getInt("width"), window.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player,Integer dragonGuildId) {
        EasyScreen gui = createGui(player,dragonGuildId);
        gui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Player player, Integer dragonGuildId) {
        EasyScreen gui = getGui();

        //关闭
        YamlConfiguration applyClose = DragonGuiYml.getApplyClose();
        EasyButton applyCloseButton = new EasyButton( applyClose.getInt("x"), applyClose.getInt("y"), applyClose.getInt("width"), applyClose.getInt("high"), ImageUrlEnum.applyClose.getUrl(), PImageUrlEnum.applyClose.getUrl() ) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildGui.openGameLobbyGui(player);
            }
        };
        List<EasyComponent> easyComponents = new ArrayList<>();
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        Component component = userComponent.get(player.getName());
        easyComponents = applyList(player, component, 1, dragonGuildId,1);
        //聊天内容
        YamlConfiguration content = DragonGuiYml.getChatContent();
        final EasyTextField contentTextField = new EasyTextField(content.getInt("x"), content.getInt("y"), content.getInt("width"));
        //发送
        YamlConfiguration send = DragonGuiYml.getSend();
        EasyButton shangyiyeButton = new EasyButton( send.getInt("x"), send.getInt("y"), send.getInt("width"), send.getInt("high"), ImageUrlEnum.applyShangyiye.getUrl(), PImageUrlEnum.applyShangyiye.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                DragonGuildChatEntity dragonGuildChatEntity = new DragonGuildChatEntity();
                dragonGuildChatEntity.setCreator(player.getName());
                dragonGuildChatEntity.setGuildId(dragonGuildId);
                dragonGuildChatEntity.setDesc(contentTextField.getText());
                dragonGuildChatEntity.setUid(player.getUniqueId().toString());
                dragonGuildChatEntity.setStatus(1);
                dragonGuildChatEntity.setCreated(new Date());
            }
        };

        gui.addComponent(applyCloseButton);
        gui.addComponent(shangyiyeButton);
        if (easyComponents.size()> 0) {
            for (EasyComponent easyComponent : easyComponents) {
                gui.addComponent(easyComponent);
            }
        }
        return gui;
    }

    private static List<EasyComponent> applyList(Player player, Component component, Integer limit,Integer dragonGuildId,Integer type) {
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
        int applyAgreeY = applyAgree.getInt("y");
        int applyRepulseY = applyRepulse.getInt("y");
        int nameYmlY = nameYml.getInt("y");
        int applyListY = applyList.getInt("y");
        List<DragonGuildApplyEntity> dragonGuildApplyEntities = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByDragonGuildId(limit, dragonGuildId);
        if (dragonGuildApplyEntities.size() == 0) {
            player.sendMessage(Message.no_more_apply);
            return list;
        }

        for (String easyComponent : applylist) {
            components.remove(easyComponent);
        }

        for (DragonGuildApplyEntity dragonGuildApplyEntity: dragonGuildApplyEntities) {
            String apply = dragonGuildApplyEntity.getUid();
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
                list.add(img);
                list.add(applyRepulseButton);
                list.add(applyAgreeButton);
                list.add(nameText);
            }else {
                components.put("nameText",nameText);
                components.put("applyAgreeButton#"+apply,applyAgreeButton);
                components.put("applyRepulseButton"+apply,applyRepulseButton);
                components.put("img",img);
            }
            applyAgreeY = applyAgreeY + applyList.getInt("high") + 3;
            applyRepulseY = applyRepulseY + applyList.getInt("high")+ 3;
            nameYmlY = nameYmlY + applyList.getInt("high")+ 3;
            applyListY = applyListY + applyList.getInt("high")+ 3;
        }

        component.setCurrent(currentPage);
        component.setApplyList(applylist);
        Map<String, Component> userComponent = DragonGuildGui.getUserComponent();
        userComponent.put(player.getName(),component);
        return list;
    }
}