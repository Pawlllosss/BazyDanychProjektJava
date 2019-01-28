package redaktor.controller.form.redaktor;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import redaktor.controller.RedaktorzyTabController;

import static org.junit.jupiter.api.Assertions.*;

public class RedaktorFormTests {

    RedaktorForm redaktorForm;
    RedaktorzyTabController redaktorzyTabController;

    @BeforeAll
    void init() {
//        MockitoAnnotations.initMocks(this);
    }


    @BeforeEach
    void setup() {
        redaktorzyTabController = new RedaktorzyTabController();
        redaktorForm = new RedaktorForm(redaktorzyTabController);
    }

    @Test
    void shouldLoadCorrectValuesIntoForm() {
    }
}
