package redaktor.DAO.update;

import redaktor.model.Sekcja;

public class SekcjaUpdateQueryBuilder extends UpdateQueryBuilder<Sekcja>{
    private final String TABLE_NAME = "redaktor.sekcja";
    private final String ID_FIELD = "sekcja_id";

    @Override
    protected void appendModifiedFieldsToQuery(Sekcja originalSekcja, Sekcja editedSekcja) {
        compareValuesWithEquealsAndAppendToQueryDiffers(originalSekcja.getNazwa(), editedSekcja.getNazwa(), "nazwa");
        compareValuesAndAppendToQueryIfDiffers(originalSekcja.getSzefId(), editedSekcja.getSzefId(), "redaktor_id");
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
