package com.suixin.dragonguild.handler;

import com.suixin.dragonguild.entity.DragonGuildEntity;
import com.suixin.dragonguild.util.MysqlUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class DragonGuildChatDatabaseHandler {
//    CREATE TABLE `dragon_guild_chat` (
//            `id` int(11) NOT NULL AUTO_INCREMENT,
//						`uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid',
//            `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
//            `guild_id` int(11) DEFAULT NULL COMMENT '公会',
//            `desc` varchar(255) DEFAULT NULL COMMENT '消息内容',
//            `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效',
//            `created` datetime DEFAULT NULL COMMENT '创建时间',
//            `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天记录';

    //配置写入
    public static int insert(DragonGuildEntity dragonGuildEntity){
        String sql = "insert into dragon_guild(uid, creator, name , level,exp_all,exp_current,max_member,status,created,modified)"
                + " values(?,?, ?, ?, ?,?,?, ?, ?, ?)";
        Object [] params = new Object[10];
        params[0]= dragonGuildEntity.getUid();
        params[1]= dragonGuildEntity.getCreator();
        params[2]= dragonGuildEntity.getName();
        params[3]= dragonGuildEntity.getLevel();
        params[4]= dragonGuildEntity.getExpAll();
        params[5]= dragonGuildEntity.getExpCurrent();
        params[6]= dragonGuildEntity.getMaxMember();
        params[7]= dragonGuildEntity.getStatus();
        params[8]= dragonGuildEntity.getCreated();
        params[9]= dragonGuildEntity.getModified();
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
            String tableSql = "CREATE TABLE `dragon_guild` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid'," +
                    "  `creator` varchar(255) DEFAULT NULL COMMENT '创建人'," +
                    "  `name` varchar(255) DEFAULT NULL COMMENT '公会名字'," +
                    "  `level` int(11) DEFAULT '1' COMMENT '等级'," +
                    "  `exp_all` int(11) DEFAULT NULL COMMENT '总经验'," +
                    "  `exp_current` int(11) DEFAULT NULL COMMENT '当前等级经验'," +
                    "  `max_member` int(11) DEFAULT NULL COMMENT '最大成员数'," +
                    "  `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效'," +
                    "  `created` datetime DEFAULT NULL COMMENT '创建时间'," +
                    "  `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间'," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='创建人名字';";
        try {
            MysqlUtil.execCommand(tableSql);
        }  catch (Exception e) {
        }
    }

    public static  List<DragonGuildEntity> selectDragonGuildDataNum(Integer current){
        String sql = "select * from dragon_guild where status = 1 limit "+current+", 5";
        List<DragonGuildEntity> dragonGuildEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    DragonGuildEntity dragonGuildEntity = new DragonGuildEntity();
                    dragonGuildEntity.setId(rst.getInt("id"));
                    dragonGuildEntity.setUid(rst.getString("uid"));
                    dragonGuildEntity.setCreator(rst.getString("creator"));
                    dragonGuildEntity.setName(rst.getString("name"));
                    dragonGuildEntity.setLevel(rst.getInt("level"));
                    dragonGuildEntity.setExpAll(rst.getInt("exp_all"));
                    dragonGuildEntity.setExpCurrent(rst.getInt("exp_current"));
                    dragonGuildEntity.setMaxMember(rst.getInt("max_member"));
                    dragonGuildEntity.setStatus(rst.getInt("status"));
                    dragonGuildEntity.setCreated(rst.getDate("created"));
                    dragonGuildEntity.setModified(rst.getDate("modified"));
                    dragonGuildEntitys.add(dragonGuildEntity);
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildEntitys;
    }
    public static DragonGuildEntity selectDragonGuildByName(String name){
        String sql = "select * from dragon_guild where status = 1 and name =  '"+name+"'";
        DragonGuildEntity dragonGuildEntity = new DragonGuildEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildEntity.setId(rst.getInt("id"));
                    dragonGuildEntity.setUid(rst.getString("uid"));
                    dragonGuildEntity.setCreator(rst.getString("creator"));
                    dragonGuildEntity.setName(rst.getString("name"));
                    dragonGuildEntity.setLevel(rst.getInt("level"));
                    dragonGuildEntity.setExpAll(rst.getInt("exp_all"));
                    dragonGuildEntity.setExpCurrent(rst.getInt("exp_current"));
                    dragonGuildEntity.setMaxMember(rst.getInt("max_member"));
                    dragonGuildEntity.setStatus(rst.getInt("status"));
                    dragonGuildEntity.setCreated(rst.getDate("created"));
                    dragonGuildEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildEntity;
    }
    public static DragonGuildEntity selectDragonGuildById(Integer id){
        String sql = "select * from dragon_guild where status = 1 and id =  '"+id+"'";
        DragonGuildEntity dragonGuildEntity = new DragonGuildEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildEntity.setId(rst.getInt("id"));
                    dragonGuildEntity.setUid(rst.getString("uid"));
                    dragonGuildEntity.setCreator(rst.getString("creator"));
                    dragonGuildEntity.setName(rst.getString("name"));
                    dragonGuildEntity.setLevel(rst.getInt("level"));
                    dragonGuildEntity.setExpAll(rst.getInt("exp_all"));
                    dragonGuildEntity.setExpCurrent(rst.getInt("exp_current"));
                    dragonGuildEntity.setMaxMember(rst.getInt("max_member"));
                    dragonGuildEntity.setStatus(rst.getInt("status"));
                    dragonGuildEntity.setCreated(rst.getDate("created"));
                    dragonGuildEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildEntity;
    }
    //查询总页数
    public static Integer selectTeamCount(){
        String sql = "select count(*) as datacount from dragon_guild where status = 1";
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

    public static DragonGuildEntity selectDragonGuildByCreator(String creator){
        String sql = "select * from dragon_guild where status = 1 and creator =  '"+creator+"'";
        DragonGuildEntity dragonGuildEntity = new DragonGuildEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    dragonGuildEntity.setId(rst.getInt("id"));
                    dragonGuildEntity.setUid(rst.getString("uid"));
                    dragonGuildEntity.setCreator(rst.getString("creator"));
                    dragonGuildEntity.setName(rst.getString("name"));
                    dragonGuildEntity.setLevel(rst.getInt("level"));
                    dragonGuildEntity.setExpAll(rst.getInt("exp_all"));
                    dragonGuildEntity.setExpCurrent(rst.getInt("exp_current"));
                    dragonGuildEntity.setMaxMember(rst.getInt("max_member"));
                    dragonGuildEntity.setStatus(rst.getInt("status"));
                    dragonGuildEntity.setCreated(rst.getDate("created"));
                    dragonGuildEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  dragonGuildEntity;
    }

    //修改配置
    public static String updateUserConfigDataNum(Integer id, DragonGuildEntity dragonGuildEntity){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update dragon_guild set");
        if (dragonGuildEntity.getUid() != null && !dragonGuildEntity.getUid().equals("")) {
            stringBuffer.append(" uid = "+"'"+dragonGuildEntity.getUid()+"'"+",");
        }
        if (dragonGuildEntity.getCreator() != null) {
            stringBuffer.append(" creator = "+dragonGuildEntity.getCreator()+",");
        }
        if (dragonGuildEntity.getName() != null && !dragonGuildEntity.getName().equals("")) {
            stringBuffer.append(" name = "+"'"+dragonGuildEntity.getName()+"'"+",");
        }
        if (dragonGuildEntity.getLevel() != null) {
            stringBuffer.append(" level = "+dragonGuildEntity.getLevel()+",");
        }
        if (dragonGuildEntity.getExpAll() != null) {
            stringBuffer.append(" exp_all = "+dragonGuildEntity.getExpAll()+",");
        }
        if (dragonGuildEntity.getExpCurrent() != null) {
            stringBuffer.append(" exp_current = "+dragonGuildEntity.getExpCurrent()+",");
        }
        if (dragonGuildEntity.getMaxMember() != null) {
            stringBuffer.append(" max_member = "+dragonGuildEntity.getMaxMember()+",");
        }
        if (dragonGuildEntity.getStatus() != null) {
            stringBuffer.append(" status = "+dragonGuildEntity.getStatus()+",");
        }
        if (dragonGuildEntity.getCreated() != null) {
            stringBuffer.append(" created = "+"'"+dragonGuildEntity.getCreated()+"'"+",");
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
        String sql = "update dragon_guild set status = -1 where id = "+id;
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
                " where TABLE_NAME = 'dragon_guild';";
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