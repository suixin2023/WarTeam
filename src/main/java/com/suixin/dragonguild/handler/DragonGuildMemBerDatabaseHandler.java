package com.suixin.dragonguild.handler;

import com.suixin.dragonguild.entity.DragonGuildMemberEntity;
import com.suixin.dragonguild.util.MysqlUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DragonGuildMemBerDatabaseHandler {
//    CREATE TABLE `dragon_guild_member` (
//            `id` int(11) NOT NULL AUTO_INCREMENT,
//  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid',
//            `dragon_guild_id` int(11) DEFAULT NULL COMMENT '公会Id',
//            `dragon_guild_name` varchar(255) DEFAULT NULL COMMENT '公会名字',
//            `position` varchar(255) DEFAULT NULL COMMENT '公会职位',
//            `exp` int(11) DEFAULT NULL COMMENT '贡献exp',
//            `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效',
//            `created` datetime DEFAULT NULL COMMENT '创建时间',
//            `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公会成员表';

    //配置写入
    public static int insert(DragonGuildMemberEntity dragonGuildMemberEntity){
        String sql = "insert into dragon_guild_member(uid, dragon_guild_id, dragon_guild_name , position,exp,status,created,modified)"
                + " values(?,?, ?, ?, ?,?,?, ?)";
        Object [] params = new Object[8];
        params[0]= dragonGuildMemberEntity.getUid();
        params[1]= dragonGuildMemberEntity.getDragonGuildId();
        params[2]= dragonGuildMemberEntity.getDragonGuildName();
        params[3]= dragonGuildMemberEntity.getPosition();
        params[4]= dragonGuildMemberEntity.getExp();
        params[5]= dragonGuildMemberEntity.getStatus();
        params[6]= dragonGuildMemberEntity.getCreated();
        params[7]= dragonGuildMemberEntity.getModified();
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
        String tableSql = "CREATE TABLE `dragon_guild_member` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid'," +
                "  `dragon_guild_id` int(11) DEFAULT NULL COMMENT '公会Id'," +
                "  `dragon_guild_name` varchar(255) DEFAULT NULL COMMENT '公会名字'," +
                "  `position` varchar(255) DEFAULT NULL COMMENT '公会职位'," +
                "  `exp` int(11) DEFAULT NULL COMMENT '贡献exp'," +
                "  `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效'," +
                "  `created` datetime DEFAULT NULL COMMENT '创建时间'," +
                "  `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间'," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公会成员表';";
        try {
            MysqlUtil.execCommand(tableSql);
        }  catch (Exception e) {
        }
    }

    public static  List<DragonGuildMemberEntity> selectDragonGuildMemBerDataNum(Integer limit, Integer dragonGuildId){
        String sql = "select * from dragon_guild_member where status = 1 and dragon_guild_id = "+dragonGuildId+" limit "+limit+", 14";
        List<DragonGuildMemberEntity> dragonGuildMemberEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    DragonGuildMemberEntity dragonGuildMemberEntity = new DragonGuildMemberEntity();
                    dragonGuildMemberEntity.setId(rst.getInt("id"));
                    dragonGuildMemberEntity.setUid(rst.getString("uid"));
                    dragonGuildMemberEntity.setDragonGuildId(rst.getInt("dragon_guild_id"));
                    dragonGuildMemberEntity.setDragonGuildName(rst.getString("dragon_guild_name"));
                    dragonGuildMemberEntity.setPosition(rst.getString("position"));
                    dragonGuildMemberEntity.setExp(rst.getInt("exp"));
                    dragonGuildMemberEntity.setStatus(rst.getInt("status"));
                    dragonGuildMemberEntity.setCreated(rst.getDate("created"));
                    dragonGuildMemberEntity.setModified(rst.getDate("modified"));
                    dragonGuildMemberEntitys.add(dragonGuildMemberEntity);
                }
                MysqlUtil.close(rst);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildMemberEntitys;
    }

    public static  Integer selectCount(Integer dragonGuildId){
        String sql = "select count(*) as datacount from dragon_guild_member where status = 1 and dragon_guild_id = "+dragonGuildId;
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
    public static  List<DragonGuildMemberEntity> selectDragonGuildMemBerByDragonGuildId(Integer dragonGuildId){
        String sql = "select * from dragon_guild_member where status = 1 and dragon_guild_id = " +dragonGuildId;
        List<DragonGuildMemberEntity> dragonGuildMemberEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    DragonGuildMemberEntity dragonGuildMemberEntity = new DragonGuildMemberEntity();
                    dragonGuildMemberEntity.setId(rst.getInt("id"));
                    dragonGuildMemberEntity.setUid(rst.getString("uid"));
                    dragonGuildMemberEntity.setDragonGuildId(rst.getInt("dragon_guild_id"));
                    dragonGuildMemberEntity.setDragonGuildName(rst.getString("dragon_guild_name"));
                    dragonGuildMemberEntity.setPosition(rst.getString("position"));
                    dragonGuildMemberEntity.setExp(rst.getInt("exp"));
                    dragonGuildMemberEntity.setStatus(rst.getInt("status"));
                    dragonGuildMemberEntity.setCreated(rst.getDate("created"));
                    dragonGuildMemberEntity.setModified(rst.getDate("modified"));
                    dragonGuildMemberEntitys.add(dragonGuildMemberEntity);
                }
                MysqlUtil.close(rst);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildMemberEntitys;
    }

    public static DragonGuildMemberEntity selectDragonGuildMemBerByUid(String uid){
        String sql = "select * from dragon_guild_member where status = 1 and uid = '" +uid+"'";
        DragonGuildMemberEntity dragonGuildMemberEntity = new DragonGuildMemberEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildMemberEntity.setId(rst.getInt("id"));
                    dragonGuildMemberEntity.setUid(rst.getString("uid"));
                    dragonGuildMemberEntity.setDragonGuildId(rst.getInt("dragon_guild_id"));
                    dragonGuildMemberEntity.setDragonGuildName(rst.getString("dragon_guild_name"));
                    dragonGuildMemberEntity.setPosition(rst.getString("position"));
                    dragonGuildMemberEntity.setExp(rst.getInt("exp"));
                    dragonGuildMemberEntity.setStatus(rst.getInt("status"));
                    dragonGuildMemberEntity.setCreated(rst.getDate("created"));
                    dragonGuildMemberEntity.setModified(rst.getDate("modified"));
                }
                MysqlUtil.close(rst);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildMemberEntity;
    }

    //修改配置
    public static String updateUserConfigDataNum(Integer id, DragonGuildMemberEntity dragonGuildMemberEntity){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update dragon_guild_member set");
        if (dragonGuildMemberEntity.getUid() != null && !dragonGuildMemberEntity.getUid().equals("")) {
            stringBuffer.append(" uid = "+"'"+dragonGuildMemberEntity.getUid()+"'"+",");
        }
        if (dragonGuildMemberEntity.getDragonGuildId() != null) {
            stringBuffer.append(" dragon_guild_id = "+dragonGuildMemberEntity.getDragonGuildId()+",");
        }
        if (dragonGuildMemberEntity.getDragonGuildName() != null && !dragonGuildMemberEntity.getDragonGuildName().equals("")) {
            stringBuffer.append(" dragon_guild_name = "+"'"+dragonGuildMemberEntity.getDragonGuildName()+"'"+",");
        }
        if (dragonGuildMemberEntity.getPosition() != null && !dragonGuildMemberEntity.getPosition().equals("")) {
            stringBuffer.append(" position = "+"'"+dragonGuildMemberEntity.getPosition()+"'"+",");
        }
        if (dragonGuildMemberEntity.getExp() != null) {
            stringBuffer.append(" exp = "+dragonGuildMemberEntity.getExp()+",");
        }
        if (dragonGuildMemberEntity.getStatus() != null) {
            stringBuffer.append(" status = "+dragonGuildMemberEntity.getStatus()+",");
        }
        if (dragonGuildMemberEntity.getCreated() != null) {
            stringBuffer.append(" created = "+"'"+dragonGuildMemberEntity.getCreated()+"'"+",");
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
        String sql = "update dragon_guild_member set status = -1 where uid = '"+uid+"'";
        try {
            int i = MysqlUtil.execCommand(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除所有队员
    public static void deleteAll(Integer dragonGuildId){
        String sql = "update dragon_guild_member set status = -1 where dragon_guild_id = "+dragonGuildId;
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
                " where TABLE_NAME = 'dragon_guild_member';";
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