package model;

import java.time.LocalTime;

public class SpostamentoLezione {

    private Lezione lezione;
    private String nuovoGiorno;
    private LocalTime nuovaOraInizio;
    private LocalTime nuovaOraFine;
    private String stato;

    public SpostamentoLezione(Lezione lezione, String nuovoGiorno, LocalTime nuovaOraInizio,
                               LocalTime nuovaOraFine) {
        this.lezione = lezione;
        this.nuovoGiorno = nuovoGiorno;
        this.nuovaOraInizio = nuovaOraInizio;
        this.nuovaOraFine = nuovaOraFine;
        this.stato = "IN_ATTESA";
    }

    public Lezione getLezione() {
        return lezione;
    }

    public void setLezione(Lezione lezione) {
        this.lezione = lezione;
    }

    public String getNuovoGiorno() {
        return nuovoGiorno;
    }

    public void setNuovoGiorno(String nuovoGiorno) {
        this.nuovoGiorno = nuovoGiorno;
    }

    public LocalTime getNuovaOraInizio() {
        return nuovaOraInizio;
    }

    public void setNuovaOraInizio(LocalTime nuovaOraInizio) {
        this.nuovaOraInizio = nuovaOraInizio;
    }

    public LocalTime getNuovaOraFine() {
        return nuovaOraFine;
    }

    public void setNuovaOraFine(LocalTime nuovaOraFine) {
        this.nuovaOraFine = nuovaOraFine;
    }

    public String getStato() {
        return stato;
    }

    public void setStato(String stato) {
        this.stato = stato;
    }
}
