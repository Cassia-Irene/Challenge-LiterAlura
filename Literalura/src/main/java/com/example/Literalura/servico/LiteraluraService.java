package com.example.Literalura.servico;

import com.example.Literalura.modelo.Livro;
import com.example.Literalura.modelo.Autor;
import com.example.Literalura.repositorio.LivroRepository;
import com.example.Literalura.repositorio.AutorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LiteraluraService {

    private final LivroRepository livroRepo;
    private final AutorRepository autorRepo;

    public LiteraluraService(LivroRepository livroRepo, AutorRepository autorRepo) {
        this.livroRepo = livroRepo;
        this.autorRepo = autorRepo;
    }

    @Transactional
    public void salvarLivro(Livro livro) {
        if (livro.getAutor() != null) {
            // Salva o autor primeiro para garantir a relação
            Autor autorPersistido = autorRepo.save(livro.getAutor());
            livro.setAutor(autorPersistido);
        }
        livroRepo.save(livro);
    }

    public List<Livro> listarLivros() {
        return livroRepo.findAll();
    }

    public List<Autor> listarAutores() {
        return autorRepo.findAll();
    }

    public List<Autor> autoresVivosEmAno(int ano) {
        return autorRepo.findByAnoNascimentoLessThanEqualAndAnoFalecimentoGreaterThanEqual(ano, ano);
    }

    public Map<String, Long> quantidadeLivrosPorIdioma() {
        return livroRepo.findAll().stream()
                .collect(Collectors.groupingBy(Livro::getIdioma, Collectors.counting()));
    }
}
