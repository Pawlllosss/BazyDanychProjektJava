package redaktor.controller.form.audycja;

import redaktor.DAO.ProgramDAO;
import redaktor.DAO.StudioDAO;
import redaktor.controller.AudycjaTabController;
import redaktor.controller.form.FormWithValidation;
import redaktor.model.Audycja;
import redaktor.model.Studio;
import redaktor.model.program.Program;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class AudycjaForm implements FormWithValidation<Audycja> {

    private AudycjaTabController controller;
    private ProgramDAO programDAO;
    private StudioDAO studioDAO;
    private AudycjaFormValidator audycjaFormValidator;

    public AudycjaForm(AudycjaTabController controller) {
        this.controller = controller;
        this.audycjaFormValidator = new AudycjaFormValidator();
        programDAO = ProgramDAO.getInstance();
        studioDAO = StudioDAO.getInstance();
    }

    @Override
    public Audycja readForm() {
        Optional<Program> program = controller.getProgramFromChoiceBox();
        Long programId = program.map(programL -> programL.getProgramId()).orElse(null);

        Optional<Studio> studio = controller.getStudioFromChoiceBox();
        Long studioId = studio.map(studioL -> studioL.getStudioId()).orElse(null);

        LocalDate localDate = controller.getDataPoczatekDzienFromDatePicker();
        Timestamp dataPoczatekDzien = Timestamp.valueOf(localDate.atStartOfDay());
        String dataPoczatekGodzina = controller.getDataPoczatekGodzinaFromTextField();
        dataPoczatekGodzina += ":00";
        Time time = Time.valueOf(dataPoczatekGodzina);
        Timestamp dataPoczatek = new Timestamp(dataPoczatekDzien.getTime() + time.getTime());

        String czasTrwania = controller.getCzasTrwaniaFromTextField();
        czasTrwania += ":00";

        Time czasTrwaniaTime = Time.valueOf(czasTrwania);

        Audycja audycja = new Audycja(0L, dataPoczatek, czasTrwaniaTime, programId, studioId);

        return audycja;
    }

    @Override
    public void loadValuesIntoForm(Audycja audycja) {
        Long programId = audycja.getProgramId();
        Optional<Program> program = programDAO.get(programId);
        controller.setProgramToChoiceBox(program.orElse(null));

        Long studioId = audycja.getStudioId();
        Optional<Studio> studio = studioDAO.get(studioId);
        controller.setStudioToChoiceBox(studio.orElse(null));

        //TODO: to separate function
        Timestamp audycjaTimestamp = audycja.getDataPoczatek();
        LocalDateTime audycjaLocalDateTime = audycjaTimestamp.toLocalDateTime();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String audycjaPoczatekGodzina = audycjaLocalDateTime.format(formatter);

        controller.setDataPoczatekDzienToDatePicker(audycjaLocalDateTime.toLocalDate());
        controller.setDataPoczatekGodzinaToTextField(audycjaPoczatekGodzina);

        Time czasTrwania = audycja.getCzasTrwania();
        String czasTrwaniaString = czasTrwania.toLocalTime().format(formatter);
        controller.setCzasTrwaniaToTextField(czasTrwaniaString);

    }

    @Override
    public boolean isFormDifferentFromEntity(Audycja audycjaToCheck) {
        return audycjaFormValidator.isFormDifferentFromEntity(getValuesFromForm(), audycjaToCheck);
    }

    @Override
    public boolean isFormCorrectlyFilled() {
        return audycjaFormValidator.isFormCorrectlyFilled(getValuesFromForm());
    }

    protected AudycjaFormValues getValuesFromForm() {
        AudycjaFormValues audycjaFormValues = new AudycjaFormValues();

        audycjaFormValues.program = controller.getProgramFromChoiceBox().orElse(null);
        audycjaFormValues.studio = controller.getStudioFromChoiceBox().orElse(null);
        audycjaFormValues.poczatekDataDzien = controller.getDataPoczatekDzienFromDatePicker();
        audycjaFormValues.poczatekDataGodzina = controller.getDataPoczatekGodzinaFromTextField();
        audycjaFormValues.czasTrwania = controller.getCzasTrwaniaFromTextField();

        return audycjaFormValues;
    }
}
