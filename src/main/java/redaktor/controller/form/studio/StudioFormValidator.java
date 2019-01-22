package redaktor.controller.form.studio;

import redaktor.controller.form.Validator;
import redaktor.model.Studio;

public class StudioFormValidator extends Validator {
    public boolean isFormDifferentFromEntity(StudioFormValues studioFormValues, Studio studioToCheck) {
        boolean wasEdited = false;

        if(checkIfValuesAreNotEqual(studioToCheck.getNazwa(), studioFormValues.nazwa)) {
            wasEdited = true;
        }
        else if (checkIfValuesAreNotEqual(studioToCheck.getNrPokoju(), studioFormValues.nrPokoju)) {
            wasEdited = true;
        }

        return wasEdited;
    }

    public boolean isFormCorrectlyFilled(StudioFormValues sekcjaFormValues) {
        boolean isCorrect = true;

        if(!isTextFieldCorrectlyFilled(sekcjaFormValues.nazwa)) {
            isCorrect = false;
        }
        else if(!isPositiveNumberFieldCorrectlyFilled(sekcjaFormValues.nrPokoju)) {
            isCorrect = false;
        }

        return isCorrect;
    }
}
