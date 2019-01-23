package redaktor.controller.form.audycja;

import redaktor.controller.form.Validator;
import redaktor.model.Audycja;
import redaktor.model.Redaktor;

//TODO: date and time validation!
public class AudycjaFormValidator extends Validator {
    public boolean isFormDifferentFromEntity(AudycjaFormValues audycjaFormValues, Audycja audycjaToCheck) {
        boolean wasEdited = false;

//        if(checkIfValuesAreNotEqual(audycjaToCheck.getImie(), audycjaFormValues.imie)) {
//            wasEdited = true;
//        }
//        else if (checkIfValuesAreNotEqual(audycjaToCheck.getNazwisko(), audycjaFormValues.nazwisko)) {
//            wasEdited = true;
//        }
//        else if (checkIfValuesAreNotEqual(audycjaToCheck.getSekcjaId(), audycjaFormValues.sekcja.getSekcjaId())) {
//            wasEdited = true;
//        }

        return wasEdited;
    }

    public boolean isFormCorrectlyFilled(AudycjaFormValues redaktorFormValues) {
        boolean isCorrect = true;

//        if(!isTextFieldCorrectlyFilled(redaktorFormValues.imie)) {
//            isCorrect = false;
//        }
//        else if(!isTextFieldCorrectlyFilled(redaktorFormValues.nazwisko)) {
//            isCorrect = false;
//        }

        return isCorrect;
    }

}
