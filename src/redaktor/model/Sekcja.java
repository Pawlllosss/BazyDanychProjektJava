package redaktor.model;

public class Sekcja {
    private long sekcjaId;
    private String nazwa;
    private long szefId;

    public Sekcja(long sekcjaId, String nazwa, long szefId) {
        this.sekcjaId = sekcjaId;
        this.nazwa = nazwa;
        this.szefId = szefId;
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

    public long getSzefId() {
        return szefId;
    }

    public void setSzefId(long szefId) {
        this.szefId = szefId;
    }
}
