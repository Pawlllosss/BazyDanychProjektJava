package redaktor.model;

import java.sql.Time;

public class PiosenkaOdtwarzanie {
    private Long piosenkaOdtwarzanieId;
    private Time czasOdtworzenia;
    private Long piosenkaId;
    private Long audycjaId;

    public PiosenkaOdtwarzanie(Long piosenkaOdtwarzanieId, Time czasOdtworzenia, Long piosenkaId, Long audycjaId) {
        this.piosenkaOdtwarzanieId = piosenkaOdtwarzanieId;
        this.czasOdtworzenia = czasOdtworzenia;
        this.piosenkaId = piosenkaId;
        this.audycjaId = audycjaId;
    }

    public Long getPiosenkaOdtwarzanieId() {
        return piosenkaOdtwarzanieId;
    }

    public void setPiosenkaOdtwarzanieId(Long piosenkaOdtwarzanieId) {
        this.piosenkaOdtwarzanieId = piosenkaOdtwarzanieId;
    }

    public Time getCzasOdtworzenia() {
        return czasOdtworzenia;
    }

    public void setCzasOdtworzenia(Time czasOdtworzenia) {
        this.czasOdtworzenia = czasOdtworzenia;
    }

    public Long getPiosenkaId() {
        return piosenkaId;
    }

    public void setPiosenkaId(Long piosenkaId) {
        this.piosenkaId = piosenkaId;
    }

    public Long getAudycjaId() {
        return audycjaId;
    }

    public void setAudycjaId(Long audycjaId) {
        this.audycjaId = audycjaId;
    }
}
