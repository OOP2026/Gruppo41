package model;

import java.time.LocalDate;
import java.time.LocalTime;

class SpostamentoLezione {
    private LocalDate dataRichiesta;
    private String stato;
    private LocalDate nuovaData;
    private LocalTime nuovoOrarioInizio;
    private LocalTime nuovoOrarioFine;
    private Lezione lezione;

    SpostamentoLezione(LocalDate dataRichiesta, String stato) {
        this.dataRichiesta = dataRichiesta;
        this.stato = stato;
    }

    public LocalDate getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(LocalDate dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }

    public LocalDate getNuovaData() {
        return nuovaData;
    }

    public void setNuovaData(LocalDate nuovaData) {
        this.nuovaData = nuovaData;
    }

    public LocalTime getNuovoOrarioInizio() {
        return nuovoOrarioInizio;
    }

    public void setNuovoOrarioInizio(LocalTime nuovoOrarioInizio) {
        this.nuovoOrarioInizio = nuovoOrarioInizio;
    }

    public LocalTime getNuovoOrarioFine() {
        return nuovoOrarioFine;
    }

    public void setNuovoOrarioFine(LocalTime nuovoOrarioFine) {
        this.nuovoOrarioFine = nuovoOrarioFine;
    }

    public Lezione getLezione() {
        return lezione;
    }

    public void setLezione(Lezione lezione) {
        this.lezione = lezione;
    }
}
