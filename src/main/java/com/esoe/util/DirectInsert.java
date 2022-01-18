package com.esoe.util;

import java.sql.*;
import java.util.HashMap;

public class DirectInsert {

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
