package redaktor.initialize.display;

import redaktor.model.Program;

public class ProgramChoiceBoxDisplayNameRetriever implements ChoiceBoxValueDisplayNameRetriever<Program> {
    @Override
    public String getName(Program program) {
        return program.getNazwa();
    }
}
