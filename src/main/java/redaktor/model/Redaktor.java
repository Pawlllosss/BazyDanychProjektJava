package redaktor.model;

public class Redaktor {
    private Long redaktorId;
    private String imie;
    private String nazwisko;
    private Long sekcjaId;

    public Redaktor(Long redaktorId, String imie, String nazwisko, Long sekcjaId) {
        this.redaktorId = redaktorId;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.sekcjaId = sekcjaId;
    }

    public Long getRedaktorId() {
        return redaktorId;
    }

    public void setRedaktorId(Long redaktorId) {
        this.redaktorId = redaktorId;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public Long getSekcjaId() {
        return sekcjaId;
    }

    public void setSekcjaId(Long sekcjaId) {
        this.sekcjaId = sekcjaId;
    }
}
