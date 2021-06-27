package br.com.alura.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {

	//Padrão de nome do Spring Data P "_" é para indicar a separaçao e a classe e o atributo
	List<Topico> findByCurso_Nome(String nomeCurso);
	
	//Padrão de nome customizado
	@Query("SELECT m FROM Topico m WHERE m.curso.nome = :nomeCurso")
	List<Topico> carregarPorNomeDoCurso(@Param("nomeCurso") String nomeCurso);
	

}
