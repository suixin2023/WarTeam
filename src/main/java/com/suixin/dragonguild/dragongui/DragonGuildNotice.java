package com.suixin.dragonguild.dragongui;


import com.suixin.dragonguild.entity.DragonGuildNoticeEntity;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildNoticeDatabaseHandler;
import com.suixin.dragonguild.util.DragonGuiYml;
import com.suixin.dragonguild.util.ImageUrlEnum;
import com.suixin.dragonguild.util.PImageUrlEnum;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.EasyButton;
import eos.moe.dragoncore.api.easygui.component.EasyLabel;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class DragonGuildNotice {
    //创建GUI
    public static EasyScreen getGui() {
        YamlConfiguration window = DragonGuiYml.getApplyBackground();
        return new EasyScreen(ImageUrlEnum.backgroundOfApply.getUrl(), window.getInt("width"), window.getInt("high"));
    }

    //打开GUI
    public static void openGameLobbyGui(Player player, Integer dragonGuildId) {
        EasyScreen gui = createGui(player,dragonGuildId);
        gui.openGui(player);
    }

    //创建组件
    public static EasyScreen createGui(Player player, Integer dragonGuildId) {
        EasyScreen gui = getGui();
        DragonGuildNoticeEntity dragonGuildNoticeEntity = DragonGuildNoticeDatabaseHandler.selectDragonGuildByGuildId(dragonGuildId);
        YamlConfiguration title = DragonGuiYml.getTitle();
        EasyLabel titleText = new EasyLabel(title.getInt("x"), title.getInt("y"), 1, Arrays.asList(dragonGuildNoticeEntity.getTitle()));
        String desc = dragonGuildNoticeEntity.getDesc();
        String[] split = desc.split("#");
        YamlConfiguration chatContent = DragonGuiYml.getChatContent();
        EasyLabel chatContentText = new EasyLabel(chatContent.getInt("x"), chatContent.getInt("y"), 1, Arrays.asList(split));
        gui.addComponent(titleText);
        gui.addComponent(chatContentText);
        return gui;
    }
}