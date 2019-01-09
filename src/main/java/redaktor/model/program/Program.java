package redaktor.model.program;

public class Program {

    private long programId;
    private String nazwa;
    private String opis;
    private long sekcjaId;

    public Program(long programId, String nazwa, String opis, long sekcjaId) {
        this.programId = programId;
        this.nazwa = nazwa;
        this.opis = opis;
        this.sekcjaId = sekcjaId;
    }

    public long getProgramId() {
        return programId;
    }

    public void setProgramId(long programId) {
        this.programId = programId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public long getSekcjaId() {
        return sekcjaId;
    }

    public void setSekcjaId(long sekcjaId) {
        this.sekcjaId = sekcjaId;
    }
}
