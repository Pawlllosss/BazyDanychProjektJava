package redaktor.model.program.view;

public class ProgramPrzypisanyRedaktor {
    private Long programId;
    private Long redaktorId;
    private String programNazwa;
    private String imieNazwisko;

    public ProgramPrzypisanyRedaktor(Long programId, Long redaktorId, String programNazwa, String imieNazwisko) {
        this.programId = programId;
        this.redaktorId = redaktorId;
        this.programNazwa = programNazwa;
        this.imieNazwisko = imieNazwisko;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getRedaktorId() {
        return redaktorId;
    }

    public void setRedaktorId(Long redaktorId) {
        this.redaktorId = redaktorId;
    }

    public String getProgramNazwa() {
        return programNazwa;
    }

    public void setProgramNazwa(String programNazwa) {
        this.programNazwa = programNazwa;
    }

    public String getImieNazwisko() {
        return imieNazwisko;
    }

    public void setImieNazwisko(String imieNazwisko) {
        this.imieNazwisko = imieNazwisko;
    }
}
