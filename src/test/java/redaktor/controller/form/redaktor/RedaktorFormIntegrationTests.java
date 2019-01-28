package redaktor.controller.form.redaktor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import redaktor.DAO.SekcjaDAO;
import redaktor.controller.RedaktorzyTabController;
import redaktor.model.Redaktor;
import redaktor.model.Sekcja;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.mockito.internal.verification.VerificationModeFactory.times;


public class RedaktorFormIntegrationTests {

    RedaktorForm redaktorForm;
    @Mock
    RedaktorzyTabController redaktorzyTabController;
    @Mock
    SekcjaDAO sekcjaDAO;

    @BeforeEach
    void setup() {
        initMocks(this);
    }

    @Test
    void shouldLoadCorrectValuesIntoForm() {
        Long redaktorId = 1L;
        String imie = "Faplo";
        String nazwisko = "Faplowicz";
        Long sekcjaId = 1L;

        Redaktor redaktor = new Redaktor(redaktorId, imie, nazwisko, sekcjaId);
        Sekcja sekcja = new Sekcja(sekcjaId, "nowa", null);

        when(sekcjaDAO.get(sekcjaId)).thenReturn(Optional.of(sekcja));
        redaktorForm = new RedaktorForm(redaktorzyTabController, sekcjaDAO);

        redaktorForm.loadValuesIntoForm(redaktor);

        Mockito.verify(redaktorzyTabController, times(1)).setImieToTextField(imie);
        Mockito.verify(redaktorzyTabController, times(1)).setNazwiskoToTextField(nazwisko);
        Mockito.verify(redaktorzyTabController, times(1)).setSekcjaToChoiceBox(sekcja);
    }
}
