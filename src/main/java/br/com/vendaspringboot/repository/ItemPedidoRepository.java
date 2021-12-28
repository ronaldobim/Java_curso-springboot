package br.com.vendaspringboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.vendaspringboot.model.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Integer> {

}
