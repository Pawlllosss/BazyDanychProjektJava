package redaktor.DAO.update;

import redaktor.model.Piosenka;

public class PiosenkaUpdateQueryBuilder extends UpdateQueryBuilder<Piosenka>{
    private final String TABLE_NAME;
    private final String SCHEMA_NAME;
    private final String ID_FIELD;

    public PiosenkaUpdateQueryBuilder(String schemaName, String tableName, String idField) {
        TABLE_NAME = tableName;
        SCHEMA_NAME = schemaName;
        ID_FIELD = idField;
    }


    @Override
    protected void appendModifiedFieldsToQuery(Piosenka originalPiosenka, Piosenka editedPiosenka) {
        compareValuesWithEqualsAndAppendToQueryDiffers(originalPiosenka.getTytul(), editedPiosenka.getTytul(), "tytul");
        compareValuesWithEqualsAndAppendToQueryDiffers(originalPiosenka.getWykonawca(), editedPiosenka.getWykonawca(), "wykonawca");
    }

    @Override
    protected void appendWhereIdOfModifiedObject(Piosenka originalPiosenka) {
        queryStringBuilder.append("WHERE " + ID_FIELD + " = " + originalPiosenka.getPiosenkaId() + ";");
    }

    @Override
    public String getTableNameWithSchema() {
        return String.format("%s.%s", SCHEMA_NAME, TABLE_NAME);
    }
}
