# Desafio Literalura 📚

Este projeto foi desenvolvido para realizar consultas de livros por autores, a proposta é desenvolver um catálogo de livros dinâmico, integrando-se à API pública Gutendex para obter dados de obras e autores e registrá-los em um banco de dados local.

O sistema oferece um menu interativo que possibilita ao usuário pesquisar títulos, visualizar informações já salvas e realizar consultas personalizadas, como identificar autores vivos em um ano específico ou listar livros disponíveis em determinado idioma.

Para esta aplicação a ferramenta utilizada foi o IntelliJ-IDEA e Postgre para armazenamento no banco de dados.

♦ Conhecimentos adquiridos do programa Oracle Next Education/ONE - Alura, turma G8 - fase de especialização em Back-End.


## 📌 Funcionalidades principais

🔍 Busca inteligente de livros por título via API Gutendex, com registro automático de obras e autores.

📚 Listagem rápida de todos os livros e autores cadastrados.

🧾 Consulta personalizada de autores vivos em um ano específico.

🌐 Filtro por idioma (PT, EN, ES, FR) para explorar o acervo de forma direcionada.

## Tecnologias Utilizadas 🛠️

- **Java** 17 (Java SE)
- **Gutendex API**
- **Gson** (para serialização e desserialização de JSON)
- **IntelliJ-IDEA**
- **Hibernate**
- **Spring Boot 3: Framework**
-  **Spring Data JPA**
-  **PostgreSQL**
-  **Jackson**
-  **Maven**

![Java](https://img.shields.io/badge/Java-17%2B-blue?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-green?style=for-the-badge&logo=spring&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-14-blue?style=for-the-badge&logo=postgresql&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-4.0-red?style=for-the-badge&logo=apache-maven&logoColor=white)

## Estrutura do Código 💻

- **ConsumoAPI**

Esta classe é responsável por realizar requisições HTTP à Gutendex API. Ela utiliza o HttpClient para enviar requisições HTTP, HttpRequest para configurar as URLs das requisições e HttpResponse para capturar as respostas da API. As funções dessa classe são usadas para buscar os códigos dos veículos da API e retornar com as informações. As excessões são implementadas para evitar erros durante o processo de reprodução dos códigos.

- **Principal**

Nesta classe você encontra Scanner para realizar a leitura da opção digitada pelo usuário, biblioteca Gson para complementar a classe de requisição da API, condicionais (if e else), estrutura de Lambdas e Stream.

## Fontes utilizadas: 

📍 API utilizada: [https://deividfortuna.github.io/fipe/?ref=public_apis&utm_medium=website](https://gutendex.com/)

📥 Dowload do projeto inicial Spring: https://start.spring.io/
- Dependências adicionadas: Spring Data JPA / Postgre SQL Driver
- Opções adicionadas: Project - Maven
- Language: Java
- Spring Boot 3.5.4

📌 Código HTTPS para API e Json: https://docs.oracle.com/en/java/javase/17/docs/api/java.net.http/java/net/http/HttpRequest.html

📚 Biblioteca Gson: https://mvnrepository.com/artifact/com.google.code.gson/gson

📥Dowload PgAdmin (PostgreSQL): https://www.pgadmin.org/download/

## Como Executar o Projeto ▶️

1. Clone este repositório:
   `bash
   git clone https://github.com/carolinesvazz/Literalura

## 📄 Licença

Este projeto está sob a licença MIT. Sinta-se à vontade para utilizá-lo e modificá-lo conforme necessário.
