package br.com.locadora.ator;

import java.util.Optional;

//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AtorRepository extends JpaRepository<Ator, Long> {
	@Query("SELECT a FROM Ator a LEFT JOIN FETCH a.filmes WHERE a.id = :id")
	Optional<Ator> findByIdWithFilmes(@Param("id") Long id);

	Ator findByNome(String nome);


}
