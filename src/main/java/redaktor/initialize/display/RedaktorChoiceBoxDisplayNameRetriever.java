package redaktor.initialize.display;

import redaktor.model.Redaktor;

import java.util.function.Function;

public class RedaktorChoiceBoxDisplayNameRetriever implements Function<Redaktor, String> {

    @Override
    public String apply(Redaktor redaktor) {
        String redaktorName = redaktor.getImie() + " " + redaktor.getNazwisko();
        return redaktorName;
    }
}
