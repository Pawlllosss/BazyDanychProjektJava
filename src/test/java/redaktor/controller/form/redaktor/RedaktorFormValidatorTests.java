package redaktor.controller.form.redaktor;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class RedaktorFormValidatorTests {

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
