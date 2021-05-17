package com.suixin.dragonguild.command;

import com.suixin.dragonguild.DragonGuild;
import com.suixin.dragonguild.dragongui.DragonGuildGui;
import com.suixin.dragonguild.entity.DragonGuildApplyEntity;
import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.entity.DragonGuildNoticeEntity;
import com.suixin.dragonguild.handler.DragonGuildApplyDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildMemBerDatabaseHandler;
import com.suixin.dragonguild.handler.DragonGuildNoticeDatabaseHandler;
import com.suixin.dragonguild.util.Message;
import com.suixin.dragonguild.util.VaultAPI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;

public class BaseCommand implements CommandExecutor {
	HashMap<Integer, Integer> levelMap = DragonGuild.getLevelMap();
	HashMap<Integer, Integer> maxNumMap = DragonGuild.getMaxNumMap();
	@Override
	public boolean onCommand(final CommandSender sender, final Command cmd, final String arg, final String[] args) {
		Player player = null;
		if (sender instanceof Player) {
			player = (Player) sender;
		} else {
			sender.sendMessage("错误，这是一个玩家指令!");
			return true;
		}
		boolean op = player.isOp();
		if (cmd.getName().equalsIgnoreCase("gh")) {
			if (args.length == 0) {
				player.sendMessage("§c§l§m §c§l§m §c§l§m §c§l§m §6§l§m §6§l§m §6§l§m §6§l§m §e§l§m §e§l§m §e§l§m §e§l§m §a§l§m §a§l§m §a§l§m §a§l§m §b§l§m §b§l§m §b§l§m §b§l§m §b§l§m §b§l§m §a§l§m §a§l§m §a§l§m §a§l§m §e§l§m §e§l§m §e§l§m §e§l§m §6§l§m §6§l§m §6§l§m §6§l§m §c§l§m §c§l§m §c§l§m §c§l§m");
				player.sendMessage("§6●§a/gh open §e打开公会管理");
				player.sendMessage("§6●§a/gh list <页码> §e打开公会列表");
				player.sendMessage("§6●§a/gh create <公会> §e创建公会!");
				player.sendMessage("§6●§a/gh update <公会> §e修改公会名!");
				player.sendMessage("§6●§a/gh join <公会> §e加入一个公会!");
				player.sendMessage("§6●§a/gh dismiss  §e解散公会!");
				player.sendMessage("§6●§a/gh out  §e退出公会!");
				player.sendMessage("§6●§a/gh out <玩家> §e踢出公会!");
				player.sendMessage("§6●§a/gh agree <玩家> §e同意玩家加入公会!");
				player.sendMessage("§6●§a/gh refuse <玩家> §e拒绝玩家加入公会!");
				if (op) {
					player.sendMessage("§6●§a/gh reload §e重载插件!");
				}
				player.sendMessage("§c§l§m §c§l§m §c§l§m §c§l§m §6§l§m §6§l§m §6§l§m §6§l§m §e§l§m §e§l§m §e§l§m §e§l§m §a§l§m §a§l§m §a§l§m §a§l§m §b§l§m §b§l§m §b§l§m §b§l§m §b§l§m §b§l§m §a§l§m §a§l§m §a§l§m §a§l§m §e§l§m §e§l§m §e§l§m §e§l§m §6§l§m §6§l§m §6§l§m §6§l§m §c§l§m §c§l§m §c§l§m §c§l§m");
				return true;
			}
			List<String> argsList = Arrays.asList(args);
			String arg1 = argsList.get(0);
			if (arg1.equals("open")) {
				//打开公会
				DragonGuildGui.openGameLobbyGui(player);
			} else if (arg1.equals("list")) {
				//打开公会列表
				dragonGuildList(argsList,player);
			} else if (arg1.equals("create")) {
				//创建公会
				createDragonGuild(argsList,player);
			} else if (arg1.equals("join")) {
				//加入一个公会
				joinDragonGuild(argsList,player);
			} else if (arg1.equals("dismiss")) {
				//解散公会
				dismissDragonGuild(argsList,player);
			} else if (arg1.equals("out")) {
				//退出公会
				outDragonGuild(argsList,player);
			} else if (arg1.equals("agree")) {
				//同意玩家加入公会
				applyPassDragonGuild(argsList,player);
			} else if (arg1.equals("refuse")) {
				//拒绝玩家加入公会!
				applyNoPassDragonGuild(argsList,player);
			} else if (arg1.equals("update")) {
				//修改公会名
				updateDragonGuild(argsList,player);
			}else if (arg1.equals("out") && argsList.size() == 2) {
				//踢出公会
				kickOutPlayer(argsList,player);
			}else if (arg1.equals("reload")) {
				if (!op) {
					player.sendMessage("§c无权限");
				}
				//重载插件
				DragonGuild.loadPlugin(player);
			}
		}
		return true;
	}

