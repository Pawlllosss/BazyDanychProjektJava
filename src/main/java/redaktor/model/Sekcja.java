package redaktor.model;

public class Sekcja {
    private Long sekcjaId;
    private String nazwa;
    private Long szefId;

    public Sekcja(Long sekcjaId, String nazwa, Long szefId) {
        this.sekcjaId = sekcjaId;
        this.nazwa = nazwa;
        this.szefId = szefId;
    }

    public Long getSekcjaId() {
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

    public Long getSzefId() {
        return szefId;
    }

    public void setSzefId(long szefId) {
        this.szefId = szefId;
    }
}
