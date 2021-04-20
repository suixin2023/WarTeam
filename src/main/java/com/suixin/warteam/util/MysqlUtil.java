package com.suixin.warteam.util;

import com.suixin.warteam.WarTeam;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

public class MysqlUtil {
    private static Connection conn = null;
    private static String dbDriver;    //定义驱动
    private static String dbURL;        //定义URL
    private static String userName;    //定义用户名
    private static String password;    //定义密码

    //从配置文件取数据库链接参数
    private static void loadConnProperties(FileConfiguration fileConfiguration) {
        //读取数据库配置
        dbDriver = fileConfiguration.getString("Mysql.dbDriver");//从配置文件中取得相应的参数并设置类变量
        dbURL = fileConfiguration.getString("Mysql.dbURL");
        userName = fileConfiguration.getString("Mysql.userName");
        password = fileConfiguration.getString("Mysql.password");
    }

    public static boolean openConnection(FileConfiguration fileConfiguration) {
        try {
            loadConnProperties(fileConfiguration);
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbURL, userName, password);
            return true;
        } catch (ClassNotFoundException classnotfoundexception) {
            classnotfoundexception.printStackTrace();
            System.err.println("db: " + classnotfoundexception.getMessage());
            return false;
        } catch (SQLException sqlexception) {
            System.err.println("db.getconn(): " + sqlexception.getMessage());
            return false;
        }
    }

    @Override
    protected void finalize() throws Exception {
        try {
            if (null != conn){
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    // 查询并得到结果集
    public static ResultSet execQuery(String sql) throws Exception {
        ResultSet rstSet = null;
        try {
            if (null == conn){
                MysqlUtil.openConnection(WarTeam.getSystemConfig());
            }
            Statement stmt = conn.createStatement();
            rstSet = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rstSet;
    }

    // 查询并得到结果集(预编译对象)
    public static ResultSet executeQuery(String sql,String [] params) throws Exception {
        ResultSet rstSet = null;
        try {
            if (null == conn){
                throw new Exception("Database not connected!");
            }
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,params[0]);
            rstSet = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rstSet;
    }

    // 插入一条新纪录，并获取标识列的值
    public static ResultSet getInsertObjectIDs(String insertSql) throws Exception {
        ResultSet rst = null;
        try {
            if (null == conn){
                MysqlUtil.openConnection(WarTeam.getSystemConfig());
            }

            Statement stmt = conn.createStatement();

            stmt.executeUpdate(insertSql, Statement.RETURN_GENERATED_KEYS);
            rst = stmt.getGeneratedKeys();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }

    //以参数SQL模式插入新纪录，并获取标识列的值
    public static ResultSet getInsertObjectIDs(String insertSql, Object[] params) throws Exception {
        ResultSet rst = null;
        PreparedStatement pstmt = null;
        try {
            if (null == conn){
                MysqlUtil.openConnection(WarTeam.getSystemConfig());
            }
            pstmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);

            if (null != params) {
                for (int i = 0; i < params.length; i++) {
                    pstmt.setObject(i + 1, params[i]);
                }
            }
            pstmt.executeUpdate();
            rst = pstmt.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rst;
    }


    // 插入、更新、删除
    public static int execCommand(String sql) throws Exception {
        int flag = 0;
        try {
            if (null == conn){
                MysqlUtil.openConnection(WarTeam.getSystemConfig());
            }
            Statement stmt = conn.createStatement();
            flag = stmt.executeUpdate(sql);

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return flag;
    }

    /*    // 存储过程调用
public void callStordProc(String sql, Object[] inParams, SqlParameter[] outParams) throws Exception {
    CallableStatement  cst = null ;
    try {
        if (null == conn)
            throw new Exception("Database not connected!");
        cst = conn.prepareCall(sql);

        if(null != inParams){
            for (int i = 0; i < inParams.length; i++) {
                cst.setObject(i + 1, inParams[i]);
            }
        }

        if (null!=outParams){
            for (int i = 0; i < inParams.length; i++) {
                cst.registerOutParameter(outParams[i].getName(), outParams[i].getType());
            }
        }
        cst.execute();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
*/
    // 释放资源
    public static void close(ResultSet rst) throws Exception {
        try {
            Statement stmt = rst.getStatement();
            rst.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static PreparedStatement execPrepared(String psql) throws Exception {
        PreparedStatement pstmt = null;
        try {
            if (null == conn){
                MysqlUtil.openConnection(WarTeam.getSystemConfig());
            }
            pstmt = conn.prepareStatement(psql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pstmt;
    }


    // 释放资源
    public static void close(Statement stmt) throws Exception {
        try {
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 释放资源
    public static void close() throws SQLException, Exception {
        if (null != conn) {
            conn.close();
            conn = null;
        }
    }

    public static Connection getConn() {
        return conn;
    }

}