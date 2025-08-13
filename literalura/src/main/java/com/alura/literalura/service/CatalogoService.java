package com.alura.literalura.service;

import com.alura.literalura.model.Autor;
import com.alura.literalura.model.Livro;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogoService {

    private final com.alura.literalura.repository.LivroRepository livroRepository;
    private final com.alura.literalura.repository.AutorRepository autorRepository;

    public CatalogoService(com.alura.literalura.repository.LivroRepository livroRepository, com.alura.literalura.repository.AutorRepository autorRepository) {
        this.livroRepository = livroRepository;
        this.autorRepository = autorRepository;
    }

    // A anotação @Transactional garante que a sessão com o banco de dados
    // permaneça aberta durante a execução de todo o método.
    // readOnly = true é uma otimização para operações de apenas leitura.
    @Transactional(readOnly = true)
    public List<com.alura.literalura.model.Livro> listarLivrosRegistrados() {
        return livroRepository.findAllWithAutores();
    }

    @Transactional(readOnly = true)
    public List<com.alura.literalura.model.Autor> listarAutoresRegistrados() {
        return autorRepository.findAllWithLivros();
    }

    @Transactional(readOnly = true)
    public List<com.alura.literalura.model.Autor> listarAutoresVivosPorAno(int ano) {
        return autorRepository.buscarAutoresVivosNoAno(ano);
    }

    @Transactional(readOnly = true)
    public List<com.alura.literalura.model.Livro> listarLivrosPorIdioma(String idioma) {
        return livroRepository.findByIdioma(idioma);
    }
}
