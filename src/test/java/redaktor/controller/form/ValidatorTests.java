package redaktor.controller.form;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ValidatorTests {
    Validator validator;

    @BeforeEach
    void setup() {
        validator = new Validator();
    }

    @Nested
    @DisplayName("Tests for method isTextFieldEmptyOrNull")
    class TestsOfMethodisStringEmptyOrNull {
        @Test
        void shouldReturnTrueIfStringIsNull() {
            boolean result = validator.isTextFieldEmptyOrNull(null);
            assertTrue(result);
        }

        @Test
        void shouldReturnTrueIfStringIsEmpty() {
            boolean result = validator.isTextFieldEmptyOrNull("");
            assertTrue(result);
        }

        @Test
        void shouldReturnTrueIfStringContainsOnlyWhitespaceCharactes() {
            boolean result = validator.isTextFieldEmptyOrNull("   ");
            assertTrue(result);

            result = validator.isTextFieldEmptyOrNull("  \t   ");
            assertTrue(result);

            result = validator.isTextFieldEmptyOrNull("  \r\f   ");
            assertTrue(result);

            result = validator.isTextFieldEmptyOrNull("  \n   ");
            assertTrue(result);
        }

        @Test
        void shouldReturnFalseIfStringIsNotEmptyAndNull() {
            boolean result = validator.isTextFieldEmptyOrNull("MyTest");
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Tests for method isTextFieldContainingAllowedCharacters")
    class TestsOfMethodCheckIfStringContainsOnlyCharactersAndSpaces {
        @Test
        void shouldReturnTrueIfStringContainsOnlyCharactersAndSpaces() {
            boolean result = validator.isTextFieldContainingAllowedCharacters("Sasa Zazaz");
            assertTrue(result);
        }

        @Test
        void shouldReturnTrueIfStringContainsDiacriticMarks() {
            boolean result = validator.isTextFieldContainingAllowedCharacters("Człowiek");
            assertTrue(result);

            result = validator.isTextFieldContainingAllowedCharacters("ęąłżźćó");
            assertTrue(result);
        }

        @Test
        void shouldReturnFalseIfStringContainsSymbols() {
            boolean result = validator.isTextFieldContainingAllowedCharacters("€");
            assertFalse(result);

            result = validator.isTextFieldContainingAllowedCharacters("$€");
            assertFalse(result);
        }

        @Test
        void shouldReturnFalseIfStringContainsDigits() {
            boolean result = validator.isTextFieldContainingAllowedCharacters(" 232 1");
            assertFalse(result);

            result = validator.isTextFieldContainingAllowedCharacters("Andrzej2");
            assertFalse(result);

            result = validator.isTextFieldContainingAllowedCharacters("97Stefan");
            assertFalse(result);
        }
    }
}
