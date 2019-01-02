package redaktor.DAO.update;


public abstract class UpdateQueryBuilder<T> {
    protected StringBuilder queryStringBuilder;
    private boolean isFirstField;

    public String buildUpdateQuery(T originalValueObject, T editedValueObject) {
        final String UPDATE_QUERY_BEGIN = "UPDATE " +  getTableName() + " SET ";

        isFirstField = true;

        queryStringBuilder = new StringBuilder(UPDATE_QUERY_BEGIN);
        appendModifiedFieldsToQuery(originalValueObject, editedValueObject);
        appendWhereIdOfModifiedObject(originalValueObject);

        return queryStringBuilder.toString();
    }

    protected abstract void appendModifiedFieldsToQuery(T originalValueObject, T editedValueObject);
    protected abstract void appendWhereIdOfModifiedObject(T originalValueObject);
    public abstract String getTableName();

    private  <T> String createFieldUpdateQueryPart(String fieldName, T fieldValue) {
        final String UPDATE_PART = fieldName + " = '" + fieldValue + "' ";
        String updateQueryPart;

        if(isFirstField) {
            updateQueryPart = UPDATE_PART;
            isFirstField = false;
        }
        else {
            updateQueryPart = "," + UPDATE_PART;
        }

        return updateQueryPart;
    }

    protected <T extends Number> String createFieldUpdateQueryPart(String fieldName, T fieldValue) {
        //what about int and Integer?
        final String UPDATE_PART = fieldName + " = " + fieldValue + " ";
        String updateQueryPart;

        if(isFirstField) {
            updateQueryPart = UPDATE_PART;
            isFirstField = false;
        }
        else {
            updateQueryPart = "," + UPDATE_PART;
        }

        return updateQueryPart;
    }

    protected <T> void compareValuesWithEquealsAndAppendToQueryDiffers(T valueOld, T valueNew, String fieldName) {
        if(!valueOld.equals(valueNew)) {
            String updateQueryPart = createFieldUpdateQueryPart(fieldName, valueNew);
            queryStringBuilder.append(updateQueryPart);
        }
    }

    protected <T extends Number> void compareValuesAndAppendToQueryIfDiffers(T valueOld, T valueNew, String fieldName) {
        if(valueOld != valueNew) {
            String updateQueryPart = createFieldUpdateQueryPart(fieldName, valueNew);
            queryStringBuilder.append(updateQueryPart);
        }
    }
}