	private void dragonGuildList ( List<String> argsList,Player player) {
		if (argsList.size() > 2){
			player.sendMessage("§c指令不正确");
			return;
		}
		Integer count = DragonGuildDatabaseHandler.selectTeamCount();
		int j = count / 5;
		int i = count % 5;
		Integer countnum;
		if (i == 0) {
			countnum = j;
		} else {
			countnum = j + 1;
		}
		if (count < 5) {
			countnum = 1;
		}
		if (argsList.size() == 1) {
			if (1 > countnum) {
				player.sendMessage(Message.no_more_team);
				return;
			}
			//查询第一页
			List<DragonGuildEntity> dragonGuildEntities = DragonGuildDatabaseHandler.selectDragonGuildDataNum(0);
			player.sendMessage("§c§l§m §c§l§m §c§l§m §c§l§m §6§l§m §6§l§m §6§l§m §6§l§m §e§l§m §e§l§m §e§l§m §e§l§m §a§l§m §a§l§m §a§l§m §a§l§m §b§l§m §b§l§m §b§l§m §e【§a§l公会列表§e】 §b§l§m §b§l§m §b§l§m §a§l§m §a§l§m §a§l§m §a§l§m §e§l§m §e§l§m §e§l§m §e§l§m §6§l§m §6§l§m §6§l§m §6§l§m §c§l§m §c§l§m §c§l§m §c§l§m");
			for (DragonGuildEntity dragonGuildEntity : dragonGuildEntities) {
				String name = dragonGuildEntity.getName();
				String creator = dragonGuildEntity.getCreator();
				Integer level = dragonGuildEntity.getLevel();
				player.sendMessage("§6●§3公会名:§a" + name + "  §3创建人:§a" + creator + "  §3等级:§6" + level);
			}
			player.sendMessage("§c§l§m §c§l§m §c§l§m §c§l§m §6§l§m §6§l§m §6§l§m §6§l§m §e§l§m §e§l§m §e§l§m §e§l§m §a§l§m §a§l§m §a§l§m §a§l§m §b§l§m §b§l§m §b§l§m §a§l<页码>  " + 1 + "\\" + countnum + " §b§l§m §b§l§m §b§l§m §a§l§m §a§l§m §a§l§m §a§l§m §e§l§m §e§l§m §e§l§m §e§l§m §6§l§m §6§l§m §6§l§m §6§l§m §c§l§m §c§l§m §c§l§m §c§l§m");
			return;
		}
		if (argsList.size() == 2) {
			//获取页数
			String num = argsList.get(1);
			Integer limit = 0;
			try {
				limit = Integer.valueOf(num);
			} catch (Exception e) {
				player.sendMessage("§c页码必须是整数");
				return;
			}
			if (limit > countnum) {
				player.sendMessage(Message.no_more_team);
				return;
			}
			limit = (limit - 1) * 5;
			List<DragonGuildEntity> dragonGuildEntities = DragonGuildDatabaseHandler.selectDragonGuildDataNum(limit);
			player.sendMessage("§c§l§m §c§l§m §c§l§m §c§l§m §6§l§m §6§l§m §6§l§m §6§l§m §e§l§m §e§l§m §e§l§m §e§l§m §a§l§m §a§l§m §a§l§m §a§l§m §b§l§m §b§l§m §b§l§m §e【§a§l公会列表§e】 §b§l§m §b§l§m §b§l§m §a§l§m §a§l§m §a§l§m §a§l§m §e§l§m §e§l§m §e§l§m §e§l§m §6§l§m §6§l§m §6§l§m §6§l§m §c§l§m §c§l§m §c§l§m §c§l§m");
			for (DragonGuildEntity dragonGuildEntity : dragonGuildEntities) {
				String name = dragonGuildEntity.getName();
				String creator = dragonGuildEntity.getCreator();
				Integer level = dragonGuildEntity.getLevel();
				player.sendMessage("§6●§3公会名:§a" + name + "  §3创建人:§a" + creator + "  §3等级:§6" + level);
			}
			player.sendMessage("§c§l§m §c§l§m §c§l§m §c§l§m §6§l§m §6§l§m §6§l§m §6§l§m §e§l§m §e§l§m §e§l§m §e§l§m §a§l§m §a§l§m §a§l§m §a§l§m §b§l§m §b§l§m §b§l§m §a§l<页码>  " + num + "\\" + countnum + " §b§l§m §b§l§m §b§l§m §a§l§m §a§l§m §a§l§m §a§l§m §e§l§m §e§l§m §e§l§m §e§l§m §6§l§m §6§l§m §6§l§m §6§l§m §c§l§m §c§l§m §c§l§m §c§l§m");
			return;
		}
	}
	private void createDragonGuild ( List<String> argsList,Player player) {
		if (argsList.size() != 2){
			player.sendMessage("§c指令不正确");
			return;
		}
		String teamName = argsList.get(1);
		DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(teamName);
		if (dragonGuildEntity.getId() != null) {
			player.sendMessage(Message.create_failure);
			return;
		}
		DragonGuildEntity dragonGuildEntity2 = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
		if (dragonGuildEntity2.getId() != null) {
			player.sendMessage(Message.team_already_exist);
			return;
		}
		double money = VaultAPI.getMoney(player.getName());
		Double amount = new Double(DragonGuild.getSystemConfig().getString("DragonGuild.cost"));
		if (money < amount) {
			player.sendMessage(DragonGuild.getSystemConfig().getString("DragonGuild.prefix") +"§c您没有足够的金币！");
			return;
		}

		dragonGuildEntity.setName(teamName);
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
		DragonGuildEntity dragonGuildEntity3 = DragonGuildDatabaseHandler.selectDragonGuildByName(teamName);
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
		dragonGuildNoticeEntity.setTitle("");
		dragonGuildNoticeEntity.setDesc("");
		dragonGuildNoticeEntity.setUid(player.getUniqueId().toString());
		dragonGuildNoticeEntity.setCreator(player.getName());
		dragonGuildNoticeEntity.setStatus(1);
		DragonGuildNoticeDatabaseHandler.insert(dragonGuildNoticeEntity);
	}

