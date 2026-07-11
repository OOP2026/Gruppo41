package model;

public class Insegnamento {

    private String nome;
    private int cfu;
    private String annoCorso;
    private Docente docente;

    public Insegnamento(String nome, int cfu, String annoCorso, Docente docente) {
        this.nome = nome;
        this.cfu = cfu;
        this.annoCorso = annoCorso;
        this.docente = docente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCfu() {
        return cfu;
    }

    public void setCfu(int cfu) {
        this.cfu = cfu;
    }

    public String getAnnoCorso() {
        return annoCorso;
    }

    public void setAnnoCorso(String annoCorso) {
        this.annoCorso = annoCorso;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    @Override
    public String toString() {
        return nome + " (anno " + annoCorso + ", " + docente.getNome() + " " + docente.getCognome() + ")";
    }
}
