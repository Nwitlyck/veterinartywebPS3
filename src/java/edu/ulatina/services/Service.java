package edu.ulatina.services;

import java.sql.*;

public abstract class Service {

    protected Connection conn = null;

    public Service() {
    }

    protected Connection getConnection() throws Exception {

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        conn = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databasename=VetShop;user=progra;password=1234;encrypt=false");

        return conn;
    }

    protected void close(Connection toClose) throws Exception {
        if (toClose != null && !toClose.isClosed()) {
            toClose.close();
            toClose = null;
        }
    }

    protected void close(PreparedStatement toClose) throws Exception {
        if (toClose != null && !toClose.isClosed()) {
            toClose.close();
            toClose = null;
        }
    }

    protected void close(ResultSet toClose) throws Exception {
        if (toClose != null && !toClose.isClosed()) {
            toClose.close();
            toClose = null;
        }
    }
}
