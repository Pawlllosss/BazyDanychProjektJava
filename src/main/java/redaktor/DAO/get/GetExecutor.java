package redaktor.DAO.get;

import redaktor.connection.ConnectionHandler;
import redaktor.model.Redaktor;
import redaktor.model.Studio;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class GetExecutor<T> {
    private final String GET_QUERY_FORMAT;
    private final String ID_FIELD_NAME;

    private ConnectionHandler connectionHandler;
    private ResultSetRetriever<T> resultSetRetriever;

    public GetExecutor(String schemaName, String tableName, String idFieldName, ResultSetRetriever<T> resultSetRetriever) {
        this.GET_QUERY_FORMAT = prepareGetQueryFormat(schemaName, tableName);
        this.ID_FIELD_NAME = idFieldName;
        this.connectionHandler = ConnectionHandler.getInstance();
        this.resultSetRetriever = resultSetRetriever;
    }

    public T execute(Long id) {
        String selectByIdQuery = GET_QUERY_FORMAT + String.format("WHERE r.%s = ?;", ID_FIELD_NAME);

        List<T> entities = new LinkedList<>();
        T entity = null;

        PreparedStatement selectByIdStatement = connectionHandler.prepareStatement(selectByIdQuery);
        try {
            selectByIdStatement.setLong(1, id);
            ResultSet resultSet = selectByIdStatement.executeQuery();
            entities = resultSetRetriever.retrieve(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!entities.isEmpty()) {
            entity = entities.get(0);
        }

        return entity;
    }

    public List<T> executeAll() {
        ResultSet resultSet = connectionHandler.executeSelectQuery(GET_QUERY_FORMAT);
        List<T> entities = null;

        try {
            entities = resultSetRetriever.retrieve(resultSet);
        }
        catch(SQLException exception) {
            exception.printStackTrace();
        }

        return entities;
    }

    private String prepareGetQueryFormat(String schemaName, String tableName) {
        return String.format("SELECT * FROM %s.%s r ", schemaName, tableName);
    }
}
