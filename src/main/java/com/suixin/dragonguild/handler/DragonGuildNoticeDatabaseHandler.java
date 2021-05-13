package com.suixin.dragonguild.handler;

import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.entity.DragonGuildNoticeEntity;
import com.suixin.dragonguild.util.MysqlUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DragonGuildNoticeDatabaseHandler {
//    CREATE TABLE `dragon_guild_notice` (
//            `id` int(11) NOT NULL AUTO_INCREMENT,
//						`uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid',
//            `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
//            `guild_id` int(11) DEFAULT NULL COMMENT '公会',
//            `title` varchar(20) DEFAULT NULL COMMENT '标题',
//            `desc` varchar(255) DEFAULT NULL COMMENT '公告内容',
//            `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效',
//            `created` datetime DEFAULT NULL COMMENT '创建时间',
//            `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告';

    //配置写入
    public static int insert(DragonGuildNoticeEntity dragonGuildNoticeEntity){
        String sql = "insert into dragon_guild_notice(uid, creator, guild_id , title,desc,status,created,modified)"
                + " values(?,?, ?, ?, ?,?,?, ?, ?, ?)";
        Object [] params = new Object[8];
        params[0]= dragonGuildNoticeEntity.getUid();
        params[1]= dragonGuildNoticeEntity.getCreator();
        params[2]= dragonGuildNoticeEntity.getGuildId();
        params[3]= dragonGuildNoticeEntity.getTitle();
        params[4]= dragonGuildNoticeEntity.getDesc();
        params[5]= dragonGuildNoticeEntity.getStatus();
        params[6]= dragonGuildNoticeEntity.getCreated();
        params[7]= dragonGuildNoticeEntity.getModified();
        try {
            ResultSet rst= MysqlUtil.getInsertObjectIDs(sql, params);
            if (rst != null) {
                return 1;
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;

    }
    //创建表
    public static void createTable() {
            String tableSql = "CREATE TABLE `dragon_guild_notice` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid'," +
                    "  `creator` varchar(255) DEFAULT NULL COMMENT '创建人'," +
                    "  `guild_id` int(11) DEFAULT NULL COMMENT '公会'," +
                    "  `title` varchar(20) DEFAULT NULL COMMENT '标题'," +
                    "  `desc` varchar(255) DEFAULT NULL COMMENT '公告内容'," +
                    "  `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效'," +
                    "  `created` datetime DEFAULT NULL COMMENT '创建时间'," +
                    "  `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间'," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='公告';";
        try {
            MysqlUtil.execCommand(tableSql);
        }  catch (Exception e) {
        }
    }

    public static  List<DragonGuildEntity> selectDragonGuildDataNum(Integer current){
        String sql = "select * from dragon_guild_notice where status = 1 limit "+current+", 5";
        List<DragonGuildNoticeEntity> dragonGuildNoticeEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    DragonGuildNoticeEntity dragonGuildNoticeEntity = new DragonGuildNoticeEntity();
                    dragonGuildNoticeEntity.setId(rst.getInt("id"));
                    dragonGuildNoticeEntity.setUid(rst.getString("uid"));
                    dragonGuildNoticeEntity.setCreator(rst.getString("creator"));
                    dragonGuildNoticeEntity.setName(rst.getString("name"));
                    dragonGuildNoticeEntity.setLevel(rst.getInt("level"));
                    dragonGuildNoticeEntity.setExpAll(rst.getInt("exp_all"));
                    dragonGuildNoticeEntity.setExpCurrent(rst.getInt("exp_current"));
                    dragonGuildNoticeEntity.setMaxMember(rst.getInt("max_member"));
                    dragonGuildNoticeEntity.setStatus(rst.getInt("status"));
                    dragonGuildNoticeEntity.setCreated(rst.getDate("created"));
                    dragonGuildNoticeEntity.setModified(rst.getDate("modified"));
                    dragonGuildNoticeEntitys.add(dragonGuildNoticeEntity);
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildNoticeEntitys;
    }
    public static DragonGuildNoticeEntity selectDragonGuildByName(String name){
        String sql = "select * from dragon_guild_notice where status = 1 and name =  '"+name+"'";
        DragonGuildNoticeEntity dragonGuildNoticeEntity = new DragonGuildNoticeEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildNoticeEntity.setId(rst.getInt("id"));
                    dragonGuildNoticeEntity.setUid(rst.getString("uid"));
                    dragonGuildNoticeEntity.setCreator(rst.getString("creator"));
                    dragonGuildNoticeEntity.setName(rst.getString("name"));
                    dragonGuildNoticeEntity.setLevel(rst.getInt("level"));
                    dragonGuildNoticeEntity.setExpAll(rst.getInt("exp_all"));
                    dragonGuildNoticeEntity.setExpCurrent(rst.getInt("exp_current"));
                    dragonGuildNoticeEntity.setMaxMember(rst.getInt("max_member"));
                    dragonGuildNoticeEntity.setStatus(rst.getInt("status"));
                    dragonGuildNoticeEntity.setCreated(rst.getDate("created"));
                    dragonGuildNoticeEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildNoticeEntity;
    }
    public static DragonGuildNoticeEntity selectDragonGuildById(Integer id){
        String sql = "select * from dragon_guild_notice where status = 1 and id =  '"+id+"'";
        DragonGuildNoticeEntity dragonGuildNoticeEntity = new DragonGuildNoticeEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildNoticeEntity.setId(rst.getInt("id"));
                    dragonGuildNoticeEntity.setUid(rst.getString("uid"));
                    dragonGuildNoticeEntity.setCreator(rst.getString("creator"));
                    dragonGuildNoticeEntity.setName(rst.getString("name"));
                    dragonGuildNoticeEntity.setLevel(rst.getInt("level"));
                    dragonGuildNoticeEntity.setExpAll(rst.getInt("exp_all"));
                    dragonGuildNoticeEntity.setExpCurrent(rst.getInt("exp_current"));
                    dragonGuildNoticeEntity.setMaxMember(rst.getInt("max_member"));
                    dragonGuildNoticeEntity.setStatus(rst.getInt("status"));
                    dragonGuildNoticeEntity.setCreated(rst.getDate("created"));
                    dragonGuildNoticeEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildNoticeEntity;
    }
    //查询总页数
    public static Integer selectTeamCount(){
        String sql = "select count(*) as datacount from dragon_guild_notice where status = 1";
        Integer datacount=0;
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            rst.next();
            datacount = rst.getInt("datacount");
            MysqlUtil.close(rst);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return datacount;
    }

    public static DragonGuildNoticeEntity selectDragonGuildByCreator(String creator){
        String sql = "select * from dragon_guild_notice where status = 1 and creator =  '"+creator+"'";
        DragonGuildNoticeEntity dragonGuildNoticeEntity = new DragonGuildNoticeEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildNoticeEntity.setId(rst.getInt("id"));
                    dragonGuildNoticeEntity.setUid(rst.getString("uid"));
                    dragonGuildNoticeEntity.setCreator(rst.getString("creator"));
                    dragonGuildNoticeEntity.setName(rst.getString("name"));
                    dragonGuildNoticeEntity.setLevel(rst.getInt("level"));
                    dragonGuildNoticeEntity.setExpAll(rst.getInt("exp_all"));
                    dragonGuildNoticeEntity.setExpCurrent(rst.getInt("exp_current"));
                    dragonGuildNoticeEntity.setMaxMember(rst.getInt("max_member"));
                    dragonGuildNoticeEntity.setStatus(rst.getInt("status"));
                    dragonGuildNoticeEntity.setCreated(rst.getDate("created"));
                    dragonGuildNoticeEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildNoticeEntity;
    }

    //修改配置
    public static String updateUserConfigDataNum(Integer id, DragonGuildNoticeEntity dragonGuildNoticeEntity){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update dragon_guild_notice set");
        if (dragonGuildNoticeEntity.getUid() != null && !dragonGuildNoticeEntity.getUid().equals("")) {
            stringBuffer.append(" uid = "+"'"+dragonGuildNoticeEntity.getUid()+"'"+",");
        }
        if (dragonGuildNoticeEntity.getCreator() != null) {
            stringBuffer.append(" creator = "+dragonGuildNoticeEntity.getCreator()+",");
        }
        if (dragonGuildNoticeEntity.getName() != null && !dragonGuildNoticeEntity.getName().equals("")) {
            stringBuffer.append(" name = "+"'"+dragonGuildNoticeEntity.getName()+"'"+",");
        }
        if (dragonGuildNoticeEntity.getLevel() != null) {
            stringBuffer.append(" level = "+dragonGuildNoticeEntity.getLevel()+",");
        }
        if (dragonGuildNoticeEntity.getExpAll() != null) {
            stringBuffer.append(" exp_all = "+dragonGuildNoticeEntity.getExpAll()+",");
        }
        if (dragonGuildNoticeEntity.getExpCurrent() != null) {
            stringBuffer.append(" exp_current = "+dragonGuildNoticeEntity.getExpCurrent()+",");
        }
        if (dragonGuildNoticeEntity.getMaxMember() != null) {
            stringBuffer.append(" max_member = "+dragonGuildNoticeEntity.getMaxMember()+",");
        }
        if (dragonGuildNoticeEntity.getStatus() != null) {
            stringBuffer.append(" status = "+dragonGuildNoticeEntity.getStatus()+",");
        }
        if (dragonGuildNoticeEntity.getCreated() != null) {
            stringBuffer.append(" created = "+"'"+dragonGuildNoticeEntity.getCreated()+"'"+",");
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

    //删除公会
    public static void deleteById(Integer id){
        String sql = "update dragon_guild_notice set status = -1 where id = "+id;
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
                " where TABLE_NAME = 'dragon_guild_notice';";
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