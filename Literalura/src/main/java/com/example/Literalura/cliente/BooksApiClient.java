package com.example.Literalura.cliente;

import com.example.Literalura.modelo.Livro;
import com.example.Literalura.modelo.Autor;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BooksApiClient {

    private final HttpClient client;
    private final ObjectMapper mapper;

    public BooksApiClient() {
        this.client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(20))
                .build();
        this.mapper = new ObjectMapper();
    }

    public List<Livro> buscarLivrosPorTitulo(String titulo) {
        String url = "https://gutendex.com/books?search=" + titulo.replace(" ", "+");
        System.out.println("Buscando livros na URL: " + url);

        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .header("Accept", "application/json")
                .header("User-Agent", "Java HttpClient") // Essencial para não ser bloqueado
                .timeout(Duration.ofSeconds(20))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Status da resposta: " + response.statusCode());
            if (response.statusCode() != 200) {
                System.out.println("Erro na requisição: " + response.body());
                return new ArrayList<>();
            }

            JsonNode root = mapper.readTree(response.body());
            JsonNode results = root.get("results");

            List<Livro> livros = new ArrayList<>();
            if (results != null && results.isArray()) {
                for (JsonNode node : results) {
                    Livro livro = new Livro();
                    livro.setTitulo(node.get("title").asText());

                    // Idioma (apenas o primeiro)
                    if (node.has("languages") && node.get("languages").isArray() && node.get("languages").size() > 0) {
                        livro.setIdioma(node.get("languages").get(0).asText());
                    }

                    // Múltiplos autores
                    JsonNode authorsNode = node.get("authors");
                    if (authorsNode != null && authorsNode.isArray() && authorsNode.size() > 0) {
                        List<Autor> listaAutores = new ArrayList<>();
                        for (JsonNode authorNode : authorsNode) {
                            Autor autor = new Autor();
                            autor.setNome(authorNode.get("name").asText());
                            autor.setAnoNascimento(authorNode.has("birth_year") && !authorNode.get("birth_year").isNull()
                                    ? authorNode.get("birth_year").asInt() : null);
                            autor.setAnoFalecimento(authorNode.has("death_year") && !authorNode.get("death_year").isNull()
                                    ? authorNode.get("death_year").asInt() : null);
                            listaAutores.add(autor);
                        }
                        if (!listaAutores.isEmpty()) {
                            livro.setAutor(listaAutores.get(0)); // define o primeiro como principal
                        }
                    }

                    livro.setDownloads(node.has("download_count") ? node.get("download_count").asInt() : 0);
                    livros.add(livro);
                }
            } else {
                System.out.println("Nenhum livro encontrado ou resposta inesperada da API:");
                System.out.println(response.body());
            }

            return livros;

        } catch (Exception e) {
            System.out.println("Erro ao buscar livros: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
