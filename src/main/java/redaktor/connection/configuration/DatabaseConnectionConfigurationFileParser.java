package redaktor.connection.configuration;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class DatabaseConnectionConfigurationFileParser {
    private String configurationFileName;

    public DatabaseConnectionConfigurationFileParser(String configurationFileName) {
        this.configurationFileName = configurationFileName;
    }

    public DatabaseConnectionDetails parse() {
        DatabaseConnectionDetails databaseConnectionDetails = null;

        try(FileReader fileReader = new FileReader(configurationFileName)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(fileReader);
            DatabaseConfigurationFileContent confFileContent = parseDatabaseConnectionDetailsFromJsonObject(jsonObject);
            databaseConnectionDetails = createDatabaseConnectionDetails(confFileContent);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return databaseConnectionDetails;
    }

    private DatabaseConnectionDetails createDatabaseConnectionDetails(DatabaseConfigurationFileContent confFileContent) {
        DatabaseConnectionDetails databaseConnectionDetails = new DatabaseConnectionDetails();

        String connectionUrl = buildConnectionUrl(confFileContent);
        databaseConnectionDetails.connectionUrl = connectionUrl;
        databaseConnectionDetails.username = confFileContent.username;
        databaseConnectionDetails.password = confFileContent.password;

        return databaseConnectionDetails;
    }

    private String buildConnectionUrl(DatabaseConfigurationFileContent confFileContent) {
        final String protocolPart = "jdbc:postgresql://";

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(protocolPart);
        stringBuilder.append(confFileContent.hostname);
        stringBuilder.append(":");
        stringBuilder.append(confFileContent.port);
        stringBuilder.append("/");
        stringBuilder.append(confFileContent.database);

        return stringBuilder.toString();
    }

    private DatabaseConfigurationFileContent parseDatabaseConnectionDetailsFromJsonObject(JSONObject jsonObject) {
        DatabaseConfigurationFileContent databaseConfigurationFileContent = new DatabaseConfigurationFileContent();

        databaseConfigurationFileContent.hostname = (String)jsonObject.get("hostname");
        databaseConfigurationFileContent.port = (long)jsonObject.get("port");
        databaseConfigurationFileContent.database = (String)jsonObject.get("database");
        databaseConfigurationFileContent.username = (String)jsonObject.get("username");
        databaseConfigurationFileContent.password = (String)jsonObject.get("password");

        return databaseConfigurationFileContent;
    }


}
