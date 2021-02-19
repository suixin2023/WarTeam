package com.suixin.warteam.handler;

import com.suixin.warteam.entity.WarTeamApplyEntity;
import com.suixin.warteam.util.MysqlUtil;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class WarTeamApplyDatabaseHandler {
//    CREATE TABLE `war_team_apply` (
//            `id` int(11) NOT NULL AUTO_INCREMENT,
//  `uid` varchar(50) DEFAULT NULL COMMENT '申请人',
//            `apply` varchar(50) DEFAULT NULL COMMENT '审核人',
//            `war_team_id` int(11) DEFAULT NULL COMMENT '申请战队',
//            `war_team_name` varchar(50) DEFAULT NULL COMMENT '申请战队',
//            `status` int(11) DEFAULT NULL COMMENT '审批通过或拒绝',
//            `created` datetime DEFAULT NULL COMMENT '创建时间',
//            `modified` timestamp NULL DEFAULT NULL COMMENT '修改时间',
//    PRIMARY KEY (`id`)
//) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='申请表';

    //配置写入
    public static int insert(WarTeamApplyEntity warTeamApplyEntity){
        String sql = "insert into war_team_apply(uid,apply, war_team_id, war_team_name ,status,created,modified)"
                + " values(?,?, ?, ?, ?, ?, ?)";
        Object [] params = new Object[7];
        params[0]= warTeamApplyEntity.getUid();
        params[1]= warTeamApplyEntity.getApply();
        params[2]= warTeamApplyEntity.getWarTeamId();
        params[3]= warTeamApplyEntity.getWarTeamName();
        params[4]= warTeamApplyEntity.getStatus();
        params[5]= warTeamApplyEntity.getCreated();
        params[6]= warTeamApplyEntity.getModified();
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
        String tableSql = "CREATE TABLE `war_team_apply` (" +
                "  `id` int(11) NOT NULL AUTO_INCREMENT," +
                "  `uid` varchar(50) DEFAULT NULL COMMENT '申请人'," +
                "  `apply` varchar(50) DEFAULT NULL COMMENT '审核人'," +
                "  `war_team_id` int(11) DEFAULT NULL COMMENT '申请战队'," +
                "  `war_team_name` varchar(50) DEFAULT NULL COMMENT '申请战队'," +
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

    public static  List<WarTeamApplyEntity> selectWarTeamApplyDataNum(Integer current){
        String sql = "select * from war_team_apply where status = 1 limit "+current+", 20";
        List<WarTeamApplyEntity> warTeamApplyEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    WarTeamApplyEntity warTeamApplyEntity = new WarTeamApplyEntity();
                    warTeamApplyEntity.setId(rst.getInt("id"));
                    warTeamApplyEntity.setUid(rst.getString("uid"));
                    warTeamApplyEntity.setApply(rst.getString("apply"));
                    warTeamApplyEntity.setWarTeamId(rst.getInt("war_team_id"));
                    warTeamApplyEntity.setWarTeamName(rst.getString("war_team_name"));
                    warTeamApplyEntity.setStatus(rst.getInt("status"));
                    warTeamApplyEntity.setCreated(rst.getDate("created"));
                    warTeamApplyEntity.setModified(rst.getDate("modified"));
                    warTeamApplyEntitys.add(warTeamApplyEntity);
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamApplyEntitys;
    }

    public static  Integer selectCount(Integer warTeamId){
        String sql = "select count(*) as datacount from war_team_apply where status = 1 and war_team_id = "+warTeamId;
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
    public static  WarTeamApplyEntity selectWarTeamApplyByUid(String uid,String warTeamName){
        String sql = "select * from war_team_apply where status = 0 and uid = '" +uid +"' and war_team_name = '" +warTeamName+"'";
        WarTeamApplyEntity warTeamApplyEntity = new WarTeamApplyEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    warTeamApplyEntity.setId(rst.getInt("id"));
                    warTeamApplyEntity.setUid(rst.getString("uid"));
                    warTeamApplyEntity.setApply(rst.getString("apply"));
                    warTeamApplyEntity.setWarTeamId(rst.getInt("war_team_id"));
                    warTeamApplyEntity.setWarTeamName(rst.getString("war_team_name"));
                    warTeamApplyEntity.setStatus(rst.getInt("status"));
                    warTeamApplyEntity.setCreated(rst.getDate("created"));
                    warTeamApplyEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamApplyEntity;
    }

    public static  List<WarTeamApplyEntity> selectWarTeamApplyByWarTeamId(Integer limit, Integer warTeamId){
        String sql = "select * from war_team_apply where status = 0 and war_team_id = '" +warTeamId+" limit "+limit+", 6";
        List<WarTeamApplyEntity> warTeamApplyEntitys = new ArrayList<>();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    WarTeamApplyEntity warTeamApplyEntity = new WarTeamApplyEntity();
                    warTeamApplyEntity.setId(rst.getInt("id"));
                    warTeamApplyEntity.setUid(rst.getString("uid"));
                    warTeamApplyEntity.setApply(rst.getString("apply"));
                    warTeamApplyEntity.setWarTeamId(rst.getInt("war_team_id"));
                    warTeamApplyEntity.setWarTeamName(rst.getString("war_team_name"));
                    warTeamApplyEntity.setStatus(rst.getInt("status"));
                    warTeamApplyEntity.setCreated(rst.getDate("created"));
                    warTeamApplyEntity.setModified(rst.getDate("modified"));
                    warTeamApplyEntitys.add(warTeamApplyEntity);
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamApplyEntitys;
    }

    public static  WarTeamApplyEntity selectWarTeamApplyByUidAndApply(String uid, String apply){
        String sql = "select * from war_team_apply where status = 0 and uid = '" +uid +"' and apply = '" +apply+"'";
        WarTeamApplyEntity warTeamApplyEntity = new WarTeamApplyEntity();
        try {
            ResultSet rst = MysqlUtil.execQuery(sql);
            if (rst != null) {
                while (rst.next()) {
                    warTeamApplyEntity.setId(rst.getInt("id"));
                    warTeamApplyEntity.setUid(rst.getString("uid"));
                    warTeamApplyEntity.setApply(rst.getString("apply"));
                    warTeamApplyEntity.setWarTeamId(rst.getInt("war_team_id"));
                    warTeamApplyEntity.setWarTeamName(rst.getString("war_team_name"));
                    warTeamApplyEntity.setStatus(rst.getInt("status"));
                    warTeamApplyEntity.setCreated(rst.getDate("created"));
                    warTeamApplyEntity.setModified(rst.getDate("modified"));
                }
            }

            MysqlUtil.close(rst);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return  warTeamApplyEntity;
    }

    //修改配置
    public static String updateUserConfigDataNum(Integer id,WarTeamApplyEntity warTeamApplyEntity){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("update war_team_apply set");
        if (warTeamApplyEntity.getUid() != null && !warTeamApplyEntity.getUid().equals("")) {
            stringBuffer.append(" uid = "+"'"+warTeamApplyEntity.getUid()+"'"+",");
        }
        if (warTeamApplyEntity.getApply() != null && !warTeamApplyEntity.getApply().equals("")) {
            stringBuffer.append(" apply = "+"'"+warTeamApplyEntity.getApply()+"'"+",");
        }
        if (warTeamApplyEntity.getWarTeamId() != null) {
            stringBuffer.append(" war_team_id = "+warTeamApplyEntity.getWarTeamId()+",");
        }
        if (warTeamApplyEntity.getWarTeamName() != null && !warTeamApplyEntity.getWarTeamName().equals("")) {
            stringBuffer.append(" war_team_name = "+"'"+warTeamApplyEntity.getWarTeamName()+"'"+",");
        }
        if (warTeamApplyEntity.getStatus() != null) {
            stringBuffer.append(" status = "+warTeamApplyEntity.getStatus()+",");
        }
        if (warTeamApplyEntity.getCreated() != null) {
            stringBuffer.append(" created = "+"'"+warTeamApplyEntity.getCreated()+"'"+",");
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
        String sql = "update war_team_apply set status = -1 where uid = '"+uid+"'";
        try {
            int i = MysqlUtil.execCommand(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //删除所有申请
    public static void deleteAll(Integer warTeamId){
        String sql = "update war_team_apply set status = -1 where war_team_id = "+warTeamId;
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
                " where TABLE_NAME = 'war_team_apply';";
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