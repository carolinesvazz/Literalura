package com.alura.literalura.repository;
import com.alura.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    @Query("SELECT a FROM Autor a JOIN FETCH a.livros WHERE a.anoNascimento <= :ano AND" +
            " (a.anoFalecimento >= :ano OR a.anoFalecimento IS NULL)")
    List<Autor> buscarAutoresVivosNoAno(Integer ano);

    Optional<Autor> findByNomeContainingIgnoreCase(String nome);

    @Query("SELECT a FROM Autor a JOIN FETCH a.livros")
    List<Autor> findAllWithLivros();
}
