package redaktor.model.program.view;

public class ProgramRedaktorCount {
    private Long programId;
    private String programNazwa;
    private Long redaktorCount;

    public ProgramRedaktorCount(Long programId, String programNazwa, Long redaktorCount) {
        this.programId = programId;
        this.programNazwa = programNazwa;
        this.redaktorCount = redaktorCount;
    }

    public Long getProgramId() {
        return programId;
    }

    public String getProgramNazwa() {
        return programNazwa;
    }

    public Long getRedaktorCount() {
        return redaktorCount;
    }
}
