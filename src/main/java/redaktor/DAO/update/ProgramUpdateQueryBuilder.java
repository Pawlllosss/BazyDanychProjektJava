package redaktor.DAO.update;

import redaktor.model.program.Program;

public class ProgramUpdateQueryBuilder extends UpdateQueryBuilder<Program> {
    private final String TABLE_NAME = "redaktor.program";
    private final String ID_FIELD = "program_id";

    @Override
    protected void appendModifiedFieldsToQuery(Program originalProgram, Program editedProgram) {
        compareValuesWithEqualsAndAppendToQueryDiffers(originalProgram.getNazwa(), editedProgram.getNazwa(), "nazwa");
        compareValuesWithEqualsAndAppendToQueryDiffers(originalProgram.getOpis(), editedProgram.getOpis(), "opis");
        compareValuesWithEqualsAndAppendToQueryDiffers(originalProgram.getSekcjaId(), editedProgram.getSekcjaId(), "sekcja_id");
    }

    @Override
    protected void appendWhereIdOfModifiedObject(Program originalProgram) {
        queryStringBuilder.append("WHERE " + ID_FIELD + " = " + originalProgram.getProgramId() + ";");
    }

    @Override
    public String getTableNameWithSchema() {
        return TABLE_NAME;
    }
}
