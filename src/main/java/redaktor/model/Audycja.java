package redaktor.model;

import java.sql.Time;
import java.sql.Timestamp;

public class Audycja {
    private Long audycjaId;
    private Timestamp dataPoczatek;
    private Time czasTrwania;
    private Long programId;
    private Long studioId;

    public Audycja(Long audycjaId, Timestamp dataPoczatek, Time czasTrwania, Long programId, Long studioId) {
        this.audycjaId = audycjaId;
        this.dataPoczatek = dataPoczatek;
        this.czasTrwania = czasTrwania;
        this.programId = programId;
        this.studioId = studioId;
    }

    public Long getAudycjaId() {
        return audycjaId;
    }

    public void setAudycjaId(Long audycjaId) {
        this.audycjaId = audycjaId;
    }

    public Timestamp getDataPoczatek() {
        return dataPoczatek;
    }

    public void setDataPoczatek(Timestamp dataPoczatek) {
        this.dataPoczatek = dataPoczatek;
    }

    public Time getCzasTrwania() {
        return czasTrwania;
    }

    public void setCzasTrwania(Time czasTrwania) {
        this.czasTrwania = czasTrwania;
    }

    public Long getProgramId() {
        return programId;
    }

    public void setProgramId(Long programId) {
        this.programId = programId;
    }

    public Long getStudioId() {
        return studioId;
    }

    public void setStudioId(Long studioId) {
        this.studioId = studioId;
    }
}
