package model;

import java.time.LocalTime;
import java.util.List;
import java.util.ArrayList;

    public class Aula {
    private String nome;
    private List<Lezione> lezioni;

    public Aula(String nome) {
        this.nome = nome;
        this.lezioni = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Lezione> getLezioni() {
        return lezioni;
    }

    public void setLezioni(List<Lezione> lezioni) {
        this.lezioni = lezioni;
    }
}
