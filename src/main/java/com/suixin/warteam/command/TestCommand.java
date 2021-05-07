package com.suixin.warteam.command;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import com.google.common.collect.ImmutableList;
import eos.moe.dragoncore.DragonCore;
import eos.moe.dragoncore.api.easygui.EasyScreen;
import eos.moe.dragoncore.api.easygui.component.*;
import eos.moe.dragoncore.api.easygui.component.listener.ClickListener.Type;
import eos.moe.dragoncore.command.CommandBase;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class TestCommand implements CommandExecutor {
    public void onConsoleCommand(CommandSender sender, String[] args) {
        Player player = (Player) Bukkit.getOnlinePlayers().stream().findFirst().get();
        EasyScreen openedScreen = EasyScreen.getOpenedScreen(player);
        EasyTextField bjk = (EasyTextField)openedScreen.getComponent("编辑框");
        bjk.setText("我打你吗的");
        Map<String, EasyComponent> components = openedScreen.getComponents();
        openedScreen.getComponents().remove("文本");
        openedScreen.updateGui(player);
    }
    public void onPlayerCommand(Player player, String[] args) {
        EasyScreen screen = new EasyScreen("a.png", 400, 250);
        screen.addComponent(new EasyButton(5, 5, 10, 10, "按钮框.png", "inventory.png") {
            @Override
            public void onClick(Player player, Type type) {
                player.sendMessage("你点击了喵喵" + type.name());
            }
        });
        screen.addComponent("文本", new EasyLabel(5, 15, 1, ImmutableList.of("§c哇哦", "§c牛逼")));
        EasyTextField textField = new EasyTextField(5, 35, 50, "秒了个米的");
        screen.addComponent("编辑框", textField);
        screen.addComponent(new EasySlot(5, 70, new ItemStack(Material.CAKE)));
        screen.addComponent(new EasyEntityView(40, 20, player.getUniqueId()));
        screen.addComponent(new EasyImage(60, 0, 50, 50, "按钮框.png"));
        EasyScrollingList list = new EasyScrollingList(100, 30, 196, 212, "0,102,255,255");
        list.setBar(10, 28, 500, "b.png");
        list.addComponent(new EasyLabel(5, 185, ImmutableList.of("哇哦", "牛逼")));
        screen.addComponent(list);
        screen.openGui(player);
        Bukkit.getScheduler().runTaskLater(DragonCore.getInstance(), () -> {
            player.sendMessage("5秒过去了,当前文本框的内容为" + textField.getText());
        }, 100L);
    }
    public String getPermission() {
        return "core.command.test";
    }
    public String getCommandDesc() {
        return "/core test";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        Player player = null;
        if (sender instanceof Player) {
            player = (Player) sender;
        } else {
            sender.sendMessage("错误，这是一个玩家指令!");
            return true;
        }
        if (command.getName().equalsIgnoreCase("test")) {
            EasyScreen screen = new EasyScreen("a.png", 400, 250);
            screen.addComponent(new EasyButton(5, 5, 10, 10, "按钮框.png", "inventory.png") {
                @Override
                public void onClick(Player player, Type type) {
                    player.sendMessage("你点击了喵喵" + type.name());
                }
            });
            screen.addComponent("文本", new EasyLabel(5, 15, 1, ImmutableList.of("§c哇哦", "§c牛逼")));
            EasyTextField textField = new EasyTextField(5, 35, 50, "秒了个米的");
            screen.addComponent("编辑框", textField);
            screen.addComponent(new EasySlot(5, 70, new ItemStack(Material.CAKE)));
            screen.addComponent(new EasyEntityView(40, 20, player.getUniqueId()));
            screen.addComponent(new EasyImage(60, 0, 50, 50, "按钮框.png"));
            EasyScrollingList list = new EasyScrollingList(100, 30, 196, 212, "inventory.png");
            list.setBar(10, 28, 500, "b.png");
            list.addComponent(new EasyLabel(5, 185, ImmutableList.of("哇哦", "牛逼")));
            list.addComponent(textField);
            screen.addComponent(list);
            screen.openGui(player);
            Player finalPlayer = player;
            Bukkit.getScheduler().runTaskLater(DragonCore.getInstance(), () -> {
                finalPlayer.sendMessage("5秒过去了,当前文本框的内容为" + textField.getText());
            }, 100L);
        }
        return true;
    }
}
