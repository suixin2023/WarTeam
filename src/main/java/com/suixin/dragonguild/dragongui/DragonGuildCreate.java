package com.suixin.dragonguild.dragongui;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.entity.DragonGuildApplyEntity;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.entity.DragonGuildNoticeEntity;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildNoticeDatabaseHandler;
import com.suixin.dragonguild.util.*;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import eos.moe.dragoncore.api.easygui.component.EasyImage;
import eos.moe.dragoncore.api.easygui.component.EasyTextField;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.HashMap;


public class DragonGuildCreate {
    private static HashMap<Integer, Integer> levelMap = DragonGuild.getLevelMap();
    private static HashMap<Integer, Integer> maxNumMap = DragonGuild.getMaxNumMap();
    //创建GUI
    public static EasyScreen getGui(Integer type) {
        YamlConfiguration mainGui = DragonGuiYml.getBackground();
        return new EasyScreen(ImageUrlEnum.background.getUrl(), mainGui.getInt("width"), mainGui.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player, Integer type) {
        EasyScreen lhdGui = createGui(type);
        lhdGui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Integer type1) {
        EasyScreen gui = getGui(type1);
        //返回
        YamlConfiguration back = DragonGuiYml.getBack();
        EasyButton backButton = new EasyButton( back.getInt("x"), back.getInt("y"), back.getInt("width"), back.getInt("high"), ImageUrlEnum.back.getUrl(), PImageUrlEnum.back.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildNoTeam.openGameLobbyGui(player);
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
        //取消
        YamlConfiguration cancel = DragonGuiYml.getCancel();
        EasyButton cancelButton = new EasyButton( cancel.getInt("x"), cancel.getInt("y"), cancel.getInt("width"), cancel.getInt("high"), ImageUrlEnum.cancel.getUrl(), PImageUrlEnum.cancel.getUrl() ) {
            @Override
            public void onClick(Player player, ClickListener.Type type) {
                DragonGuildNoTeam.openGameLobbyGui(player);
            }
        };

        //聊天内容
        YamlConfiguration shurukuang = DragonGuiYml.getShurukuang();
        EasyTextField contentTextField = new EasyTextField(shurukuang.getInt("x"), shurukuang.getInt("y"), shurukuang.getInt("width"),"请输入");


        //确定键
        YamlConfiguration confirm = DragonGuiYml.getConfirm();
        EasyButton confirmButton = new EasyButton( confirm.getInt("x"), confirm.getInt("y"), confirm.getInt("width"), confirm.getInt("high"),  ImageUrlEnum.confirm.getUrl(), PImageUrlEnum.confirm.getUrl()) {
            @Override
            public void onClick(Player player, Type type) {
                if (type1 == 1){
                    boolean b = createDragonGuild(contentTextField.getText(), player);
                    if (b) {
                        DragonGuildGui.openGameLobbyGui(player);
                    }
                }else if (type1 == 2) {
                    boolean b = joinDragonGuild(contentTextField.getText(), player);
                    if (b) {
                        DragonGuildGui.openGameLobbyGui(player);
                    }
                }
            }
        };
        YamlConfiguration window = DragonGuiYml.getWindow();
        EasyImage img = new EasyImage( window.getInt("x"), window.getInt("y"), window.getInt("width"), window.getInt("high"),ImageUrlEnum.window.getUrl());
        gui.addComponent(img);
        gui.addComponent(contentTextField);
        gui.addComponent(closeButton);
        gui.addComponent(backButton);
        gui.addComponent(cancelButton);
        gui.addComponent(confirmButton);
        return gui;
    }

    private static boolean joinDragonGuild(String guildName, Player player) {
        DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
        if (dragonGuildMemberEntity.getId() != null) {
            player.sendMessage(Message.apply_join2+dragonGuildMemberEntity.getDragonGuildName());
            return false;
        }
        DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(guildName);
        if (dragonGuildEntity.getId() == null) {
            player.sendMessage(Message.team_inexistence);
            return false;
        }
        Integer count = DragonGuildMemBerDatabaseHandler.selectCount(dragonGuildEntity.getId());
        if (dragonGuildEntity.getMaxMember().equals(count)) {
            player.sendMessage(Message.team_full);
            return false;
        }
        if (player.getName().equals(dragonGuildEntity.getCreator())) {
            player.sendMessage(Message.cant_join_your_team);
            return false;
        }
        DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUid(player.getName(), guildName);
        if (dragonGuildApplyEntity.getId() != null) {
            player.sendMessage(Message.apply_failure);
            return false;
        }
        dragonGuildApplyEntity.setUid(player.getName());
        dragonGuildApplyEntity.setDragonGuildId(dragonGuildEntity.getId());
        dragonGuildApplyEntity.setDragonGuildName(dragonGuildEntity.getName());
        dragonGuildApplyEntity.setApply(dragonGuildEntity.getCreator());
        dragonGuildApplyEntity.setStatus(0);
        dragonGuildApplyEntity.setCreated(new Date());
        DragonGuildApplyDatabaseHandler.insert(dragonGuildApplyEntity);
        player.sendMessage(Message.apply_successful);
        return true;
    }

    private static boolean createDragonGuild (String guildName, Player player) {
        DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(guildName);
        if (dragonGuildEntity.getId() != null) {
            player.sendMessage(Message.create_failure);
            return false;
        }
        DragonGuildEntity dragonGuildEntity2 = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
        if (dragonGuildEntity2.getId() != null) {
            player.sendMessage(Message.team_already_exist);
            return false;
        }
        double money = VaultAPI.getMoney(player.getName());
        Double amount = new Double(DragonGuild.getSystemConfig().getString("DragonGuild.cost"));
        if (money < amount) {
            player.sendMessage(DragonGuild.getSystemConfig().getString("DragonGuild.prefix") +"§c您没有足够的金币！");
            return false;
        }

        dragonGuildEntity.setName(guildName);
        dragonGuildEntity.setLevel(1);
        //一级最大成员数
        dragonGuildEntity.setMaxMember(maxNumMap.get(1));
        dragonGuildEntity.setUid(player.getUniqueId().toString());
        dragonGuildEntity.setCreator(player.getName());
        dragonGuildEntity.setExpAll(0);
        //升二级所需经验
        dragonGuildEntity.setExpCurrent(levelMap.get(2));
        dragonGuildEntity.setStatus(1);
        dragonGuildEntity.setCreated(new Date());
        dragonGuildEntity.setModified(new Date());
        int res = DragonGuildDatabaseHandler.insert(dragonGuildEntity);
        DragonGuildEntity dragonGuildEntity3 = DragonGuildDatabaseHandler.selectDragonGuildByName(guildName);
        DragonGuildMemberEntity dragonGuildMemberEntity = new DragonGuildMemberEntity();
        dragonGuildMemberEntity.setDragonGuildId(dragonGuildEntity3.getId());
        dragonGuildMemberEntity.setDragonGuildName(dragonGuildEntity3.getName());
        dragonGuildMemberEntity.setExp(0);
        dragonGuildMemberEntity.setUid(player.getName());
        dragonGuildMemberEntity.setStatus(1);
        dragonGuildMemberEntity.setCreated(new Date());
        DragonGuildMemBerDatabaseHandler.insert(dragonGuildMemberEntity);
        player.sendMessage(Message.create_successful);
        VaultAPI.removeMoney(player.getName(),amount);
        DragonGuildNoticeEntity dragonGuildNoticeEntity = new DragonGuildNoticeEntity();
        DragonGuildEntity dragonGuildEntity1 = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
        Integer id = dragonGuildEntity1.getId();
        dragonGuildNoticeEntity.setGuildId(id);
        dragonGuildNoticeEntity.setCreated(new Date());
        dragonGuildNoticeEntity.setTitle("§a["+guildName+"]"+"公会成立啦!");
        dragonGuildNoticeEntity.setDescs("§b兄弟们多多拉人#§c做大做强,再创辉煌!");
        dragonGuildNoticeEntity.setUid(player.getUniqueId().toString());
        dragonGuildNoticeEntity.setCreator(player.getName());
        dragonGuildNoticeEntity.setStatus(1);
        DragonGuildNoticeDatabaseHandler.insert(dragonGuildNoticeEntity);
        return true;
    }
}