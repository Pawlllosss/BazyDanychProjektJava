package redaktor.controller.form;

import java.text.Normalizer;

public class Validator {
    protected boolean isStringEmptyOrNull(String string) {
        if(string == null || string.trim().isEmpty()) {
            return true;
        }

        return false;
    }

    protected boolean isNameStringProper(String string) {
        String normalizedString = removeDiactricalMarksFromString(string);
        return normalizedString.matches("[a-zA-Z -]+");
    }

    //TODO: could also use apache tools
    private String removeDiactricalMarksFromString(String string) {
        String normalizedString = Normalizer.normalize(string, Normalizer.Form.NFD);
        return normalizedString.replaceAll("[^\\p{ASCII}]", "");
    }
}
