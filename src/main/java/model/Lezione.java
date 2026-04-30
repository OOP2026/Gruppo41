package model;

import java.time.LocalTime;

class Lezione {
    private String giornoSettimana;
    private LocalTime oraInizio;
    private LocalTime oraFine;

    private Aula aula;
    private Insegnamento insegnamento;

    Lezione(String giornoSettimana, LocalTime oraInizio, LocalTime oraFine) {
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

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
    }

    public Insegnamento getInsegnamento() {
        return insegnamento;
    }

    public void setInsegnamento(Insegnamento insegnamento) {
        this.insegnamento = insegnamento;
    }
}