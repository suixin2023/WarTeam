package com.suixin.warteam.handler;

import com.suixin.warteam.entity.WarTeamMemberEntity;
import com.suixin.warteam.util.MysqlUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class WarTeamMemBerDatabaseHandler {
//    CREATE TABLE `war_team_member` (
//            `id` int(11) NOT NULL AUTO_INCREMENT,
//  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid',
//            `war_team_id` int(11) DEFAULT NULL COMMENT '战队Id',
//            `war_team_name` varchar(255) DEFAULT NULL COMMENT '战队名字',
//            `position` varchar(255) DEFAULT NULL COMMENT '战队职位',
//            `exp` int(11) DEFAULT NULL COMMENT '贡献exp',
//            `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效',
//            `created` datetime DEFAULT NULL COMMENT '创建时间',
//            `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='战队成员表';

    //配置写入
    public static int insert(WarTeamMemberEntity warTeamMemberEntity){
        String sql = "insert into war_team_member(uid, war_team_id, war_team_name , position,exp,status,created,modified)"
                + " values(?,?, ?, ?, ?,?,?, ?)";
        Object [] params = new Object[8];
        params[0]= warTeamMemberEntity.getUid();
        params[1]= warTeamMemberEntity.getWarTeamId();
        params[2]= warTeamMemberEntity.getWarTeamName();
        params[3]= warTeamMemberEntity.getPosition();
        params[4]= warTeamMemberEntity.getExp();
        params[5]= warTeamMemberEntity.getStatus();
        params[6]= warTeamMemberEntity.getCreated();
        params[7]= warTeamMemberEntity.getModified();
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
        String tableSql = "CREATE TABLE `war_team_member` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `uid` varchar(100) DEFAULT NULL COMMENT '创建人uuid'," +
                "  `war_team_id` int(11) DEFAULT NULL COMMENT '战队Id'," +
                "  `war_team_name` varchar(255) DEFAULT NULL COMMENT '战队名字'," +
                "  `position` varchar(255) DEFAULT NULL COMMENT '战队职位'," +
                "  `exp` int(11) DEFAULT NULL COMMENT '贡献exp'," +
                "  `status` int(11) DEFAULT NULL COMMENT '有效性1有效-1无效'," +
                "  `created` datetime DEFAULT NULL COMMENT '创建时间'," +
                "  `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间'," +
                "  PRIMARY KEY (`id`)" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='战队成员表';";
        try {
            MysqlUtil.execCommand(tableSql);
        }  catch (Exception e) {
        }
    }

    public static  List<WarTeamMemberEntity> selectWarTeamMemBerDataNum(Integer limit, Integer warTeamId){
        String sql = "select * from war_team_member where status = 1 and war_team_id = "+warTeamId+" limit "+limit+", 20";
        List<WarTeamMemberEntity> warTeamMemberEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    WarTeamMemberEntity warTeamMemberEntity = new WarTeamMemberEntity();
                    warTeamMemberEntity.setId(rst.getInt("id"));
                    warTeamMemberEntity.setUid(rst.getString("uid"));
                    warTeamMemberEntity.setWarTeamId(rst.getInt("war_team_id"));
                    warTeamMemberEntity.setWarTeamName(rst.getString("war_team_name"));
                    warTeamMemberEntity.setPosition(rst.getString("position"));
                    warTeamMemberEntity.setExp(rst.getInt("exp"));
                    warTeamMemberEntity.setStatus(rst.getInt("status"));
                    warTeamMemberEntity.setCreated(rst.getDate("created"));
                    warTeamMemberEntity.setModified(rst.getDate("modified"));
                    warTeamMemberEntitys.add(warTeamMemberEntity);
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamMemberEntitys;
    }

    public static  Integer selectCount(Integer warTeamId){
        String sql = "select count(*) as datacount from war_team_member where status = 1 and war_team_id = "+warTeamId;
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
    public static  List<WarTeamMemberEntity> selectWarTeamMemBerByWarTeamId(Integer warTeamId){
        String sql = "select * from war_team_member where status = 1 and war_team_id = " +warTeamId;
        List<WarTeamMemberEntity> warTeamMemberEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    WarTeamMemberEntity warTeamMemberEntity = new WarTeamMemberEntity();
                    warTeamMemberEntity.setId(rst.getInt("id"));
                    warTeamMemberEntity.setUid(rst.getString("uid"));
                    warTeamMemberEntity.setWarTeamId(rst.getInt("war_team_id"));
                    warTeamMemberEntity.setWarTeamName(rst.getString("war_team_name"));
                    warTeamMemberEntity.setPosition(rst.getString("position"));
                    warTeamMemberEntity.setExp(rst.getInt("exp"));
                    warTeamMemberEntity.setStatus(rst.getInt("status"));
                    warTeamMemberEntity.setCreated(rst.getDate("created"));
                    warTeamMemberEntity.setModified(rst.getDate("modified"));
                    warTeamMemberEntitys.add(warTeamMemberEntity);
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamMemberEntitys;
    }

    public static  WarTeamMemberEntity selectWarTeamMemBerByUid(String uid){
        String sql = "select * from war_team_member where status = 1 and uid = '" +uid+"'";
        WarTeamMemberEntity warTeamMemberEntity = new WarTeamMemberEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    warTeamMemberEntity.setId(rst.getInt("id"));
                    warTeamMemberEntity.setUid(rst.getString("uid"));
                    warTeamMemberEntity.setWarTeamId(rst.getInt("war_team_id"));
                    warTeamMemberEntity.setWarTeamName(rst.getString("war_team_name"));
                    warTeamMemberEntity.setPosition(rst.getString("position"));
                    warTeamMemberEntity.setExp(rst.getInt("exp"));
                    warTeamMemberEntity.setStatus(rst.getInt("status"));
                    warTeamMemberEntity.setCreated(rst.getDate("created"));
                    warTeamMemberEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamMemberEntity;
    }

    //修改配置
    public static String updateUserConfigDataNum(Integer id,WarTeamMemberEntity warTeamMemberEntity){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update war_team_member set");
        if (warTeamMemberEntity.getUid() != null && !warTeamMemberEntity.getUid().equals("")) {
            stringBuffer.append(" uid = "+"'"+warTeamMemberEntity.getUid()+"'"+",");
        }
        if (warTeamMemberEntity.getWarTeamId() != null) {
            stringBuffer.append(" war_team_id = "+warTeamMemberEntity.getWarTeamId()+",");
        }
        if (warTeamMemberEntity.getWarTeamName() != null && !warTeamMemberEntity.getWarTeamName().equals("")) {
            stringBuffer.append(" war_team_name = "+"'"+warTeamMemberEntity.getWarTeamName()+"'"+",");
        }
        if (warTeamMemberEntity.getPosition() != null && !warTeamMemberEntity.getPosition().equals("")) {
            stringBuffer.append(" position = "+"'"+warTeamMemberEntity.getPosition()+"'"+",");
        }
        if (warTeamMemberEntity.getExp() != null) {
            stringBuffer.append(" exp = "+warTeamMemberEntity.getExp()+",");
        }
        if (warTeamMemberEntity.getStatus() != null) {
            stringBuffer.append(" status = "+warTeamMemberEntity.getStatus()+",");
        }
        if (warTeamMemberEntity.getCreated() != null) {
            stringBuffer.append(" created = "+"'"+warTeamMemberEntity.getCreated()+"'"+",");
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
        String sql = "update war_team_member set status = -1 where uid = "+uid;
        try {
            int i = MysqlUtil.execCommand(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除所有队员
    public static void deleteAll(Integer warTeamId){
        String sql = "update war_team_member set status = -1 where war_team_id = "+warTeamId;
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
                " where TABLE_NAME = 'war_team_member';";
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