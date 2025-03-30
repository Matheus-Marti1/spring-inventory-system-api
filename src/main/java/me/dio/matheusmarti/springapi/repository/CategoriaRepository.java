package me.dio.matheusmarti.springapi.repository;

import me.dio.matheusmarti.springapi.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    boolean existsByNomeIgnoreCase(String nome);
    boolean existsByNomeAndIdNot(String nome, Long id);
}
