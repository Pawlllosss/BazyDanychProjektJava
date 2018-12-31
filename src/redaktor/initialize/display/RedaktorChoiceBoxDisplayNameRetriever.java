package redaktor.initialize.display;

import redaktor.model.Redaktor;

public class RedaktorChoiceBoxDisplayNameRetriever implements ChoiceBoxValueDisplayNameRetriever<Redaktor> {
    @Override
    public String getName(Redaktor redaktor) {
        String redaktorName = redaktor.getImie() + " " + redaktor.getNazwisko();
        return redaktorName;
    }
}
