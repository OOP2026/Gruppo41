package model;

import java.time.LocalTime;
import java.util.Objects;

public class Lezione {
    private Insegnamento insegnamento;
    private String giornoSettimana;
    private LocalTime oraInizio;
    private LocalTime oraFine;
    private Aula aula;

    public Lezione(Insegnamento insegnamento, String giornoSettimana, LocalTime oraInizio, LocalTime oraFine, Aula aula) {
        this.insegnamento = insegnamento;
        this.giornoSettimana = giornoSettimana;
        this.oraInizio = oraInizio;
        this.oraFine = oraFine;
        this.aula = aula;
    }

    public Insegnamento getInsegnamento() { return insegnamento; }
    public void setInsegnamento(Insegnamento insegnamento) { this.insegnamento = insegnamento; }
    public String getGiornoSettimana() { return giornoSettimana; }
    public void setGiornoSettimana(String giornoSettimana) { this.giornoSettimana = giornoSettimana; }
    public LocalTime getOraInizio() { return oraInizio; }
    public void setOraInizio(LocalTime oraInizio) { this.oraInizio = oraInizio; }
    public LocalTime getOraFine() { return oraFine; }
    public void setOraFine(LocalTime oraFine) { this.oraFine = oraFine; }
    public Aula getAula() { return aula; }
    public void setAula(Aula aula) { this.aula = aula; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lezione lezione = (Lezione) o;
        return Objects.equals(insegnamento, lezione.insegnamento) &&
                Objects.equals(giornoSettimana, lezione.giornoSettimana) &&
                Objects.equals(oraInizio, lezione.oraInizio) &&
                Objects.equals(oraFine, lezione.oraFine) &&
                Objects.equals(aula, lezione.aula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(insegnamento, giornoSettimana, oraInizio, oraFine, aula);
    }
}
