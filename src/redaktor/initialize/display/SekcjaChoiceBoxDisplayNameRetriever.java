package redaktor.initialize.display;

import redaktor.model.Sekcja;

public class SekcjaChoiceBoxDisplayNameRetriever implements ChoiceBoxValueDisplayNameRetriever<Sekcja> {
    @Override
    public String getName(Sekcja sekcja) {
        return sekcja.getNazwa();
    }
}
