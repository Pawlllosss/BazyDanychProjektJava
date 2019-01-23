package redaktor.controller.form;

import org.junit.jupiter.api.*;
import redaktor.controller.form.redaktor.RedaktorFormValidator;
import redaktor.controller.form.redaktor.RedaktorFormValues;

import static org.junit.jupiter.api.Assertions.*;

public class AudycjaFormValidatorItTests {

    RedaktorFormValidator redaktorFormValidator;

    @BeforeEach
    void setup() {
        redaktorFormValidator = new RedaktorFormValidator();
    }

    @Nested
    @DisplayName("Tests for method isFormCorrectlyFilled")
    class TestsOfMethodIsFormCorrectlyFilled {
        @Test
        void shouldReturnTrueIfFormIsCorrectlyFilled() {
            RedaktorFormValues redaktorFormValues = new RedaktorFormValues();
            redaktorFormValues.imie = "Człowiek";
            redaktorFormValues.nazwisko = "Sztos";
            redaktorFormValues.sekcja = null;

            boolean result = redaktorFormValidator.isFormCorrectlyFilled(redaktorFormValues);
            assertTrue(result);
        }
    }

}
