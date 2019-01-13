package redaktor.controller.form.program;

import redaktor.controller.form.Validator;
import redaktor.model.Sekcja;
import redaktor.model.program.Program;

public class ProgramFormValidator extends Validator {
    public boolean isFormDifferentFromEntity(ProgramFormValues programFormValues, Program programToCheck) {
        boolean wasEdited = false;

        if(checkIfValuesAreNotEqual(programToCheck.getNazwa(), programFormValues.nazwa)) {
            wasEdited = true;
        }
        else if (checkIfValuesAreNotEqual(programToCheck.getOpis(), programFormValues.opis)) {
            wasEdited = true;
        }
        else if (checkIfValuesAreNotEqual(programToCheck.getSekcjaId(), programFormValues.sekcja.getSekcjaId())) {
            wasEdited = true;
        }

        return wasEdited;
    }

    public boolean isFormCorrectlyFilled(ProgramFormValues sekcjaFormValues) {
        boolean isCorrect = true;

        if(!isTextFieldCorrectlyFilled(sekcjaFormValues.nazwa)) {
            isCorrect = false;
        }
        else if(!isTextFieldCorrectlyFilled(sekcjaFormValues.opis)) {
            isCorrect = false;
        }

        return isCorrect;
    }
}
