package br.com.vendaspringboot.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import br.com.vendaspringboot.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	//Query Methodos
	List<Cliente> findByNomeIgnoreCaseContaining(String nome);	
	Cliente findOneByNome(String nome);
	boolean existsByNome(String nome);
	@Query(value = "select c from Cliente c where nome like %:nome%")
	List<Cliente> encontrarPorNome(@Param("nome") String nome);
	@Query(value = "select * from Cliente c where nome like %:nome%", nativeQuery = true)
	List<Cliente> encontrarPorNomeSQLNativo(@Param("nome") String nome);
	@Query(value = "delete from Cliente c where nome = :nome")
	@Modifying
	void deleteByNome(@Param("nome") String nome);

}
