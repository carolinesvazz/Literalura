package com.alura.literalura.repository;
import com.alura.literalura.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Long> {

    Optional<Livro> findByTituloContainingIgnoreCase(String titulo);

    /**
     * Busca livros por um determinado idioma e, ao mesmo tempo, carrega
     * a lista de autores associados para evitar LazyInitializationException.
     * @param idioma O código do idioma (ex: "pt", "en").
     * @return Uma lista de livros com seus autores já inicializados.
     */
    @Query("SELECT l FROM Livro l JOIN FETCH l.autores WHERE l.idioma = :idioma")
    List<Livro> findByIdioma(String idioma);

    /**
     * Busca todos os livros e carrega seus autores associados.
     * @return Uma lista de todos os livros com seus autores já inicializados.
     */
    @Query("SELECT l FROM Livro l JOIN FETCH l.autores")
    List<Livro> findAllWithAutores();
}
