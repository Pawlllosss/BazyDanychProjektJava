package redaktor.controller.form.program;

import redaktor.DAO.SekcjaDAO;
import redaktor.controller.ProgramTabController;
import redaktor.controller.form.FormWithValidation;
import redaktor.model.Sekcja;
import redaktor.model.program.Program;

import java.util.Optional;

public class ProgramForm implements FormWithValidation<Program> {

    private ProgramTabController controller;
    private SekcjaDAO sekcjaDao;
    private ProgramFormValidator programFormValidator;

    public ProgramForm(ProgramTabController controller) {
        this.controller = controller;
        this.programFormValidator = new ProgramFormValidator();
        sekcjaDao = SekcjaDAO.getInstance();
    }

    @Override
    public Program readForm() {
        String nazwa = controller.getNazwaFromTextField();
        String opis = controller.getOpisFromTextField();
        Optional<Sekcja> sekcja = controller.getSekcjaFromChoiceBox();
        Long sekcjaId = sekcja.map(sekcjaLambda -> sekcjaLambda.getSekcjaId()).orElse(null);

        Program program = new Program(0, nazwa, opis, sekcjaId);
        return program;
    }

    @Override
    public void loadValuesIntoForm(Program program) {
        Long sekcjaId = program.getSekcjaId();
        Optional<Sekcja> sekcja = sekcjaDao.get(sekcjaId);

        controller.setNawaToTextField(program.getNazwa());
        controller.setOpisToTextField(program.getOpis());
        controller.setSekcjaToChoiceBox(sekcja.orElse(null));
    }

    @Override
    public boolean isFormDifferentFromEntity(Program programToCheck) {
        return programFormValidator.isFormDifferentFromEntity(getValuesFromForm(), programToCheck);
    }

    @Override
    public boolean isFormCorrectlyFilled() {
        return programFormValidator.isFormCorrectlyFilled(getValuesFromForm());
    }

    protected ProgramFormValues getValuesFromForm() {
        ProgramFormValues programFormValues = new ProgramFormValues();

        programFormValues.nazwa = controller.getNazwaFromTextField();
        programFormValues.opis = controller.getOpisFromTextField();
        programFormValues.sekcja = controller.getSekcjaFromChoiceBox().orElse(null);

        return programFormValues;
    }
}