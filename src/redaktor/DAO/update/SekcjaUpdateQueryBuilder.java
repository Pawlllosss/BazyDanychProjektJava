package redaktor.DAO.update;

import redaktor.model.Sekcja;

import java.util.HashMap;
import java.util.Map;

public class SekcjaUpdateQueryBuilder extends UpdateQueryBuilder<Sekcja>{
    private final String TABLE_NAME = "redaktor.sekcja";
    private final String ID_FIELD = "sekcja_id";

    @Override
    protected void appendModifiedFieldsToQuery(Sekcja originalSekcja, Sekcja editedSekcja) {
        Map<String, Object> editedFields = new HashMap<>();

        compareValuesWithEquealsAndAppendToQueryDiffers(originalSekcja.getNazwa(), editedSekcja.getNazwa(), "nazwa");
        compareValuesAndAppendToQueryIfDiffers(originalSekcja.getSzefId(), editedSekcja.getSzefId(), "szef_id");
    }

    @Override
    protected void appendWhereIdOfModifiedObject(Sekcja originalSekcja) {
        queryStringBuilder.append("WHERE " + ID_FIELD + " = " + originalSekcja.getSekcjaId() + ";");
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
