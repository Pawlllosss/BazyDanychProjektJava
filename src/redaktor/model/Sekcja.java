package redaktor.model;

public class Sekcja {
    private long sekcjaId;
    private String nazwa;

    public Sekcja(long sekcjaId, String nazwa) {
        this.sekcjaId = sekcjaId;
        this.nazwa = nazwa;
    }

    public long getSekcjaId() {
        return sekcjaId;
    }

    public void setSekcjaId(long sekcjaId) {
        this.sekcjaId = sekcjaId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
}
