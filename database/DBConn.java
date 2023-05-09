package database;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {
    private static String DRIVER_NAME = "com.mysql.cj.jdbc.Driver";
    private static String IP = "localhost:3306";
    private static String DATABASE_NAME = "KNK";
    private static String USER_NAME = "root";
    private static String PASSWORD = "";
    private Connection connection;

    public static DBConn getConnection() {
        return new DBConn();
    }

    private DBConn() {
        this.connection = this.initConnection();
    }

    private Connection initConnection() {
        try {
            // jdbc:mysql://{IP}/{Database_Name}
            Class.forName(DBConn.DRIVER_NAME);
            return DriverManager.getConnection(
                    "jdbc:mysql://" + DBConn.IP + "/"
                            + DBConn.DATABASE_NAME,
                    DBConn.USER_NAME,
                    DBConn.PASSWORD);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }


    public void ExecuteOraret(String query) throws SQLException {

        PreparedStatement pst = this.connection.prepareStatement(query);
        pst.execute();
        pst.close();

    }

    //INSERT INTO STUDENTS VALUES (1, "STUDENT", "STUDENT", ...)
    public ResultSet executeQuery(String query) {
        try {
            PreparedStatement st = this.connection.prepareStatement(query);
            return st.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    //INSERT INTO STUDENTS VALUES (?, ?, ?)
    public int execute(QueryBuilder queryBuilder) {
        try {
            PreparedStatement pst =
                    this.connection.prepareStatement(queryBuilder.getQuery(), Statement.RETURN_GENERATED_KEYS);

            System.out.println(queryBuilder.getQuery());
            System.out.println(queryBuilder.getTypes());

            Object[] values = queryBuilder.getValues();
            char[] types = queryBuilder.getTypes().toCharArray();

            for (int i = 0; i < values.length; i++) {
                //vendosja e vlerave per parametrat perkates fillon nga indeksi 1
                switch (types[i]) {
                    case 'i':
                        pst.setInt(i + 1, (int) values[i]);
                        break;
                    case 's':
                        pst.setString(i + 1, (String) values[i]);
                        break;
                    case 'f':
                        pst.setFloat(i + 1, (float) values[i]);
                        break;
                    default:
                        throw new Exception("Type not supported!");
                }
            }

            pst.execute();
            int lastInsertedId = 0;
            ResultSet rs = pst.getGeneratedKeys();
            if (rs.next()) {
                //Id should be the first column, right ?
                lastInsertedId = rs.getInt(1);
            }
            rs.close();
            pst.close();
            return lastInsertedId;

        } catch (Exception e) {
            System.err.println(e.getMessage());
            return 0;
        }
    }

    public void executeU(String query) throws SQLException {
        PreparedStatement pst = this.connection.prepareStatement(query);
        pst.execute();
        pst.close();
    }

}
