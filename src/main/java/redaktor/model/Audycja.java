package redaktor.model;

import java.sql.Timestamp;

public class Audycja {
    private Long audycjaId;
    private Timestamp dataPoczatek;
    private Timestamp dataKoniec;
    private Long programId;
    private Long studioId;

    public Audycja(Long audycjaId, Timestamp dataPoczatek, Timestamp dataKoniec, Long programId, Long studioId) {
        this.audycjaId = audycjaId;
        this.dataPoczatek = dataPoczatek;
        this.dataKoniec = dataKoniec;
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

    public Timestamp getDataKoniec() {
        return dataKoniec;
    }

    public void setDataKoniec(Timestamp dataKoniec) {
        this.dataKoniec = dataKoniec;
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
