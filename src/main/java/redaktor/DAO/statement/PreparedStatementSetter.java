package redaktor.DAO.statement;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

final public class PreparedStatementSetter {
    public static void setForeignKey(PreparedStatement preparedStatement, int parameterIndex, Long foreignKey) throws SQLException {
        if(foreignKey != null) {
            preparedStatement.setLong(parameterIndex, foreignKey);
        } else {
          preparedStatement.setNull(parameterIndex, Types.INTEGER);
        }
    }
}
