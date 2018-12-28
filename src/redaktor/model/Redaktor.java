package redaktor.model;

public class Redaktor {
    //TODO: I need to use polish variables in project...
    private long redaktorId;
    private String imie;
    private String nazwisko;
    private String sekcjaNazwa;

    public Redaktor(long redaktorId, String imie, String nazwisko, String sekcjaNazwa) {
        this.redaktorId = redaktorId;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.sekcjaNazwa = sekcjaNazwa;
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

    public String getSekcjaNazwa() {
        return sekcjaNazwa;
    }

    public void setSekcjaNazwa(String sekcjaNazwa) {
        this.sekcjaNazwa = sekcjaNazwa;
    }
}
