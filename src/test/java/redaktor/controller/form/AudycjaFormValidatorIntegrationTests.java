package redaktor.controller.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import redaktor.controller.form.redaktor.RedaktorFormValidator;
import redaktor.controller.form.redaktor.RedaktorFormValues;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class AudycjaFormValidatorIntegrationTests {

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
