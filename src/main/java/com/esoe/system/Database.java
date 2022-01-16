package com.esoe.system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.File;
import java.sql.*;
import java.util.HashMap;
import java.util.Scanner;

public class Database  {

    public void importSQLFile(String path) {
        try{
            File f = new File(path);
            Scanner s = new Scanner(f);
            s.useDelimiter("(;(\r)?\n)|((\r)?\n)?(--)?.*(--(\r)?\n)");
            Statement st = null;
            st = connect().createStatement();
            while (s.hasNext()){
                String line = s.next();
                if (line.startsWith("/*!") && line.endsWith("*/")){
                    int i = line.indexOf(' ');
                    line = line.substring(i + 1, line.length() - " */".length());
                }
                if (line.trim().length() > 0){
                    st.execute(line);
                }
            }
            s.close();
            st.close();
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection
                ("jdbc:mariadb://localhost/HSR?user=root&password=123");
            return conn;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public static Boolean insert(String table, String values) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO " + table +  " VALUES " + "(" + values + ");";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Boolean insert(String table, String columns, String values) {
        try (Connection conn = connect()) {
            String sql = "INSERT INTO " + table + " (" + columns + ") " + " VALUES " + "(" + values + ");";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static Boolean insert(String table, HashMap<String, String> attr) {
        String columns = "", values = "";
        for (String key : attr.keySet()) {
            String value = attr.get(key);
            columns += (key + ", ");
            values += ("'" + value + "'" + ", ");
        }
        columns = columns.substring(0, columns.length() - 2);
        values = values.substring(0, values.length() - 2);

        try (Connection conn = connect()) {
            String sql = "INSERT INTO " + table + " (" + columns + ") " + " VALUES " + "(" + values + ");";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
}
