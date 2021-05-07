package com.suixin.warteam;

import com.google.common.base.Charsets;
import com.suixin.warteam.command.BaseCommand;
import com.suixin.warteam.command.TestCommand;
import com.suixin.warteam.handler.WarTeamApplyDatabaseHandler;
import com.suixin.warteam.handler.WarTeamDatabaseHandler;
import com.suixin.warteam.handler.WarTeamMemBerDatabaseHandler;
import com.suixin.warteam.listener.ButtonClickListener;
import com.suixin.warteam.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import lk.vexview.VexView;
import lk.vexview.api.VexViewAPI;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

public class WarTeam extends JavaPlugin {
	private static WarTeam instance;
	private static YamlConfiguration systemConfig;
	private static HashMap<Integer,Integer> levelMap = new HashMap<>();
	private static HashMap<Integer,Integer> maxNumMap = new HashMap<>();
	@Override
	public void onEnable() {

		instance = this;
		//初始化配置文件
		getCommand("wt").setExecutor(new BaseCommand());
		getCommand("test").setExecutor(new TestCommand());
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"==================[WarTeam]==================");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"第一次运行，请先检查配置文件");
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"作者QQ：2469012478");
		boolean economy = VaultAPI.setupEconomy();
		if (economy) {
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"Vault已连接");
		}else{
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW +"Vault未找到,插件将无法正常运行");
		}
		//检测VV是否开启
		if ( Bukkit.getServer().getPluginManager().isPluginEnabled("VexView")) {
			//获取前置版本
			VexView vexView = VexViewAPI.getVexView();
			String version = vexView.getVersion();
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"VexView已连接");
			Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"当前VV版本："+version);
			//点击事件
//				Bukkit.getPluginManager().registerEvents(new VvEventListener(),this);
			//按键事件
			Bukkit.getPluginManager().registerEvents(new ButtonClickListener(),this);
		}else {
			Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW+"未找到VexView，插件将无法正常运行");
		}
		Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN +"==================[WarTeam]==================");
		loadPlugin(null);
		if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			new SomeExpansion(this).register();
		}
	}
	// 加载配置
	public static void loadPlugin(Player player) {
		// 读取config配置文件
		systemConfig = getYml("config.yml");
		String level = systemConfig.getString("WarTeam.level");
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
		if(!VvGuiYml.loadGui()) {
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

				Integer count = WarTeamApplyDatabaseHandler.selectTabCount();
				Integer count1 = WarTeamDatabaseHandler.selectTabCount();
				Integer count2 = WarTeamMemBerDatabaseHandler.selectTabCount();
				if (count == 0) {
					//如果表不存在，则初始化表
					WarTeamApplyDatabaseHandler.createTable();
				}
				if (count1 == 0) {
					WarTeamDatabaseHandler.createTable();
				}
				if (count2 == 0) {
					WarTeamMemBerDatabaseHandler.createTable();
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
				WarTeam.getInstance().saveResource(yml, false);
			}
			config.load(new BufferedReader(new InputStreamReader(new FileInputStream(file), Charsets.UTF_8)));
			return YamlConfiguration.loadConfiguration(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static WarTeam getInstance() {
		return instance;
	}

	public static void setInstance(WarTeam instance) {
		WarTeam.instance = instance;
	}

	public static YamlConfiguration getSystemConfig() {
		return systemConfig;
	}

	public static void setSystemConfig(YamlConfiguration systemConfig) {
		WarTeam.systemConfig = systemConfig;
	}

	public static HashMap<Integer, Integer> getLevelMap() {
		return levelMap;
	}

	public static void setLevelMap(HashMap<Integer, Integer> levelMap) {
		WarTeam.levelMap = levelMap;
	}

	public static HashMap<Integer, Integer> getMaxNumMap() {
		return maxNumMap;
	}

	public static void setMaxNumMap(HashMap<Integer, Integer> maxNumMap) {
		WarTeam.maxNumMap = maxNumMap;
	}
}
