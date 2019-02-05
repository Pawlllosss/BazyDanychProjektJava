package redaktor.controller.form.piosenka;

import redaktor.controller.PiosenkaTabController;
import redaktor.controller.form.FormWithValidation;
import redaktor.model.Piosenka;
import redaktor.model.Studio;

public class PiosenkaForm implements FormWithValidation<Piosenka> {
    private PiosenkaTabController controller;
    private PiosenkaFormValidator piosenkaFormValidator;

    public PiosenkaForm(PiosenkaTabController controller) {
        this.controller = controller;
        this.piosenkaFormValidator = new PiosenkaFormValidator();
    }

    @Override
    public Piosenka readForm() {
        String tytul = controller.getTytulFromTextField();
        String wykonawca = controller.getWykonawcaFromTextField();
        Piosenka piosenka = new Piosenka(0L, tytul, wykonawca);

        return piosenka;
    }

    @Override
    public void loadValuesIntoForm(Piosenka piosenka) {
        controller.setTytulToTextField(piosenka.getTytul());
        controller.setWykonawcaToTextField(piosenka.getWykonawca());
    }

    @Override
    public boolean isFormDifferentFromEntity(Piosenka piosenkaToCheck) {
        return piosenkaFormValidator.isFormDifferentFromEntity(getValuesFromForm(), piosenkaToCheck);
    }

    @Override
    public boolean isFormCorrectlyFilled() {
        return piosenkaFormValidator.isFormCorrectlyFilled(getValuesFromForm());
    }

    protected PiosenkaFormValues getValuesFromForm() {
        PiosenkaFormValues piosenkaFormValues = new PiosenkaFormValues();

        piosenkaFormValues.tytul = controller.getTytulFromTextField();
        piosenkaFormValues.wykonawca = controller.getWykonawcaFromTextField();

        return piosenkaFormValues;
    }
}