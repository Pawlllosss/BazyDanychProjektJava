package redaktor.connection;

import java.sql.*;

public class ConnectionHandler {
    private static ConnectionHandler ourInstance = new ConnectionHandler();
    private Connection connection;

    public static ConnectionHandler getInstance() {
        return ourInstance;
    }

    public ResultSet executeSelectQuery(String queryString) {
        //TODO: in java 8 I could use Optional...
        ResultSet resultSet = null;

        try {
            Statement statement = connection.createStatement();
            resultSet = statement.executeQuery(queryString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public void executeUpdateQuery(String queryString) {

        try {
            Statement statement = connection.createStatement();
            statement.executeUpdate(queryString);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public PreparedStatement prepareStatement(String queryString) {
        PreparedStatement preparedStatement = null;

        try {
            preparedStatement = connection.prepareStatement(queryString);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return preparedStatement;
    }

    private ConnectionHandler() {
        connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager
//                    .getConnection("jdbc:postgresql://localhost:5432/Redaktor",
                    .getConnection("jdbc:postgresql://localhost:5432/RedaktorMod",
                            "postgres", "123");
//            connection = DriverManager
//                    .getConnection("jdbc:postgresql://pascal.fis.agh.edu.pl:5432/u6oczadly",
//                            "u6oczadly", "6oczadly");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName()+": "+e.getMessage());
            System.exit(0);
        }
        System.out.println("Opened database successfully");
    }
}
