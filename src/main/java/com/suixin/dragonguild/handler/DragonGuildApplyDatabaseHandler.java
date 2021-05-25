package com.suixin.dragonguild.handler;

import com.suixin.dragonguild.entity.DragonGuildApplyEntity;
import com.suixin.dragonguild.util.MysqlUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class DragonGuildApplyDatabaseHandler {
//    CREATE TABLE `dragon_guild_apply` (
//            `id` int(11) NOT NULL AUTO_INCREMENT,
//  `uid` varchar(50) DEFAULT NULL COMMENT '申请人',
//            `apply` varchar(50) DEFAULT NULL COMMENT '审核人',
//            `dragon_guild_id` int(11) DEFAULT NULL COMMENT '申请公会',
//            `dragon_guild_name` varchar(50) DEFAULT NULL COMMENT '申请公会',
//            `status` int(11) DEFAULT NULL COMMENT '审批通过或拒绝',
//            `created` datetime DEFAULT NULL COMMENT '创建时间',
//            `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请表';

    //配置写入
    public static int insert(DragonGuildApplyEntity dragonGuildApplyEntity){
        String sql = "insert into dragon_guild_apply(uid,apply, dragon_guild_id, dragon_guild_name ,status,created,modified)"
                + " values(?,?, ?, ?, ?, ?, ?)";
        Object [] params = new Object[7];
        params[0]= dragonGuildApplyEntity.getUid();
        params[1]= dragonGuildApplyEntity.getApply();
        params[2]= dragonGuildApplyEntity.getDragonGuildId();
        params[3]= dragonGuildApplyEntity.getDragonGuildName();
        params[4]= dragonGuildApplyEntity.getStatus();
        params[5]= dragonGuildApplyEntity.getCreated();
        params[6]= dragonGuildApplyEntity.getModified();
        try {
            ResultSet rst= MysqlUtil.getInsertObjectIDs(sql, params);
            if (rst != null) {
                MysqlUtil.close(rst);
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
    //创建表
    public static void createTable() {
        String tableSql = "CREATE TABLE `dragon_guild_apply` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `uid` varchar(50) DEFAULT NULL COMMENT '申请人'," +
                "  `apply` varchar(50) DEFAULT NULL COMMENT '审核人'," +
                "  `dragon_guild_id` int(11) DEFAULT NULL COMMENT '申请公会'," +
                "  `dragon_guild_name` varchar(50) DEFAULT NULL COMMENT '申请公会'," +
                "  `status` int(11) DEFAULT NULL COMMENT '审批通过或拒绝'," +
                "  `created` datetime DEFAULT NULL COMMENT '创建时间'," +
                "  `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间'," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请表';";
        try {
            MysqlUtil.execCommand(tableSql);
        }  catch (Exception e) {
        }
    }

    public static  List<DragonGuildApplyEntity> selectDragonGuildApplyDataNum(Integer current){
        String sql = "select * from dragon_guild_apply where status = 1 limit "+current+", 20";
        List<DragonGuildApplyEntity> dragonGuildApplyEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    DragonGuildApplyEntity dragonGuildApplyEntity = new DragonGuildApplyEntity();
                    dragonGuildApplyEntity.setId(rst.getInt("id"));
                    dragonGuildApplyEntity.setUid(rst.getString("uid"));
                    dragonGuildApplyEntity.setApply(rst.getString("apply"));
                    dragonGuildApplyEntity.setDragonGuildId(rst.getInt("dragon_guild_id"));
                    dragonGuildApplyEntity.setDragonGuildName(rst.getString("dragon_guild_name"));
                    dragonGuildApplyEntity.setStatus(rst.getInt("status"));
                    dragonGuildApplyEntity.setCreated(rst.getDate("created"));
                    dragonGuildApplyEntity.setModified(rst.getDate("modified"));
                    dragonGuildApplyEntitys.add(dragonGuildApplyEntity);
                }
                MysqlUtil.close(rst);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildApplyEntitys;
    }

    public static  Integer selectCount(Integer dragonGuildId){
        String sql = "select count(*) as datacount from dragon_guild_apply where status = 0 and dragon_guild_id = "+dragonGuildId;
        Integer datacount = 0;
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            rst.next();
            datacount = rst.getInt("datacount");

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  datacount;
    }
    public static DragonGuildApplyEntity selectDragonGuildApplyByUid(String uid, String dragonGuildName){
        String sql = "select * from dragon_guild_apply where status = 0 and uid = '" +uid +"' and dragon_guild_name = '" +dragonGuildName+"'";
        DragonGuildApplyEntity dragonGuildApplyEntity = new DragonGuildApplyEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildApplyEntity.setId(rst.getInt("id"));
                    dragonGuildApplyEntity.setUid(rst.getString("uid"));
                    dragonGuildApplyEntity.setApply(rst.getString("apply"));
                    dragonGuildApplyEntity.setDragonGuildId(rst.getInt("dragon_guild_id"));
                    dragonGuildApplyEntity.setDragonGuildName(rst.getString("dragon_guild_name"));
                    dragonGuildApplyEntity.setStatus(rst.getInt("status"));
                    dragonGuildApplyEntity.setCreated(rst.getDate("created"));
                    dragonGuildApplyEntity.setModified(rst.getDate("modified"));
                }
                MysqlUtil.close(rst);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildApplyEntity;
    }

    public static  List<DragonGuildApplyEntity> selectDragonGuildApplyByDragonGuildId(Integer limit, Integer dragonGuildId){
        String sql = "select * from dragon_guild_apply where status = 0 and dragon_guild_id = " +dragonGuildId+" order by created desc limit "+limit+", 6";
        List<DragonGuildApplyEntity> dragonGuildApplyEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    DragonGuildApplyEntity dragonGuildApplyEntity = new DragonGuildApplyEntity();
                    dragonGuildApplyEntity.setId(rst.getInt("id"));
                    dragonGuildApplyEntity.setUid(rst.getString("uid"));
                    dragonGuildApplyEntity.setApply(rst.getString("apply"));
                    dragonGuildApplyEntity.setDragonGuildId(rst.getInt("dragon_guild_id"));
                    dragonGuildApplyEntity.setDragonGuildName(rst.getString("dragon_guild_name"));
                    dragonGuildApplyEntity.setStatus(rst.getInt("status"));
                    dragonGuildApplyEntity.setCreated(rst.getDate("created"));
                    dragonGuildApplyEntity.setModified(rst.getDate("modified"));
                    dragonGuildApplyEntitys.add(dragonGuildApplyEntity);
                }
                MysqlUtil.close(rst);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildApplyEntitys;
    }

    public static DragonGuildApplyEntity selectDragonGuildApplyByUidAndApply(String uid, String apply){
        String sql = "select * from dragon_guild_apply where status = 0 and uid = '" +uid +"' and apply = '" +apply+"'";
        DragonGuildApplyEntity dragonGuildApplyEntity = new DragonGuildApplyEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildApplyEntity.setId(rst.getInt("id"));
                    dragonGuildApplyEntity.setUid(rst.getString("uid"));
                    dragonGuildApplyEntity.setApply(rst.getString("apply"));
                    dragonGuildApplyEntity.setDragonGuildId(rst.getInt("dragon_guild_id"));
                    dragonGuildApplyEntity.setDragonGuildName(rst.getString("dragon_guild_name"));
                    dragonGuildApplyEntity.setStatus(rst.getInt("status"));
                    dragonGuildApplyEntity.setCreated(rst.getDate("created"));
                    dragonGuildApplyEntity.setModified(rst.getDate("modified"));
                }
                MysqlUtil.close(rst);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildApplyEntity;
    }

    //修改配置
    public static String updateUserConfigDataNum(Integer id, DragonGuildApplyEntity dragonGuildApplyEntity){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update dragon_guild_apply set");
        if (dragonGuildApplyEntity.getUid() != null && !dragonGuildApplyEntity.getUid().equals("")) {
            stringBuffer.append(" uid = "+"'"+dragonGuildApplyEntity.getUid()+"'"+",");
        }
        if (dragonGuildApplyEntity.getApply() != null && !dragonGuildApplyEntity.getApply().equals("")) {
            stringBuffer.append(" apply = "+"'"+dragonGuildApplyEntity.getApply()+"'"+",");
        }
        if (dragonGuildApplyEntity.getDragonGuildId() != null) {
            stringBuffer.append(" dragon_guild_id = "+dragonGuildApplyEntity.getDragonGuildId()+",");
        }
        if (dragonGuildApplyEntity.getDragonGuildName() != null && !dragonGuildApplyEntity.getDragonGuildName().equals("")) {
            stringBuffer.append(" dragon_guild_name = "+"'"+dragonGuildApplyEntity.getDragonGuildName()+"'"+",");
        }
        if (dragonGuildApplyEntity.getStatus() != null) {
            stringBuffer.append(" status = "+dragonGuildApplyEntity.getStatus()+",");
        }
        if (dragonGuildApplyEntity.getCreated() != null) {
            stringBuffer.append(" created = "+"'"+dragonGuildApplyEntity.getCreated()+"'"+",");
        }
        stringBuffer.append(" id = id ");
        stringBuffer.append(" where id  = " + id);

        String sql = stringBuffer.toString();
        try {
            MysqlUtil.execCommand(sql);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sql;
    }

    //删除队员
    public static void deleteById(String uid){
        String sql = "update dragon_guild_apply set status = -1 where uid = '"+uid+"'";
        try {
            int i = MysqlUtil.execCommand(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除所有申请
    public static void deleteAll(Integer dragonGuildId){
        String sql = "update dragon_guild_apply set status = -1 where dragon_guild_id = "+dragonGuildId;
        try {
            int i = MysqlUtil.execCommand(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //查询表是否存在
    public static Integer selectTabCount(){
        String sql = "select count(1) datacount " +
                " from information_schema.TABLES " +
                " where TABLE_NAME = 'dragon_guild_apply';";
        Integer datacount=0;
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            rst.next();
            datacount = rst.getInt("datacount");
            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return datacount;
    }
}