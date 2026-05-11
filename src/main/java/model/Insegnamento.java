package model;

import java.util.List;

    public class Insegnamento {
    private String nome;
    private int cfu;
    private int annoCorso;
    private List<Lezione> lezioni;

    public Insegnamento(String nome, int cfu, int annoCorso) {
        this.nome = nome;
        this.cfu = cfu;
        this.annoCorso = annoCorso;
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

    public int getAnnoCorso() {
        return annoCorso;
    }

    public void setAnnoCorso(int annoCorso) {
        this.annoCorso = annoCorso;
    }

    public List<Lezione> getLezioni() {
        return lezioni;
    }

    public void setLezioni(List<Lezione> lezioni) {
        this.lezioni = lezioni;
    }
}
