package redaktor.DAO.update;

import redaktor.model.Audycja;

public class AudycjaUpdateQueryBuilder extends UpdateQueryBuilder<Audycja>{
    private final String TABLE_NAME = "redaktor.audycja";
    private final String ID_FIELD = "audycja_id";

    @Override
    protected void appendModifiedFieldsToQuery(Audycja originalAudycja, Audycja editedAudycja) {
        compareValuesWithEqualsAndAppendToQueryDiffers(originalAudycja.getDataPoczatek(), editedAudycja.getDataPoczatek(), "data_poczatek");
        compareValuesWithEqualsAndAppendToQueryDiffers(originalAudycja.getDataKoniec(), editedAudycja.getDataKoniec(), "data_koniec");
        compareValuesWithEqualsAndAppendToQueryDiffers(originalAudycja.getProgramId(), editedAudycja.getProgramId(), "program_id");
        compareValuesWithEqualsAndAppendToQueryDiffers(originalAudycja.getStudioId(), editedAudycja.getStudioId(), "studio_id");
    }

    @Override
    protected void appendWhereIdOfModifiedObject(Audycja originalAudycja) {
        queryStringBuilder.append("WHERE " + ID_FIELD + " = " + originalAudycja.getAudycjaId() + ";");
    }

    @Override
    public String getTableName() {
        return TABLE_NAME;
    }
}
