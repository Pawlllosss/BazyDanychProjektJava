package redaktor.controller.form.audycja;

import redaktor.controller.form.Validator;
import redaktor.model.Audycja;

//TODO: date and time validation!
public class AudycjaFormValidator extends Validator {
    public boolean isFormDifferentFromEntity(AudycjaFormValues audycjaFormValues, Audycja audycjaToCheck) {
        boolean wasEdited = false;

        wasEdited = true;


        return wasEdited;
    }

    public boolean isFormCorrectlyFilled(AudycjaFormValues redaktorFormValues) {
        boolean isCorrect = true;


        return isCorrect;
    }

}