	private void updateDragonGuild ( List<String> argsList,Player player) {
		if (argsList.size() != 2) {
			player.sendMessage("§c指令不正确");
			return;
		}
		DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
		if (dragonGuildMemberEntity1.getId() == null) {
			player.sendMessage(Message.not_join_oneTeam);
			return;
		}
		DragonGuildEntity dragonGuildEntity2 = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
		if (dragonGuildEntity2.getId() == null) {
			player.sendMessage(Message.no_permission);
			return;
		}
		String teamName = argsList.get(1);
		DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(teamName);
		if (dragonGuildEntity.getId() != null) {
			player.sendMessage(Message.update_failure);
			return;
		}
		double money = VaultAPI.getMoney(player.getName());
		Double amount = new Double(DragonGuild.getSystemConfig().getString("DragonGuild.update_cost"));
		if (money < amount) {
			player.sendMessage(DragonGuild.getSystemConfig().getString("DragonGuild.prefix") + "§c您没有足够的金币！");
			return;
		}
		DragonGuildEntity dragonGuild = new DragonGuildEntity();
		dragonGuild.setName(teamName);
		DragonGuildDatabaseHandler.updateUserConfigDataNum(dragonGuildEntity2.getId(),dragonGuild);

		List<DragonGuildMemberEntity> dragonGuildMemberEntities = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByDragonGuildId(dragonGuildEntity2.getId());
		for (DragonGuildMemberEntity dragonGuildMemberEntity : dragonGuildMemberEntities) {
			dragonGuildMemberEntity.setDragonGuildName(teamName);
			DragonGuildMemBerDatabaseHandler.updateUserConfigDataNum(dragonGuildMemberEntity.getId(),dragonGuildMemberEntity);
		}

		player.sendMessage(Message.update_successful);
		VaultAPI.removeMoney(player.getName(),amount);
	}

