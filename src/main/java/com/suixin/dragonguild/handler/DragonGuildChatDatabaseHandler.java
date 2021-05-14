package com.suixin.dragonguild.handler;


import com.suixin.dragonguild.entity.DragonGuildChatEntity;
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
        //            `title` varchar(20) DEFAULT NULL COMMENT '标题',
        //            `desc` varchar(255) DEFAULT NULL COMMENT '聊天内容',
        //            `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效',
        //            `created` datetime DEFAULT NULL COMMENT '创建时间',
        //            `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
        //    PRIMARY KEY (`id`)
        //) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天';

        //配置写入
        public static int insert(DragonGuildChatEntity dragonGuildChatEntity){
            String sql = "insert into dragon_guild_chat(uid, creator, guild_id , title,desc,status,created,modified)"
                    + " values(?,?, ?, ?, ?,?,?, ?, ?, ?)";
            Object [] params = new Object[7];
            params[0]= dragonGuildChatEntity.getUid();
            params[1]= dragonGuildChatEntity.getCreator();
            params[2]= dragonGuildChatEntity.getGuildId();
            params[3]= dragonGuildChatEntity.getDesc();
            params[4]= dragonGuildChatEntity.getStatus();
            params[5]= dragonGuildChatEntity.getCreated();
            params[6]= dragonGuildChatEntity.getModified();
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
            String tableSql = "CREATE TABLE `dragon_guild_chat` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid'," +
                    "  `creator` varchar(255) DEFAULT NULL COMMENT '创建人'," +
                    "  `guild_id` int(11) DEFAULT NULL COMMENT '公会'," +
                    "  `desc` varchar(255) DEFAULT NULL COMMENT '聊天内容'," +
                    "  `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效'," +
                    "  `created` datetime DEFAULT NULL COMMENT '创建时间'," +
                    "  `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间'," +
                    "  PRIMARY KEY (`id`)" +
                    ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='聊天';";
            try {
                MysqlUtil.execCommand(tableSql);
            }  catch (Exception e) {
            }
        }

        public static List<DragonGuildChatEntity> selectDragonGuildDataNum(Integer current){
            String sql = "select * from dragon_guild_chat where status = 1 limit "+current+", 5";
            List<DragonGuildChatEntity> dragonGuildChatEntitys = new ArrayList<>();
            try {
                ResultSet rst = MysqlUtil.execQuery(sql);
                if (rst != null) {
                    while (rst.next()) {
                        DragonGuildChatEntity dragonGuildChatEntity = new DragonGuildChatEntity();
                        dragonGuildChatEntity.setId(rst.getInt("id"));
                        dragonGuildChatEntity.setUid(rst.getString("uid"));
                        dragonGuildChatEntity.setCreator(rst.getString("creator"));
                        dragonGuildChatEntity.setGuildId(rst.getInt("guild_id"));
                        dragonGuildChatEntity.setDesc(rst.getString("desc"));
                        dragonGuildChatEntity.setStatus(rst.getInt("status"));
                        dragonGuildChatEntity.setCreated(rst.getDate("created"));
                        dragonGuildChatEntity.setModified(rst.getDate("modified"));
                        dragonGuildChatEntitys.add(dragonGuildChatEntity);
                    }
                }

                MysqlUtil.close(rst);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return  dragonGuildChatEntitys;
        }
        public static DragonGuildChatEntity selectDragonGuildByName(String name){
            String sql = "select * from dragon_guild_chat where status = 1 and name =  '"+name+"'";
            DragonGuildChatEntity dragonGuildChatEntity = new DragonGuildChatEntity();
            try {
                ResultSet rst = MysqlUtil.execQuery(sql);
                if (rst != null) {
                    while (rst.next()) {
                        dragonGuildChatEntity.setId(rst.getInt("id"));
                        dragonGuildChatEntity.setUid(rst.getString("uid"));
                        dragonGuildChatEntity.setCreator(rst.getString("creator"));
                        dragonGuildChatEntity.setGuildId(rst.getInt("guild_id"));
                        dragonGuildChatEntity.setDesc(rst.getString("desc"));
                        dragonGuildChatEntity.setStatus(rst.getInt("status"));
                        dragonGuildChatEntity.setCreated(rst.getDate("created"));
                        dragonGuildChatEntity.setModified(rst.getDate("modified"));
                    }
                }

                MysqlUtil.close(rst);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return  dragonGuildChatEntity;
        }
        public static DragonGuildChatEntity selectDragonGuildById(Integer id){
            String sql = "select * from dragon_guild_chat where status = 1 and id =  '"+id+"'";
            DragonGuildChatEntity dragonGuildChatEntity = new DragonGuildChatEntity();
            try {
                ResultSet rst = MysqlUtil.execQuery(sql);
                if (rst != null) {
                    while (rst.next()) {
                        dragonGuildChatEntity.setId(rst.getInt("id"));
                        dragonGuildChatEntity.setUid(rst.getString("uid"));
                        dragonGuildChatEntity.setCreator(rst.getString("creator"));
                        dragonGuildChatEntity.setGuildId(rst.getInt("guild_id"));
                        dragonGuildChatEntity.setDesc(rst.getString("desc"));
                        dragonGuildChatEntity.setStatus(rst.getInt("status"));
                        dragonGuildChatEntity.setCreated(rst.getDate("created"));
                        dragonGuildChatEntity.setModified(rst.getDate("modified"));
                    }
                }

                MysqlUtil.close(rst);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return  dragonGuildChatEntity;
        }
        //查询总页数
        public static Integer selectTeamCount(){
            String sql = "select count(*) as datacount from dragon_guild_chat where status = 1";
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

        public static DragonGuildChatEntity selectDragonGuildByCreator(String creator){
            String sql = "select * from dragon_guild_chat where status = 1 and creator =  '"+creator+"'";
            DragonGuildChatEntity dragonGuildChatEntity = new DragonGuildChatEntity();
            try {
                ResultSet rst = MysqlUtil.execQuery(sql);
                if (rst != null) {
                    while (rst.next()) {
                        dragonGuildChatEntity.setId(rst.getInt("id"));
                        dragonGuildChatEntity.setUid(rst.getString("uid"));
                        dragonGuildChatEntity.setCreator(rst.getString("creator"));
                        dragonGuildChatEntity.setGuildId(rst.getInt("guild_id"));
                        dragonGuildChatEntity.setDesc(rst.getString("desc"));
                        dragonGuildChatEntity.setStatus(rst.getInt("status"));
                        dragonGuildChatEntity.setCreated(rst.getDate("created"));
                        dragonGuildChatEntity.setModified(rst.getDate("modified"));
                    }
                }

                MysqlUtil.close(rst);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return  dragonGuildChatEntity;
        }

        //修改配置
        public static String updateUserConfigDataNum(Integer id, DragonGuildChatEntity dragonGuildChatEntity){
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("update dragon_guild_chat set");
            if (dragonGuildChatEntity.getUid() != null && !dragonGuildChatEntity.getUid().equals("")) {
                stringBuffer.append(" uid = "+"'"+dragonGuildChatEntity.getUid()+"'"+",");
            }
            if (dragonGuildChatEntity.getCreator() != null) {
                stringBuffer.append(" creator = "+dragonGuildChatEntity.getCreator()+",");
            }
            if (dragonGuildChatEntity.getGuildId() != null) {
                stringBuffer.append(" guild_id = "+dragonGuildChatEntity.getGuildId()+",");
            }
            if (dragonGuildChatEntity.getDesc() != null && !dragonGuildChatEntity.getDesc().equals("")) {
                stringBuffer.append(" desc = "+"'"+dragonGuildChatEntity.getDesc()+"'"+",");
            }
            if (dragonGuildChatEntity.getStatus() != null) {
                stringBuffer.append(" status = "+dragonGuildChatEntity.getStatus()+",");
            }
            if (dragonGuildChatEntity.getCreated() != null) {
                stringBuffer.append(" created = "+"'"+dragonGuildChatEntity.getCreated()+"'"+",");
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
            String sql = "update dragon_guild_chat set status = -1 where id = "+id;
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
                    " where TABLE_NAME = 'dragon_guild_chat';";
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