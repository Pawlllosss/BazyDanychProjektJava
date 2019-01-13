package redaktor.controller.form;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class ValidatorTests {
    Validator validator;

    @BeforeEach
    void setup() {
        validator = new Validator();
    }

    @Nested
    @DisplayName("Tests for method isStringEmptyOrNull")
    class TestsOfMethodisStringEmptyOrNull {
        @Test
        void shouldReturnTrueIfStringIsNull() {
            boolean result = validator.isStringEmptyOrNull(null);
            assertTrue(result);
        }

        @Test
        void shouldReturnTrueIfStringIsEmpty() {
            boolean result = validator.isStringEmptyOrNull("");
            assertTrue(result);
        }

        @Test
        void shouldReturnTrueIfStringContainsOnlyWhitespaceCharactes() {
            boolean result = validator.isStringEmptyOrNull("   ");
            assertTrue(result);

            result = validator.isStringEmptyOrNull("  \t   ");
            assertTrue(result);

            result = validator.isStringEmptyOrNull("  \r\f   ");
            assertTrue(result);

            result = validator.isStringEmptyOrNull("  \n   ");
            assertTrue(result);
        }

        @Test
        void shouldReturnFalseIfStringIsNotEmptyAndNull() {
            boolean result = validator.isStringEmptyOrNull("MyTest");
            assertFalse(result);
        }
    }

    @Nested
    @DisplayName("Tests for method isNameStringProper")
    class TestsOfMethodCheckIfStringContainsOnlyCharactersAndSpaces {
        @Test
        void shouldReturnTrueIfStringContainsOnlyCharactersAndSpaces() {
            boolean result = validator.isNameStringProper("Sasa Zazaz");
            assertTrue(result);
        }

        @Test
        void shouldReturnTrueIfStringContainsDiacriticMarks() {
            boolean result = validator.isNameStringProper("Człowiek");
            assertTrue(result);

            result = validator.isNameStringProper("ęąłżźćó");
            assertTrue(result);
        }

        @Test
        void shouldReturnFalseIfStringContainsSymbols() {
            boolean result = validator.isNameStringProper("€");
            assertFalse(result);

            result = validator.isNameStringProper("$€");
            assertFalse(result);
        }

        @Test
        void shouldReturnFalseIfStringContainsDigits() {
            boolean result = validator.isNameStringProper(" 232 1");
            assertFalse(result);

            result = validator.isNameStringProper("Andrzej2");
            assertFalse(result);

            result = validator.isNameStringProper("97Stefan");
            assertFalse(result);
        }
    }
}
