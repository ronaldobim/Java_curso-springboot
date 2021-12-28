package br.com.vendaspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.vendaspringboot.model.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

}
