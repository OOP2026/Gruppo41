package model;

import java.time.LocalTime;

public class Vincolo {

    private String giorno;
    private LocalTime oraInizio;
    private LocalTime oraFine;

    public Vincolo(String giorno, LocalTime oraInizio, LocalTime oraFine) {
        this.giorno = giorno;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
    }

    public String getGiorno() {
        return giorno;
    }

    public void setGiorno(String giorno) {
        this.giorno = giorno;
    }

    public LocalTime getOraInizio() {
        return oraInizio;
    }

    public void setOraInizio(LocalTime oraInizio) {
        this.oraInizio = oraInizio;
    }

    public LocalTime getOraFine() {
        return oraFine;
    }

    public void setOraFine(LocalTime oraFine) {
        this.oraFine = oraFine;
    }
}