	private void kickOutPlayer ( List<String> argsList,Player player) {
		if (argsList.size() != 2) {
			player.sendMessage("§c指令不正确");
			return;
		}
		DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
		if (dragonGuildMemberEntity1.getId() == null) {
			player.sendMessage(Message.not_join_oneTeam);
			return;
		}
		DragonGuildEntity dragonGuildEntity2 = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
		if (dragonGuildEntity2.getId() == null) {
			player.sendMessage(Message.no_permission);
			return;
		}
		String playerName = argsList.get(1);
		DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByCreator(playerName);
		if (dragonGuildEntity.getId() != null) {
			player.sendMessage(Message.not_allow_out_team);
			return;
		}

		DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(playerName);
		if (dragonGuildMemberEntity.getId() == null) {
			player.sendMessage(Message.plyer_not_join_oneTeam);
			return;
		}
		DragonGuildMemBerDatabaseHandler.deleteById(playerName);
		player.sendMessage(Message.out_player_successful);
	}

	private void joinDragonGuild ( List<String> argsList,Player player) {
		if (argsList.size() != 2){
			player.sendMessage("§c指令不正确");
			return;
		}
		String teamName = argsList.get(1);
		DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
		if (dragonGuildMemberEntity.getId() != null) {
			player.sendMessage(Message.apply_join2+dragonGuildMemberEntity.getDragonGuildName());
			return;
		}
		DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(teamName);
		if (dragonGuildEntity.getId() == null) {
			player.sendMessage(Message.team_inexistence);
			return;
		}
		Integer count = DragonGuildMemBerDatabaseHandler.selectCount(dragonGuildEntity.getId());
		if (dragonGuildEntity.getMaxMember().equals(count)) {
			player.sendMessage(Message.team_full);
			return;
		}
		if (player.getName().equals(dragonGuildEntity.getCreator())) {
			player.sendMessage(Message.cant_join_your_team);
			return;
		}
		DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUid(player.getName(), teamName);
		if (dragonGuildApplyEntity.getId() != null) {
			player.sendMessage(Message.apply_failure);
			return;
		}
		dragonGuildApplyEntity.setUid(player.getName());
		dragonGuildApplyEntity.setDragonGuildId(dragonGuildEntity.getId());
		dragonGuildApplyEntity.setDragonGuildName(dragonGuildEntity.getName());
		dragonGuildApplyEntity.setApply(dragonGuildEntity.getCreator());
		dragonGuildApplyEntity.setStatus(0);
		dragonGuildApplyEntity.setCreated(new Date());
		DragonGuildApplyDatabaseHandler.insert(dragonGuildApplyEntity);
		player.sendMessage(Message.apply_successful);
	}

	private void dismissDragonGuild ( List<String> argsList,Player player) {
		if (argsList.size() > 1){
			player.sendMessage("§c指令不正确");
			return;
		}
		DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
		if (dragonGuildEntity.getId() == null) {
			player.sendMessage(Message.no_team);
			return;
		}
		DragonGuildDatabaseHandler.deleteById(dragonGuildEntity.getId());
		List<DragonGuildMemberEntity> dragonGuildMemberEntities = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByDragonGuildId(dragonGuildEntity.getId());
		for (DragonGuildMemberEntity dragonGuildMemberEntity : dragonGuildMemberEntities) {
			String uid = dragonGuildMemberEntity.getUid();
			Player addressee = Bukkit.getServer().getPlayer(uid);
			if (addressee != null) {
				addressee.sendMessage(Message.team_dissolve);
			}
		}
		DragonGuildMemBerDatabaseHandler.deleteAll(dragonGuildEntity.getId());
		DragonGuildApplyDatabaseHandler.deleteAll(dragonGuildEntity.getId());
		DragonGuildNoticeDatabaseHandler.deleteById(dragonGuildEntity.getId());
	}

