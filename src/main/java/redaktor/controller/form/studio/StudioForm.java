package redaktor.controller.form.studio;

import redaktor.DAO.StudioDAO;
import redaktor.controller.StudioTabController;
import redaktor.controller.form.FormWithValidation;
import redaktor.model.Studio;

public class StudioForm implements FormWithValidation<Studio> {

    private StudioTabController controller;
    private StudioDAO studioDAO;
    private StudioFormValidator studioFormValidator;

    public StudioForm(StudioTabController controller) {
        this.controller = controller;
        this.studioFormValidator = new StudioFormValidator();
        studioDAO = StudioDAO.getInstance();
    }

    @Override
    public Studio readForm() {
        String nazwa = controller.getNazwaFromTextField();
        Integer nrPokoju = Integer.valueOf(controller.getNrPokojuFromTextField());
        Studio studio = new Studio(0L, nazwa, nrPokoju);

        return studio;
    }

    @Override
    public void loadValuesIntoForm(Studio studio) {
        controller.setNazwaToTextField(studio.getNazwa());
        controller.setNrPokojuTextField(studio.getNrPokoju().toString());
    }

    @Override
    public boolean isFormDifferentFromEntity(Studio studioToCheck) {
        return studioFormValidator.isFormDifferentFromEntity(getValuesFromForm(), studioToCheck);
    }

    @Override
    public boolean isFormCorrectlyFilled() {
        return studioFormValidator.isFormCorrectlyFilled(getValuesFromForm());
    }

    protected StudioFormValues getValuesFromForm() {
        StudioFormValues sekcjaFormValues = new StudioFormValues();

        sekcjaFormValues.nazwa = controller.getNazwaFromTextField();
        sekcjaFormValues.nrPokoju = Integer.valueOf(controller.getNrPokojuFromTextField());

        return sekcjaFormValues;
    }
}