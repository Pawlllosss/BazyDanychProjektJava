package redaktor.controller.form.piosenka;

import redaktor.controller.form.Validator;
import redaktor.model.Piosenka;

public class PiosenkaFormValidator extends Validator {
    public boolean isFormDifferentFromEntity(PiosenkaFormValues piosenkaFormValues, Piosenka piosenkaToCheck) {
        boolean wasEdited = false;

        if(checkIfValuesAreNotEqual(piosenkaToCheck.getTytul(), piosenkaFormValues.tytul)) {
            wasEdited = true;
        }
        else if (checkIfValuesAreNotEqual(piosenkaToCheck.getWykonawca(), piosenkaFormValues.wykonawca)) {
            wasEdited = true;
        }

        return wasEdited;
    }

    public boolean isFormCorrectlyFilled(PiosenkaFormValues sekcjaFormValues) {
        boolean isCorrect = true;

        if(!isTextFieldCorrectlyFilled(sekcjaFormValues.tytul)) {
            isCorrect = false;
        }
        else if(!isTextFieldCorrectlyFilled(sekcjaFormValues.wykonawca)) {
            isCorrect = false;
        }

        return isCorrect;
    }
}