	private void outDragonGuild ( List<String> argsList,Player player) {
		if (argsList.size() > 1){
			player.sendMessage("§c指令不正确");
			return;
		}
		DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
		if (dragonGuildEntity.getId() != null) {
			player.sendMessage(Message.not_allow_out_team);
			return;
		}

		DragonGuildMemberEntity dragonGuildMemberEntity = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(player.getName());
		if (dragonGuildMemberEntity.getId() == null) {
			player.sendMessage(Message.not_join_oneTeam);
			return;
		}
		DragonGuildMemBerDatabaseHandler.deleteById(player.getName());
		player.sendMessage(Message.out_successful);
	}

	private void applyPassDragonGuild ( List<String> argsList,Player player) {
		if (argsList.size() != 2){
			player.sendMessage("§c指令不正确");
			return;
		}
		String userName = argsList.get(1);
		DragonGuildEntity dragonGuildEntity1 = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
		if (dragonGuildEntity1.getId() == null) {
			player.sendMessage(Message.create_first);
			return;
		}
		DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUidAndApply(userName,player.getName());
		if (dragonGuildApplyEntity.getId() == null) {
			player.sendMessage(Message.apply_inexistence);
			return;
		}
		DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(userName);
		if (dragonGuildMemberEntity1.getId() != null) {
			player.sendMessage(Message.apply_join);
			//玩家加入其他公会后，此申请自动拒绝
			dragonGuildApplyEntity.setStatus(2);
			DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
			return;
		}
		DragonGuildEntity dragonGuildEntity = DragonGuildDatabaseHandler.selectDragonGuildByName(dragonGuildApplyEntity.getDragonGuildName());

		DragonGuildMemberEntity dragonGuildMemberEntity = new DragonGuildMemberEntity();
		dragonGuildMemberEntity.setDragonGuildId(dragonGuildEntity.getId());
		dragonGuildMemberEntity.setDragonGuildName(dragonGuildEntity.getName());
		dragonGuildMemberEntity.setExp(0);
		dragonGuildMemberEntity.setUid(dragonGuildApplyEntity.getUid());
		dragonGuildMemberEntity.setStatus(1);
		dragonGuildMemberEntity.setCreated(new Date());
		DragonGuildMemBerDatabaseHandler.insert(dragonGuildMemberEntity);
		dragonGuildEntity.setMaxMember(dragonGuildEntity.getMaxMember() + 1);
		DragonGuildDatabaseHandler.updateUserConfigDataNum(dragonGuildEntity.getId(),dragonGuildEntity);
		dragonGuildApplyEntity.setStatus(1);
		DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
		Player addressee = Bukkit.getServer().getPlayer(userName);
		if (addressee != null) {
			addressee.sendMessage(Message.join_successful);
		}
		player.sendMessage(Message.apply_pass);
	}

	private void applyNoPassDragonGuild ( List<String> argsList,Player player) {
		if (argsList.size() != 2){
			player.sendMessage("§c指令不正确");
			return;
		}
		String userName = argsList.get(1);
		DragonGuildEntity dragonGuildEntity1 = DragonGuildDatabaseHandler.selectDragonGuildByCreator(player.getName());
		if (dragonGuildEntity1.getId() == null) {
			player.sendMessage(Message.create_first);
			return;
		}
		DragonGuildApplyEntity dragonGuildApplyEntity = DragonGuildApplyDatabaseHandler.selectDragonGuildApplyByUidAndApply(userName,player.getName());
		if (dragonGuildApplyEntity.getId() == null) {
			player.sendMessage(Message.apply_inexistence);
			return;
		}
		DragonGuildMemberEntity dragonGuildMemberEntity1 = DragonGuildMemBerDatabaseHandler.selectDragonGuildMemBerByUid(userName);
		if (dragonGuildMemberEntity1.getId() != null) {
			player.sendMessage(Message.apply_join);
			//玩家加入其他公会后，此申请自动拒绝
			dragonGuildApplyEntity.setStatus(2);
			DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
			return;
		}
		dragonGuildApplyEntity.setStatus(2);
		DragonGuildApplyDatabaseHandler.updateUserConfigDataNum(dragonGuildApplyEntity.getId(),dragonGuildApplyEntity);
		Player addressee = Bukkit.getServer().getPlayer(userName);
		if (addressee != null) {
			addressee.sendMessage(Message.join_failure);
		}
		player.sendMessage(Message.apply_nopass);
	}
}
