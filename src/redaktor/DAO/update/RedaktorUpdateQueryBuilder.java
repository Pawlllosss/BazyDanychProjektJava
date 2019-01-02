package redaktor.DAO.update;

import redaktor.model.Redaktor;

import java.util.HashMap;
import java.util.Map;

public class RedaktorUpdateQueryBuilder extends UpdateQueryBuilder<Redaktor>{
    private final String TABLE_NAME = "redaktor.redaktor";
    private final String ID_FIELD = "redaktor_id";

    @Override
    protected void appendModifiedFieldsToQuery(Redaktor originalRedaktor, Redaktor editedRedaktor) {
        Map<String, Object> editedFields = new HashMap<>();

        compareValuesWithEquealsAndAppendToQueryDiffers(originalRedaktor.getImie(), editedRedaktor.getImie(), "imie");
        compareValuesWithEquealsAndAppendToQueryDiffers(originalRedaktor.getNazwisko(), editedRedaktor.getNazwisko(), "nazwisko");
        compareValuesAndAppendToQueryIfDiffers(originalRedaktor.getSekcjaId(), editedRedaktor.getSekcjaId(), "sekcja_id");
    }

    @Override
    protected void appendWhereIdOfModifiedObject(Redaktor originalRedaktor) {
        queryStringBuilder.append("WHERE redaktor_id = " + originalRedaktor.getRedaktorId() + ";");
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
