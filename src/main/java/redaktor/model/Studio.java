package redaktor.model;

public class Studio {
    private Long studioId;
    private String nazwa;
    private Integer nrPokoju;

    public Studio(Long studioId, String nazwa, Integer nrPokoju) {
        this.studioId = studioId;
        this.nazwa = nazwa;
        this.nrPokoju = nrPokoju;
    }

    public Long getStudioId() {
        return studioId;
    }

    public void setStudioId(Long studioId) {
        this.studioId = studioId;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Integer getNrPokoju() {
        return nrPokoju;
    }

    public void setNrPokoju(Integer nrPokoju) {
        this.nrPokoju = nrPokoju;
    }
}
