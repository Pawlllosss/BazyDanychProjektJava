package redaktor.model.program.view;

public class ProgramRedaktorCount {

    private Long programId;
    private String nazwa;
    private Long redaktorCount;

    public ProgramRedaktorCount(Long programId, String nazwa, Long redaktorCount) {
        this.programId = programId;
        this.nazwa = nazwa;
        this.redaktorCount = redaktorCount;
    }

    public Long getProgramId() {
        return programId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public Long getRedaktorCount() {
        return redaktorCount;
    }
}
