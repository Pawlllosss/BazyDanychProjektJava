package redaktor.model;

public class Redaktor {
    //TODO: I need to use polish variables in project...
    private long redaktorId;
    private String imie;
    private String nazwisko;
    private long sekcjaId;

    public Redaktor(long redaktorId, String imie, String nazwisko, long sekcjaId) {
        this.redaktorId = redaktorId;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.sekcjaId = sekcjaId;
    }

    public long getRedaktorId() {
        return redaktorId;
    }

    public void setRedaktorId(long redaktorId) {
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

    public long getSekcjaId() {
        return sekcjaId;
    }

    public void setSekcjaId(long sekcjaId) {
        this.sekcjaId = sekcjaId;
    }
}
