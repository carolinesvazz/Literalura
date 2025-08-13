package com.alura.literalura.principal;

import com.alura.literalura.model.*;
import com.alura.literalura.repository.AutorRepository;
import com.alura.literalura.repository.LivroRepository;
import com.alura.literalura.service.CatalogoService;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConverteDados;
import org.springframework.stereotype.Component;
import java.util.Scanner;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

    @Component
    public class Principal {
        private final Scanner leitura = new Scanner(System.in);
        private final String ENDERECO_BASE = "https://gutendex.com/books/";

        private final ConsumoAPI consumo;
        private final ConverteDados conversor;
        private final LivroRepository livroRepository; // Ainda necessário para salvar
        private final AutorRepository autorRepository; // Ainda necessário para salvar
        private final CatalogoService catalogoService; // Nova dependência

        public Principal(ConsumoAPI consumo, ConverteDados conversor, LivroRepository livroRepository, AutorRepository autorRepository, CatalogoService catalogoService) {
            this.consumo = consumo;
            this.conversor = conversor;
            this.livroRepository = livroRepository;// Injeta o repositório
            this.autorRepository = autorRepository;
            this.catalogoService = catalogoService; // Injeta o serviço
        }

        public void exibeMenu() {
            var opcao = -1;
            while (opcao != 0) {
                var menu = """
                
                Escolha uma opção:
                1 - Buscar livro pelo título
                2 - Listar livros registrados
                3 - Listar autores registrados
                4 - Listar autores vivos em determinado ano
                5 - Listar livros em determinado idioma
                        
                0 - Sair                                 
                """;

                System.out.println(menu);
                opcao = leitura.nextInt();
                leitura.nextLine();

                switch (opcao) {
                    case 1: buscarLivroWeb(); break;
                    case 2: listarLivrosRegistrados(); break;
                    case 3: listarAutoresRegistrados(); break;
                    case 4: listarAutoresVivosPorAno(); break;
                    case 5: listarLivrosPorIdioma(); break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                }
            }
        }

        //O método de busca permanece na classe Principal, pois envolve lógica de API

        private void buscarLivroWeb() {
            // ... seu método buscarLivroWeb() permanece o mesmo
            System.out.println("Digite o título do livro que deseja buscar:");
            var nomeLivro = leitura.nextLine();

            Optional<Livro> livroExistente = livroRepository.findByTituloContainingIgnoreCase(nomeLivro);
            if (livroExistente.isPresent()) {
                System.out.println("\nEste livro já está cadastrado no nosso banco de dados:");
                System.out.println(livroExistente.get());
                return;
            }

            try {
                var nomeLivroCodificado = URLEncoder.encode(nomeLivro, StandardCharsets.UTF_8);
                String url = ENDERECO_BASE + "?search=" + nomeLivroCodificado;
                var json = consumo.obterDados(url);
                DadosBusca dadosBusca = conversor.obterDados(json, DadosBusca.class);

                Optional<DadosLivro> dadosLivroOpt = dadosBusca.resultados().stream()
                        .filter(l -> l.titulo().equalsIgnoreCase(nomeLivro))
                        .findFirst();

                if (dadosLivroOpt.isPresent()) {
                    DadosLivro dadosLivro = dadosLivroOpt.get();
                    Autor autor;
                    if (!dadosLivro.autores().isEmpty()) {
                        DadosAutor dadosAutor = dadosLivro.autores().get(0);
                        Optional<Autor> autorExistente = autorRepository.findByNomeContainingIgnoreCase(dadosAutor.nome());
                        autor = autorExistente.orElseGet(() -> {
                            Autor novoAutor = new Autor();
                            novoAutor.setNome(dadosAutor.nome());
                            novoAutor.setAnoNascimento(dadosAutor.anoNascimento());
                            novoAutor.setAnoFalecimento(dadosAutor.anoFalecimento());
                            return novoAutor;
                        });
                    } else {
                        autor = autorRepository.findByNomeContainingIgnoreCase("Desconhecido")
                                .orElseGet(() -> {
                                    Autor autorDesconhecido = new Autor();
                                    autorDesconhecido.setNome("Desconhecido");
                                    return autorDesconhecido;
                                });
                    }

                    Livro novoLivro = new Livro();
                    novoLivro.setTitulo(dadosLivro.titulo());
                    novoLivro.setIdioma(dadosLivro.idiomas().get(0));
                    novoLivro.setNumeroDownloads(dadosLivro.numeroDownloads());
                    novoLivro.getAutores().add(autor);
                    livroRepository.save(novoLivro);

                    System.out.println("\nLivro salvo com sucesso!");
                    System.out.println("\nNovo livro cadastrado: " + novoLivro);

                } else {
                    System.out.println("\nNão encontramos este livro. Por favor, verifique a ortografia ou tente um título diferente.");
                }
            } catch (Exception e) {
                System.out.println("Ocorreu um erro durante a busca: " + e.getMessage());
            }
        }

        // MÉTODOS DE LISTAGEM AGORA USAM O SERVIÇO
        private void listarLivrosRegistrados() {
            System.out.println("\n--- LIVROS REGISTRADOS ---");
            List<Livro> livros = catalogoService.listarLivrosRegistrados();
            if (livros.isEmpty()) {
                System.out.println("Nenhum livro cadastrado.");
            } else {
                livros.forEach(System.out::println);
            }
        }

        private void listarAutoresRegistrados() {
            System.out.println("\n--- AUTORES REGISTRADOS ---");
            List<Autor> autores = catalogoService.listarAutoresRegistrados();
            if (autores.isEmpty()) {
                System.out.println("Nenhum autor cadastrado.");
            } else {
                autores.forEach(System.out::println);
            }
        }

        private void listarAutoresVivosPorAno() {
            System.out.println("Digite o ano para a pesquisa:");
            var ano = leitura.nextInt();
            leitura.nextLine();

            List<Autor> autoresVivos = catalogoService.listarAutoresVivosPorAno(ano);

            if (autoresVivos.isEmpty()) {
                System.out.println("\nNenhum autor vivo encontrado para o ano de " + ano + ".");
            } else {
                System.out.println("\n--- AUTORES VIVOS EM " + ano + " ---");
                autoresVivos.forEach(System.out::println);
            }
        }

        private void listarLivrosPorIdioma() {
            System.out.println("""
            Digite o idioma para a busca:
            es - espanhol
            en - inglês
            fr - francês
            pt - português
            """);
            var idioma = leitura.nextLine();

            List<Livro> livrosPorIdioma = catalogoService.listarLivrosPorIdioma(idioma);

            if (livrosPorIdioma.isEmpty()) {
                System.out.println("\nNenhum livro encontrado para o idioma '" + idioma + "'.");
            } else {
                System.out.println("\n--- LIVROS NO IDIOMA: " + idioma + " ---");
                livrosPorIdioma.forEach(System.out::println);
            }
        }
    }
