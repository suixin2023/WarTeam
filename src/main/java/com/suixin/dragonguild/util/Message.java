package com.suixin.dragonguild.util;

import com.suixin.dragonguild.DragonGuild;
import org.bukkit.configuration.file.YamlConfiguration;


/**
 * 全局消息
 */
public class Message {

    public static String create_successful;
    public static String update_successful;
    public static String create_failure;
    public static String update_failure;
    public static String create_failure_not_enough_money;
    public static String apply_successful;
    public static String apply_failure;
    public static String team_inexistence;
    public static String team_full;
    public static String team_dissolve;
    public static String out_successful;
    public static String out_player_successful;
    public static String join_successful;
    public static String join_failure;
    public static String create_first;
    public static String apply_inexistence;
    public static String apply_pass;
    public static String apply_nopass;
    public static String team_already_exist;
    public static String cant_join_your_team;
    public static String no_team;
    public static String not_allow_out_team;
    public static String not_join_oneTeam;
    public static String plyer_not_join_oneTeam;
    public static String no_more_team;
    public static String apply_join;
    public static String apply_join2;
    public static String no_more_member;
    public static String no_more_apply;
    public static String no_more_guild;
    public static String no_permission;
    public static String name;
    public static String contribution;
    public static String level;
    public static String member;

    public static boolean loadMessage( ){
        YamlConfiguration config = DragonGuild.getYml("message.yml");
        YamlConfiguration systemConfig = DragonGuild.getSystemConfig();
        String prefix = systemConfig.getString("DragonGuild.prefix");
        if (config != null) {
            create_successful = prefix + config.getString("DragonGuild.create_successful","§a创建成功");
            update_successful = prefix + config.getString("DragonGuild.update_successful","§a修改成功");
            create_failure = prefix + config.getString("DragonGuild.create_failure","§c创建失败,公会已存在!");
            update_failure = prefix + config.getString("DragonGuild.update_failure","§c修改失败,公会已存在!");
            create_failure_not_enough_money = prefix + config.getString("DragonGuild.create_failure_not_enough_money","§c创建失败,没有足够的钱!创建公会需花费："+systemConfig.getString("DragonGuild.cost")+"金币");
            apply_successful = prefix + config.getString("DragonGuild.apply_successful","§a成功发送加入申请!,请耐心等待管理员审核!");
            apply_failure = prefix + config.getString("DragonGuild.apply_failure","§c已经申请过,请不要重复提交!");
            team_inexistence = prefix + config.getString("DragonGuild.team_inexistence","§c错误!公会不存在!");
            team_full = prefix + config.getString("DragonGuild.team_full","§c错误!公会成员已满!");
            team_dissolve = prefix + config.getString("DragonGuild.team_dissolve","§e公会已解散!");
            out_successful = prefix + config.getString("DragonGuild.out_successful","§a退出公会成功!");
            out_player_successful = prefix + config.getString("DragonGuild.out_player_successful","§a踢出公会成功!");
            join_successful = prefix + config.getString("DragonGuild.join_successful","§a加入公会成功!!");
            join_failure = prefix + config.getString("DragonGuild.join_failure","§c管理员已拒绝,加入公会失败!");
            create_first = prefix + config.getString("DragonGuild.create_first","§c请先创建一个公会!");
            apply_inexistence = prefix + config.getString("DragonGuild.apply_inexistence","§c未找到该玩家的申请!");
            apply_pass = prefix + config.getString("DragonGuild.apply_pass","§a审批通过!");
            apply_nopass = prefix + config.getString("DragonGuild.apply_nopass","§c审批不通过");
            team_already_exist = prefix + config.getString("DragonGuild.team_already_exist","§c你已经有公会了");
            cant_join_your_team = prefix + config.getString("DragonGuild.cant_join_your_team","§c不能申请加入自己创建的公会");
            no_team = prefix + config.getString("DragonGuild.no_team","§c没有公会可以解散");
            not_allow_out_team = prefix + config.getString("DragonGuild.not_allow_out_team","§c你不能退出自己的公会");
            not_join_oneTeam = prefix + config.getString("DragonGuild.not_join_oneTeam","§c你还没有加入一个公会");
            plyer_not_join_oneTeam = prefix + config.getString("DragonGuild.plyer_not_join_oneTeam","§c该玩家还没有加入一个公会");
            no_more_team = prefix + config.getString("DragonGuild.no_more_team","§c没有更多的公会了");
            apply_join = prefix + config.getString("DragonGuild.apply_join","§c该玩家已经加入其他公会");
            apply_join2 = prefix + config.getString("DragonGuild.apply_join2","§c申请失败！你已经加入了公会：");
            no_more_member = prefix + config.getString("DragonGuild.no_more_member","§c没有更多的成员了");
            no_more_apply = prefix + config.getString("DragonGuild.no_more_apply","§c没有更多的申请了");
            no_more_guild = prefix + config.getString("DragonGuild.no_more_guild","§c没有更多的公会了");
            no_permission = prefix + config.getString("DragonGuild.no_permission","§c无权限,你不是队长");
            name = prefix + config.getString("information.name","§a游戏名：");
            contribution = prefix + config.getString("information.contribution","§a贡献：");
            level = prefix + config.getString("information.level","§a等级：");
            member = prefix + config.getString("information.member","§a成员：");
            return true;
        }else {
            return true;
        }
    }
}