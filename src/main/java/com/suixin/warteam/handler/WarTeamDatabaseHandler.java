package com.suixin.warteam.handler;

import com.suixin.warteam.entity.WarTeamEntity;
import com.suixin.warteam.util.MysqlUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class WarTeamDatabaseHandler {
//    CREATE TABLE `war_team` (
//            `id` int(11) NOT NULL AUTO_INCREMENT,
//  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid',
//            `creator` varchar(255) DEFAULT NULL COMMENT '创建人',
//            `name` varchar(255) DEFAULT NULL COMMENT '战队名字',
//            `level` int(11) DEFAULT '1' COMMENT '等级',
//            `exp_all` int(11) DEFAULT NULL COMMENT '总经验',
//            `exp_current` int(11) DEFAULT NULL COMMENT '当前等级经验',
//            `max_member` int(11) DEFAULT NULL COMMENT '最大成员数',
//            `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效',
//            `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='创建人名字';

    //配置写入
    public static int insert(WarTeamEntity warTeamEntity){
        String sql = "insert into war_team(uid, creator, name , level,exp_all,exp_current,max_member,status,created,modified)"
                + " values(?,?, ?, ?, ?,?,?, ?, ?, ?)";
        Object [] params = new Object[10];
        params[0]= warTeamEntity.getUid();
        params[1]= warTeamEntity.getCreator();
        params[2]= warTeamEntity.getName();
        params[3]= warTeamEntity.getLevel();
        params[4]= warTeamEntity.getExpAll();
        params[5]= warTeamEntity.getExpCurrent();
        params[6]= warTeamEntity.getMaxMember();
        params[7]= warTeamEntity.getStatus();
        params[8]= warTeamEntity.getCreated();
        params[9]= warTeamEntity.getModified();
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
            String tableSql = "CREATE TABLE `war_team` (" +
                    "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                    "  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid'," +
                    "  `creator` varchar(255) DEFAULT NULL COMMENT '创建人'," +
                    "  `name` varchar(255) DEFAULT NULL COMMENT '战队名字'," +
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

    public static  List<WarTeamEntity> selectWarTeamDataNum(Integer current){
        String sql = "select * from war_team where status = 1 limit "+current+", 20";
        List<WarTeamEntity> warTeamEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    WarTeamEntity warTeamEntity = new WarTeamEntity();
                    warTeamEntity.setId(rst.getInt("id"));
                    warTeamEntity.setUid(rst.getString("uid"));
                    warTeamEntity.setCreator(rst.getString("creator"));
                    warTeamEntity.setName(rst.getString("name"));
                    warTeamEntity.setLevel(rst.getInt("level"));
                    warTeamEntity.setExpAll(rst.getInt("exp_all"));
                    warTeamEntity.setExpCurrent(rst.getInt("exp_current"));
                    warTeamEntity.setMaxMember(rst.getInt("max_member"));
                    warTeamEntity.setStatus(rst.getInt("status"));
                    warTeamEntity.setCreated(rst.getDate("created"));
                    warTeamEntity.setModified(rst.getDate("modified"));
                    warTeamEntitys.add(warTeamEntity);
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamEntitys;
    }
    public static  WarTeamEntity selectWarTeamByName(String name){
        String sql = "select * from war_team where status = 1 and name =  '"+name+"'";
        WarTeamEntity warTeamEntity = new WarTeamEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    warTeamEntity.setId(rst.getInt("id"));
                    warTeamEntity.setUid(rst.getString("uid"));
                    warTeamEntity.setCreator(rst.getString("creator"));
                    warTeamEntity.setName(rst.getString("name"));
                    warTeamEntity.setLevel(rst.getInt("level"));
                    warTeamEntity.setExpAll(rst.getInt("exp_all"));
                    warTeamEntity.setExpCurrent(rst.getInt("exp_current"));
                    warTeamEntity.setMaxMember(rst.getInt("max_member"));
                    warTeamEntity.setStatus(rst.getInt("status"));
                    warTeamEntity.setCreated(rst.getDate("created"));
                    warTeamEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamEntity;
    }

    public static  WarTeamEntity selectWarTeamByCreator(String creator){
        String sql = "select * from war_team where status = 1 and creator =  '"+creator+"'";
        WarTeamEntity warTeamEntity = new WarTeamEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    warTeamEntity.setId(rst.getInt("id"));
                    warTeamEntity.setUid(rst.getString("uid"));
                    warTeamEntity.setCreator(rst.getString("creator"));
                    warTeamEntity.setName(rst.getString("name"));
                    warTeamEntity.setLevel(rst.getInt("level"));
                    warTeamEntity.setExpAll(rst.getInt("exp_all"));
                    warTeamEntity.setExpCurrent(rst.getInt("exp_current"));
                    warTeamEntity.setMaxMember(rst.getInt("max_member"));
                    warTeamEntity.setStatus(rst.getInt("status"));
                    warTeamEntity.setCreated(rst.getDate("created"));
                    warTeamEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamEntity;
    }

    //修改配置
    public static String updateUserConfigDataNum(Integer id,WarTeamEntity warTeamEntity){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update war_team set");
        if (warTeamEntity.getUid() != null && !warTeamEntity.getUid().equals("")) {
            stringBuffer.append(" uid = "+"'"+warTeamEntity.getUid()+"'"+",");
        }
        if (warTeamEntity.getCreator() != null) {
            stringBuffer.append(" creator = "+warTeamEntity.getCreator()+",");
        }
        if (warTeamEntity.getName() != null && !warTeamEntity.getName().equals("")) {
            stringBuffer.append(" name = "+"'"+warTeamEntity.getName()+"'"+",");
        }
        if (warTeamEntity.getLevel() != null) {
            stringBuffer.append(" level = "+warTeamEntity.getLevel()+",");
        }
        if (warTeamEntity.getExpAll() != null) {
            stringBuffer.append(" exp_all = "+warTeamEntity.getExpAll()+",");
        }
        if (warTeamEntity.getExpCurrent() != null) {
            stringBuffer.append(" exp_current = "+warTeamEntity.getExpCurrent()+",");
        }
        if (warTeamEntity.getMaxMember() != null) {
            stringBuffer.append(" max_member = "+warTeamEntity.getMaxMember()+",");
        }
        if (warTeamEntity.getStatus() != null) {
            stringBuffer.append(" status = "+warTeamEntity.getStatus()+",");
        }
        if (warTeamEntity.getCreated() != null) {
            stringBuffer.append(" created = "+"'"+warTeamEntity.getCreated()+"'"+",");
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

    //删除战队
    public static void deleteById(Integer id){
        String sql = "update war_team set status = -1 where id = "+id;
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
                " where TABLE_NAME = 'war_team';";
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