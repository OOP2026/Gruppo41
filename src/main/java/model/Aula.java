package model;

import java.util.Objects;

public class Aula {
    private String nome;

    public Aula(String nome) {
        this.nome = nome;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Aula aula = (Aula) o;
        return Objects.equals(nome, aula.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome);
    }
}
