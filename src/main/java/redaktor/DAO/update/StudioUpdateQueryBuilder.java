package redaktor.DAO.update;

import redaktor.model.Studio;

public class StudioUpdateQueryBuilder extends UpdateQueryBuilder<Studio>{
    private final String TABLE_NAME = "redaktor.studio";
    private final String ID_FIELD = "studio_id";

    @Override
    protected void appendModifiedFieldsToQuery(Studio originalStudio, Studio editedStudio) {
        compareValuesWithEqualsAndAppendToQueryDiffers(originalStudio.getNazwa(), editedStudio.getNazwa(), "nazwa");
        compareValuesWithEqualsAndAppendToQueryDiffers(originalStudio.getNrPokoju(), editedStudio.getNrPokoju(), "nr_pokoju");
    }

    @Override
    protected void appendWhereIdOfModifiedObject(Studio originalStudio) {
        queryStringBuilder.append("WHERE " + ID_FIELD + " = " + originalStudio.getStudioId() + ";");
    }

    @Override
    public String getTableNameWithSchema() {
        return TABLE_NAME;
    }
}
