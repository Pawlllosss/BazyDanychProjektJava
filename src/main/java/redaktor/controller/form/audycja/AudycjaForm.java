package redaktor.controller.form.audycja;

import redaktor.DAO.SekcjaDAO;
import redaktor.controller.AudycjaTabController;
import redaktor.controller.form.FormWithValidation;
import redaktor.model.Audycja;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;
import redaktor.model.Studio;
import redaktor.model.program.Program;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

public class AudycjaForm implements FormWithValidation<Audycja> {

    private AudycjaTabController controller;
    private SekcjaDAO sekcjaDAO;
    private AudycjaFormValidator audycjaFormValidator;

    public AudycjaForm(AudycjaTabController controller) {
        this.controller = controller;
        this.audycjaFormValidator = new AudycjaFormValidator();
        sekcjaDAO = SekcjaDAO.getInstance();
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
//        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
//
//        try {
//            Date date = dateFormat.parse(dataPoczatekGodzina);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }


        Audycja audycja = new Audycja(0L, dataPoczatek, czasTrwaniaTime, programId, studioId);

        return audycja;
    }

    @Override
    public void loadValuesIntoForm(Audycja audycja) {
//        Long sekcjaId = redaktor.getSekcjaId();
//        Optional<Sekcja> sekcja = sekcjaDAO.get(sekcjaId);
//
//        controller.setImieToTextField(redaktor.getImie());
//
//        controller.setNazwiskoToTextField(redaktor.getNazwisko());
//        controller.setSekcjaToChoiceBox(sekcja.orElse(null));
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
