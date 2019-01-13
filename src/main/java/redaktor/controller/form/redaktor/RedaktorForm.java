package redaktor.controller.form.redaktor;

import redaktor.DAO.SekcjaDAO;
import redaktor.controller.RedaktorzyTabController;
import redaktor.controller.form.FormWithValidation;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.Optional;

public class RedaktorForm implements FormWithValidation<Redaktor> {

    private RedaktorzyTabController controller;
    private SekcjaDAO sekcjaDAO;
    private RedaktorFormValidator redaktorFormValidator;

    public RedaktorForm(RedaktorzyTabController controller) {
        this.controller = controller;
        this.redaktorFormValidator = new RedaktorFormValidator();
        sekcjaDAO = SekcjaDAO.getInstance();
    }

    @Override
    public Redaktor readForm() {
        String imie = controller.getImieFromTextField();
        String nazwisko = controller.getNazwiskoFromTextField();
        Optional<Sekcja> sekcja = controller.getSekcjaFromChoiceBox();
        Long sekcjaId = sekcja.map(sekcjaL -> sekcjaL.getSekcjaId()).orElse(null);

        Redaktor redaktor = new Redaktor(0, imie, nazwisko, sekcjaId);

        return redaktor;
    }

    @Override
    public void loadValuesIntoForm(Redaktor redaktor) {
        Long sekcjaId = redaktor.getSekcjaId();
        Optional<Sekcja> sekcja = sekcjaDAO.get(sekcjaId);

        controller.setImieToTextField(redaktor.getImie());

        controller.setNazwiskoToTextField(redaktor.getNazwisko());
        controller.setSekcjaToChoiceBox(sekcja.orElse(null));
    }

    @Override
    public boolean isFormDifferentFromEntity(Redaktor redaktorToCheck) {
        return redaktorFormValidator.isFormDifferentFromEntity(getValuesFromForm(), redaktorToCheck);
    }

    @Override
    public boolean isFormCorrectlyFilled() {
        return redaktorFormValidator.isFormCorrectlyFilled(getValuesFromForm());
    }

    protected RedaktorFormValues getValuesFromForm() {
        RedaktorFormValues redaktorFormValues = new RedaktorFormValues();

        redaktorFormValues.imie = controller.getImieFromTextField();
        redaktorFormValues.nazwisko = controller.getNazwiskoFromTextField();
        redaktorFormValues.sekcja = controller.getSekcjaFromChoiceBox().orElse(null);

        return redaktorFormValues;
    }
}
