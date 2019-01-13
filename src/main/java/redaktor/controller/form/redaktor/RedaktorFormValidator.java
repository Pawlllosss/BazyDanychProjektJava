package redaktor.controller.form.redaktor;

import redaktor.controller.form.Validator;
import redaktor.model.Redaktor;

public class RedaktorFormValidator extends Validator {
    public boolean isFormDifferentFromEntity(RedaktorFormValues redaktorFormValues, Redaktor redaktorToCheck) {
        boolean wasEdited = false;

        if(checkIfValuesAreNotEqual(redaktorToCheck.getImie(), redaktorFormValues.imie)) {
            wasEdited = true;
        }
        else if (checkIfValuesAreNotEqual(redaktorToCheck.getNazwisko(), redaktorFormValues.nazwisko)) {
            wasEdited = true;
        }
        else if (checkIfValuesAreNotEqual(redaktorToCheck.getSekcjaId(), redaktorFormValues.sekcja.getSekcjaId())) {
            wasEdited = true;
        }

        return wasEdited;
    }

    public boolean isFormCorrectlyFilled(RedaktorFormValues redaktorFormValues) {
        boolean isCorrect = true;

        if(!isTextFieldCorrectlyFilled(redaktorFormValues.imie)) {
            isCorrect = false;
        }
        else if(!isTextFieldCorrectlyFilled(redaktorFormValues.nazwisko)) {
            isCorrect = false;
        }

        return isCorrect;
    }

}
