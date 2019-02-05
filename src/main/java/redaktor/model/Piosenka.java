package redaktor.model;

public class Piosenka {
    private Long piosenkaId;
    private String tytul;
    private String wykonawca;

    public Piosenka(Long piosenkaId, String tytul, String wykonawca) {
        this.piosenkaId = piosenkaId;
        this.tytul = tytul;
        this.wykonawca = wykonawca;
    }

    public Long getPiosenkaId() {
        return piosenkaId;
    }

    public void setPiosenkaId(Long piosenkaId) {
        this.piosenkaId = piosenkaId;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getWykonawca() {
        return wykonawca;
    }

    public void setWykonawca(String wykonawca) {
        this.wykonawca = wykonawca;
    }
}
