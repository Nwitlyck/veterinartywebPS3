package edu.ulatina.services;

import java.sql.*;

/**
 *
 * @author Nwitlyck
 */
public abstract class Service {

    protected Connection conn = null;

    public Service() {
    }

    protected Connection getConnection() throws Exception {

        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");//jdbc:sqlserver://localhost:3306;databasename=VetShop;user=MyUserName;password=*****;

        conn = DriverManager.getConnection("jdbc:sqlserver://localhost:3306;databasename=VetShop;integratedsecurity=true");

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
