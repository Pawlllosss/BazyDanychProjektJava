package redaktor.controller.form.sekcja;

import redaktor.controller.form.Validator;
import redaktor.model.Sekcja;

public class SekcjaFormValidator extends Validator {
    public boolean isFormDifferentFromEntity(SekcjaFormValues sekcjaFormValues, Sekcja sekcjaToCheck) {
        boolean wasEdited = false;

        if(checkIfValuesAreNotEqual(sekcjaToCheck.getNazwa(), sekcjaFormValues.nazwa)) {
            wasEdited = true;
        }
        else if (checkIfValuesAreNotEqual(sekcjaToCheck.getSzefId(), sekcjaFormValues.szef.getRedaktorId())) {
            wasEdited = true;
        }

        return wasEdited;
    }

    public boolean isFormCorrectlyFilled(SekcjaFormValues sekcjaFormValues) {
        boolean isCorrect = true;

        if(!isTextFieldCorrectlyFilled(sekcjaFormValues.nazwa)) {
            isCorrect = false;
        }

        return isCorrect;
    }
}
