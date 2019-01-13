package redaktor.controller.form.sekcja;

import redaktor.DAO.RedaktorDAO;
import redaktor.controller.SekcjeTabController;
import redaktor.controller.form.FormWithValidation;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.Optional;

public class SekcjaForm implements FormWithValidation<Sekcja> {

    private SekcjeTabController controller;
    private RedaktorDAO redaktorDAO;
    private SekcjaFormValidator sekcjaFormValidator;

    public SekcjaForm(SekcjeTabController controller) {
        this.controller = controller;
        this.sekcjaFormValidator = new SekcjaFormValidator();
        redaktorDAO = RedaktorDAO.getInstance();
    }

    @Override
    public Sekcja readForm() {

        String nazwa = controller.getNazwaFromTextField();
        Optional<Redaktor> szef = controller.getSzefFromChoiceBox();
        Long szefId = szef.map(szefLambda -> szefLambda.getRedaktorId()).orElse(null);
        Sekcja sekcja = new Sekcja(0L, nazwa, szefId);

        return sekcja;

    }

    @Override
    public void loadValuesIntoForm(Sekcja sekcja) {
        Long szefId = sekcja.getSzefId();
        Optional<Redaktor> szef = redaktorDAO.get(szefId);

        controller.setNazwaToTextField(sekcja.getNazwa());
        controller.setSzefToChoiceBox(szef.orElse(null));
    }

    @Override
    public boolean isFormDifferentFromEntity(Sekcja sekcjaToCheck) {
        return sekcjaFormValidator.isFormDifferentFromEntity(getValuesFromForm(), sekcjaToCheck);
    }

    @Override
    public boolean isFormCorrectlyFilled() {
        return sekcjaFormValidator.isFormCorrectlyFilled(getValuesFromForm());
    }

    protected SekcjaFormValues getValuesFromForm() {
        SekcjaFormValues sekcjaFormValues = new SekcjaFormValues();

        sekcjaFormValues.nazwa = controller.getNazwaFromTextField();
        sekcjaFormValues.szef = controller.getSzefFromChoiceBox().orElse(null);

        return sekcjaFormValues;
    }
}