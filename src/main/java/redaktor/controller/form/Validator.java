package redaktor.controller.form;

import java.text.Normalizer;

public class Validator {

    protected boolean isTextFieldCorrectlyFilled(String string) {
        boolean isCorrect = true;

        if(isTextFieldEmptyOrNull(string)) {
            isCorrect = false;
        } else if(!isTextFieldContainingAllowedCharacters(string)) {
            isCorrect = false;
        }

        return isCorrect;
    }

    protected boolean isPositiveNumberFieldCorrectlyFilled(Integer number) {
        boolean isCorrect = true;

        if(isNumericalFieldEmpty(number)) {
            isCorrect = false;
        } else if(!isNumberPositive(number)) {
            isCorrect = false;
        }

        return isCorrect;
    }

    protected boolean isTextFieldEmptyOrNull(String string) {
        if(string == null || string.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    protected boolean isTextFieldContainingAllowedCharacters(String string) {
        String normalizedString = removeDiactricalMarksFromString(string);
        return normalizedString.matches("[a-zA-Z -]+");
    }

    protected boolean isNumericalFieldEmpty(Integer number) {
        if(number == null) {
            return true;
        }

        return false;
    }

    protected boolean isNumberPositive(Integer number) {
        return number > 0;
    }

    protected <T> boolean checkIfValuesAreEqual(T val1, T val2) {
        return val1.equals(val2);
    }

    protected <T> boolean checkIfValuesAreNotEqual(T val1, T val2) {
        return !checkIfValuesAreEqual(val1, val2);
    }

    private String removeDiactricalMarksFromString(String string) {
        String normalizedString = Normalizer.normalize(string, Normalizer.Form.NFD);
        return normalizedString.replaceAll("[^\\p{ASCII}]", "");
    }
}
