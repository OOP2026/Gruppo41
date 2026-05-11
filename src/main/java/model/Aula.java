package model;

// Aula
import java.util.ArrayList;
import java.util.List;

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
