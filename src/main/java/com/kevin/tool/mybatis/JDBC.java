package com.kevin.tool.mybatis;

import lombok.Data;

import java.sql.*;
/**
 *
 * @author lihongmin
 * @date 2020/7/7 8:55
 * @since 1.0.0
 */
public class JDBC {

    public static final String URL = "jdbc:mysql://localhost:3306/kevin";
    public static final String USER = "kevin";
    public static final String PASSWORD = "123456";

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        // 加载驱动
        Class.forName("com.mysal.jdbc.Driver");
        // 获取链接
        Connection connection = DriverManager.getConnection(URL, URL, PASSWORD);
        // 操作数据库查询
        PreparedStatement preparedStatement = connection.prepareStatement("select * from user where id = ?", 1);
        // 获取结果集
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            String id = resultSet.getString("id");
            String name = resultSet.getString("name");
            System.out.printf("id:" + id + ", name:" + name);
        }
    }

    @Data
    public class User {
        private Long id;
        private String name;
    }

}
