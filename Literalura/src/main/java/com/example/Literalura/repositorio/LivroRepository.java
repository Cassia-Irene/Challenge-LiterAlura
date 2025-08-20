package com.example.Literalura.repositorio;

import com.example.Literalura.modelo.Livro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LivroRepository extends JpaRepository<Livro, Long> {
}
