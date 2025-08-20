package com.example.Literalura;

import com.example.Literalura.cliente.BooksApiClient;
import com.example.Literalura.modelo.Livro;
import com.example.Literalura.modelo.Autor;
import com.example.Literalura.servico.LiteraluraService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication {

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Bean
	CommandLineRunner run(LiteraluraService service) {
		return args -> {
			Scanner scanner = new Scanner(System.in);
			BooksApiClient client = new BooksApiClient();
			boolean running = true;

			while (running) {
				System.out.println("\n=== Menu Literalura ===");
				System.out.println("1. Buscar livros por título");
				System.out.println("2. Listar todos os livros");
				System.out.println("3. Listar autores");
				System.out.println("4. Listar autores vivos em determinado ano");
				System.out.println("5. Quantidade de livros por idioma");
				System.out.println("0. Sair");
				System.out.print("Escolha uma opção: ");

				int opcao = scanner.nextInt();
				scanner.nextLine(); // consumir Enter

				switch (opcao) {
					case 1 -> {
						System.out.print("Digite o título do livro: ");
						String title = scanner.nextLine();
						List<Livro> encontrados = client.buscarLivrosPorTitulo(title);
						for (Livro l : encontrados) {
							service.salvarLivro(l); // salva no banco
						}
						encontrados.forEach(System.out::println);
					}
					case 2 -> {
						List<Livro> livros = service.listarLivros();
						if (livros.isEmpty()) System.out.println("Nenhum livro no banco.");
						else livros.forEach(System.out::println);
					}
					case 3 -> {
						List<Autor> autores = service.listarAutores();
						if (autores.isEmpty()) System.out.println("Nenhum autor disponível.");
						else autores.forEach(System.out::println);
					}
					case 4 -> {
						System.out.print("Digite o ano para filtrar autores vivos: ");
						int ano = scanner.nextInt();
						List<Autor> vivos = service.autoresVivosEmAno(ano);
						if (vivos.isEmpty()) System.out.println("Nenhum autor vivo encontrado nesse ano.");
						else vivos.forEach(System.out::println);
					}
					case 5 -> {
						Map<String, Long> qtdPorIdioma = service.quantidadeLivrosPorIdioma();
						if (qtdPorIdioma.isEmpty()) System.out.println("Nenhum livro disponível.");
						else qtdPorIdioma.forEach((idioma, qtd) ->
								System.out.println("Idioma: " + idioma + " - Quantidade: " + qtd));
					}
					case 0 -> running = false;
					default -> System.out.println("Opção inválida!");
				}
			}

			scanner.close();
			System.out.println("Programa encerrado.");
		};
	}
}
