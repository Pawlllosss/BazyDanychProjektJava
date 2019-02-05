package redaktor.model;

import java.sql.Timestamp;

public class PiosenkaOdtwarzanie {
    private Long piosenkaOdtwarzanieId;
    private Timestamp czasOdtwarzania;
    private Long piosenkaId;
    private Long audycjaId;

    public PiosenkaOdtwarzanie(Long piosenkaOdtwarzanieId, Timestamp czasOdtwarzania, Long piosenkaId, Long audycjaId) {
        this.piosenkaOdtwarzanieId = piosenkaOdtwarzanieId;
        this.czasOdtwarzania = czasOdtwarzania;
        this.piosenkaId = piosenkaId;
        this.audycjaId = audycjaId;
    }

    public Long getPiosenkaOdtwarzanieId() {
        return piosenkaOdtwarzanieId;
    }

    public void setPiosenkaOdtwarzanieId(Long piosenkaOdtwarzanieId) {
        this.piosenkaOdtwarzanieId = piosenkaOdtwarzanieId;
    }

    public Timestamp getCzasOdtwarzania() {
        return czasOdtwarzania;
    }

    public void setCzasOdtwarzania(Timestamp czasOdtwarzania) {
        this.czasOdtwarzania = czasOdtwarzania;
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
