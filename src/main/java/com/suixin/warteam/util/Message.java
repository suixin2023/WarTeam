package com.suixin.warteam.util;

import com.suixin.warteam.WarTeam;
import org.bukkit.configuration.file.YamlConfiguration;


/**
 * 全局消息
 */
public class Message {

    public static String create_successful;
    public static String create_failure;
    public static String create_failure_not_enough_money;
    public static String apply_successful;
    public static String apply_failure;
    public static String team_inexistence;
    public static String team_full;
    public static String team_dissolve;
    public static String out_successful;
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
    public static String no_more_team;
    public static String apply_join;
    public static String apply_join2;
    public static String no_more_member;

    public static boolean loadMessage( ){
        YamlConfiguration config = WarTeam.getYml("message.yml");
        YamlConfiguration systemConfig = WarTeam.getSystemConfig();
        String prefix = systemConfig.getString("WarTeam.prefix");
        if (config != null) {
            create_successful = prefix + config.getString("WarTeam.create_successful","§a创建成功");
            create_failure = prefix + config.getString("WarTeam.create_failure","§c创建失败,战队已存在!");
            create_failure_not_enough_money = prefix + config.getString("WarTeam.create_failure_not_enough_money","§c创建失败,没有足够的钱!创建战队需花费："+systemConfig.getString("WarTeam.cost")+"金币");
            apply_successful = prefix + config.getString("WarTeam.apply_successful","§a成功发送加入申请!,请耐心等待管理员审核!");
            apply_failure = prefix + config.getString("WarTeam.apply_failure","§c已经申请过,请不要重复提交!");
            team_inexistence = prefix + config.getString("WarTeam.team_inexistence","§c错误!战队不存在!");
            team_full = prefix + config.getString("WarTeam.team_full","§c错误!战队成员已满!");
            team_dissolve = prefix + config.getString("WarTeam.team_dissolve","§e战队已解散!");
            out_successful = prefix + config.getString("WarTeam.out_successful","§a退出战队成功!");
            join_successful = prefix + config.getString("WarTeam.join_successful","§a加入战队成功!!");
            join_failure = prefix + config.getString("WarTeam.join_failure","§c管理员已拒绝,加入战队失败!");
            create_first = prefix + config.getString("WarTeam.create_first","§c请先创建一个战队!");
            apply_inexistence = prefix + config.getString("WarTeam.apply_inexistence","§c未找到该玩家的申请!");
            apply_pass = prefix + config.getString("WarTeam.apply_pass","§a审批通过!");
            apply_nopass = prefix + config.getString("WarTeam.apply_nopass","§c审批不通过");
            team_already_exist = prefix + config.getString("WarTeam.team_already_exist","§c你已经有战队了");
            cant_join_your_team = prefix + config.getString("WarTeam.cant_join_your_team","§c不能申请加入自己创建的战队");
            no_team = prefix + config.getString("WarTeam.no_team","§c没有战队可以解散");
            not_allow_out_team = prefix + config.getString("WarTeam.not_allow_out_team","§c你不能退出自己的战队");
            not_join_oneTeam = prefix + config.getString("WarTeam.not_join_oneTeam","§c你还没有加入一个战队");
            no_more_team = prefix + config.getString("WarTeam.no_more_team","§c没有更多的战队了");
            apply_join = prefix + config.getString("WarTeam.apply_join","§c该玩家已经加入其他战队");
            apply_join2 = prefix + config.getString("WarTeam.apply_join2","§c申请失败！你已经加入了战队：");
            no_more_member = prefix + config.getString("WarTeam.no_more_team","§c没有更多的队员了");
            return true;
        }else {
            return true;
        }
    }
}