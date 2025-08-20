# Literalura

Literalura é uma aplicação de console em Java desenvolvida com Spring Boot, projetada para buscar, gerenciar e armazenar informações sobre livros e autores. A aplicação interage com a API pública Gutendex para buscar dados e persistir as informações em um banco de dados PostgreSQL.

## Funcionalidades

O menu interativo da aplicação oferece as seguintes opções:

* **Buscar livros por título**: Permite que o usuário pesquise um livro por título na API Gutendex. Os resultados são salvos no banco de dados local para consultas futuras.
* **Listar todos os livros**: Exibe todos os livros que foram salvos no banco de dados.
* **Listar autores**: Exibe todos os autores salvos no banco de dados.
* **Listar autores vivos em determinado ano**: Busca autores no banco de dados que estavam vivos em um ano específico.
* **Quantidade de livros por idioma**: Conta e exibe a quantidade de livros para cada idioma presente no banco de dados.
* **Sair**: Encerra a aplicação.

## Tecnologias Utilizadas

* **Java 21**: Linguagem de programação.
* **Spring Boot**: Framework para o desenvolvimento da aplicação.
* **Spring Data JPA**: Para a persistência de dados e interação com o banco de dados.
* **Hibernate**: Implementação da JPA.
* **PostgreSQL**: Sistema de gerenciamento de banco de dados relacional.
* **Maven**: Ferramenta de automação de construção para gerenciar dependências e compilar o projeto.
* **Gutendex API**: A API externa utilizada para buscar dados de livros.

## Requisitos

Para executar esta aplicação, você precisará ter o seguinte instalado:

* **Java Development Kit (JDK) 21** ou superior.
* **PostgreSQL**.

## Configuração e Execução

### 1. Configuração do Banco de Dados

Configure o acesso ao seu banco de dados PostgreSQL no arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura
spring.datasource.username=postgres
spring.datasource.password=sua_senha
```

Substitua sua_senha pela senha do seu usuário do PostgreSQL. O Hibernate criará as tabelas autor e livro automaticamente na inicialização da aplicação.

2. Execução da Aplicação
Navegue até o diretório Literalura e use o Maven para executar a aplicação a partir da linha de comando:
```./mvnw spring-boot:run```

Após a compilação, o menu interativo será exibido no console.
