package redaktor.controller.form.redaktor;

import redaktor.controller.form.Validator;
import redaktor.model.Redaktor;

public class RedaktorFormValidator extends Validator {
    public boolean isFormDiffersFromEntity(RedaktorFormValues redaktorFormValues, Redaktor redaktorToCheck) {
        boolean wasEdited = false;

        if(!redaktorToCheck.getImie().equals(redaktorFormValues.imie)) {
            wasEdited = true;
        }
        else if (!redaktorToCheck.getNazwisko().equals(redaktorFormValues.nazwisko)) {
            wasEdited = true;
        }
        else if (redaktorToCheck.getSekcjaId() != redaktorFormValues.sekcja.getSekcjaId()) {
            wasEdited = true;
        }

        return wasEdited;
    }

    public boolean isFormCorrectlyFilled(RedaktorFormValues redaktorFormValues) {
        boolean isCorrect = true;

        System.out.println(redaktorFormValues.imie);
        System.out.println(redaktorFormValues.nazwisko);

        if(!isImieCorrectlyFilled(redaktorFormValues.imie)) {
            isCorrect = false;
        }
        else if(!isNazwiskoCorrectlyFilled(redaktorFormValues.nazwisko)) {
            isCorrect = false;
        }

        return isCorrect;
    }

    private boolean isImieCorrectlyFilled(String imie) {
        boolean isCorrect = true;

        if(isStringEmptyOrNull(imie)) {
            isCorrect = false;
        } else if(!isNameStringProper(imie)) {
            isCorrect = false;
        }

        return isCorrect;
    }

    private boolean isNazwiskoCorrectlyFilled(String nazwisko) {
        return isImieCorrectlyFilled(nazwisko);
    }
}
