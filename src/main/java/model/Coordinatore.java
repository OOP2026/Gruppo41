package model;


// Coordinatore
public class Coordinatore extends Docente {

    public void approvaRichiesta(SpostamentoLezione s) {
        s.stato = "APPROVATA";
    }

    public void rifiuta(SpostamentoLezione s) {
        s.stato = "RIFIUTATA";
    }
}
