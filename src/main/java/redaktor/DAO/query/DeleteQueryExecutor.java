package redaktor.DAO.query;

import redaktor.connection.ConnectionHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DeleteQueryExecutor {
    private String deleteQuery;
    private ConnectionHandler connectionHandler;
    private long id;


    public DeleteQueryExecutor(String deleteQuery, long id) {
        this.deleteQuery = deleteQuery;
        this.id = id;
        this.connectionHandler = ConnectionHandler.getInstance();
    }

    public void executeDeleteQuery() {
        PreparedStatement preparedStatement = connectionHandler.prepareStatement(deleteQuery);

        try {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
