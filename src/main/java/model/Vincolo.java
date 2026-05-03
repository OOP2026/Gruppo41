package model;

import java.time.LocalTime;

    public class Vincolo {
    private String giornoSettimana;
    private LocalTime oraInizio;
    private LocalTime oraFine;

    public Vincolo(String giornoSettimana, LocalTime oraInizio, LocalTime oraFine) {
        this.giornoSettimana = giornoSettimana;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
    }

    public String getGiornoSettimana() {
        return giornoSettimana;
    }
    public void setGiornoSettimana(String giornoSettimana) {
        this.giornoSettimana = giornoSettimana;
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
