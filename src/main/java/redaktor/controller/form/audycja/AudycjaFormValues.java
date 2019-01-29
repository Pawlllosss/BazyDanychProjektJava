package redaktor.controller.form.audycja;

import redaktor.model.Studio;
import redaktor.model.program.Program;

import java.time.LocalDate;

public class AudycjaFormValues {
    public Program program;
    public Studio studio;
    public LocalDate poczatekDataDzien;
    public String poczatekDataGodzina;
    public LocalDate koniecDataDzien;
    public String koniecDataGodzina;
}