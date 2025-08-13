package com.alura.literalura.model;

import com.alura.literalura.model.Livro;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "autores")
public class Autor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String nome;

    private Integer anoNascimento;
    private Integer anoFalecimento;
//ManyToMany = um autor para muitas músicas
    @ManyToMany(mappedBy = "autores", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Livro> livros = new ArrayList<>();

    // Construtor padrão que e exigido pelo JPA
    public Autor() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getAnoNascimento() {
        return anoNascimento;
    }

    public void setAnoNascimento(Integer anoNascimento) {
        this.anoNascimento = anoNascimento;
    }

    public Integer getAnoFalecimento() {
        return anoFalecimento;
    }

    public void setAnoFalecimento(Integer anoFalecimento) {
        this.anoFalecimento = anoFalecimento;
    }

    public List<Livro> getLivros() {
        return livros;
    }

    public void setLivros(List<Livro> livros) {
        this.livros = livros;
    }

    @Override
    public String toString() {
        // Mapeia a lista de livros para uma string de títulos
        String titulosLivros = livros.stream()
                .map(Livro::getTitulo)
                .collect(Collectors.joining(", "));

        return "------ AUTOR ------" +
                "\nNome: " + nome +
                "\nAno de Nascimento: " + anoNascimento +
                "\nAno de Falecimento: " + anoFalecimento +
                "\nLivros: [" + (titulosLivros.isEmpty() ? "Nenhum livro registrado" : titulosLivros) + "]" +
                "\n-------------------";
    }
}
