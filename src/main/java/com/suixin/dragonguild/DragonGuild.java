package com.suixin.dragonguild;

import com.google.common.base.Charsets;
import com.suixin.dragonguild.command.BaseCommand;
import com.suixin.dragonguild.dragongui.DragonGuildNotice;
import com.suixin.dragonguild.handler.*;
import com.suixin.dragonguild.listener.AppointButtonClickListener;
import com.suixin.dragonguild.listener.EasyButtonClickListener;
import com.suixin.dragonguild.task.ActivationCodeTask;
import com.suixin.dragonguild.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DragonGuild extends JavaPlugin {
	private static DragonGuild instance;
	private static YamlConfiguration systemConfig;
	private static HashMap<Integer,Integer> levelMap = new HashMap<>();
	private static HashMap<Integer,Integer> maxNumMap = new HashMap<>();
	private static List<String> updateContent = new ArrayList<>();
	private static String version;

	@Override
	public void onEnable() {
		instance = this;
		//初始化配置文件
		version = JavaPlugin.getPlugin(DragonGuild.class).getDescription().getVersion();
		getCommand("gh").setExecutor(new BaseCommand());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"==================[DragonGuild]==================");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"第一次运行，请先检查配置文件");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"作者QQ：2469012478");
		boolean economy = VaultAPI.setupEconomy();
		if (economy) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"Vault已连接");
		}else{
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW +"Vault未找到,插件将无法正常运行");
		}
		//检测是否开启
		if ( Bukkit.getServer().getPluginManager().isPluginEnabled("DragonCore")) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"DragonCore已连接");
			Bukkit.getPluginManager().registerEvents(new EasyButtonClickListener(),this);
			Bukkit.getPluginManager().registerEvents(new AppointButtonClickListener(),this);
		}else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"未找到DragonCore，插件将无法正常运行");
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"==================[DragonGuild]==================");
		loadPlugin(null);
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new SomeExpansion(this).register();
		}
		ActivationCodeTask.timeTask(this);
	}
	// 加载配置
	public static void loadPlugin(Player player) {
		// 读取config配置文件
		systemConfig = getYml("config.yml");
		Boolean ss = systemConfig.getBoolean("DragonGuild.position.god_of_war.permission.kick",false);
		Bukkit.getConsoleSender().sendMessage("§a测试配置读取"+ss);
		String level = systemConfig.getString("DragonGuild.level");
		level = level.substring(1,level.length() -1).replace(" ","");
		String[] levelSplit = level.split(",");
		for (String s : levelSplit) {
			String[] split = s.split(":");
			levelMap.put(Integer.valueOf(split[0]),Integer.valueOf(split[1]));
			maxNumMap.put(Integer.valueOf(split[0]),Integer.valueOf(split[2]));
		}
		// 加载全局消息
		if(!Message.loadMessage()) {
			Bukkit.getConsoleSender().sendMessage("§c载入语言文件异常,请检查");
			return;
		}
		Bukkit.getConsoleSender().sendMessage("§a载入语言文件");
		if(!DragonGuiYml.loadGui()) {
			Bukkit.getConsoleSender().sendMessage("§c载入gui文件异常,请检查");
			return;
		}
		Bukkit.getConsoleSender().sendMessage("§a载入GUI文件");
		if (player != null) {
			player.sendMessage("§a重载成功");
		}

		try {
			//加载数据库驱动
			boolean connection = MysqlUtil.openConnection(systemConfig);
			if (connection) {
				Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + "Mysql已连接");

				Integer count = DragonGuildApplyDatabaseHandler.selectTabCount();
				Integer count1 = DragonGuildDatabaseHandler.selectTabCount();
				Integer count2 = DragonGuildMemBerDatabaseHandler.selectTabCount();
				Integer count3 = DragonGuildChatDatabaseHandler.selectTabCount();
				Integer count4 = DragonGuildNoticeDatabaseHandler.selectTabCount();
				if (count == 0) {
					//如果表不存在，则初始化表
					DragonGuildApplyDatabaseHandler.createTable();
				}
				if (count1 == 0) {
					DragonGuildDatabaseHandler.createTable();
				}
				if (count2 == 0) {
					DragonGuildMemBerDatabaseHandler.createTable();
				}
				if (count3 == 0) {
					DragonGuildChatDatabaseHandler.createTable();
				}
				if (count4 == 0) {
					DragonGuildNoticeDatabaseHandler.createTable();
				}
			}
		}catch (Exception e){
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED +"Mysql连接失败:"+e.getMessage());
			Bukkit.getConsoleSender().sendMessage(ChatColor.RED +"如有疑问，请联系作者：QQ:2469012478");
		}
	}

	public static YamlConfiguration getYml(String yml){
		try {
			YamlConfiguration config = new YamlConfiguration();
			File file = new File(getInstance().getDataFolder(), yml);
			if (!file.exists()) {
				DragonGuild.getInstance().saveResource(yml, false);
			}
			config.load(new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8)));
			return YamlConfiguration.loadConfiguration(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static DragonGuild getInstance() {
		return instance;
	}

	public static void setInstance(DragonGuild instance) {
		DragonGuild.instance = instance;
	}

	public static YamlConfiguration getSystemConfig() {
		return systemConfig;
	}

	public static void setSystemConfig(YamlConfiguration systemConfig) {
		DragonGuild.systemConfig = systemConfig;
	}

	public static HashMap<Integer, Integer> getLevelMap() {
		return levelMap;
	}

	public static void setLevelMap(HashMap<Integer, Integer> levelMap) {
		DragonGuild.levelMap = levelMap;
	}

	public static HashMap<Integer, Integer> getMaxNumMap() {
		return maxNumMap;
	}

	public static void setMaxNumMap(HashMap<Integer, Integer> maxNumMap) {
		DragonGuild.maxNumMap = maxNumMap;
	}

	public static List<String> getUpdateContent() {
		return updateContent;
	}

	public static void setUpdateContent(List<String> updateContent) {
		DragonGuild.updateContent = updateContent;
	}

	public static String getVersion() {
		return version;
	}

	public static void setVersion(String version) {
		DragonGuild.version = version;
	}

	public static void main(String[] args) {

	}
}
