package br.com.vendaspringboot.service;

import java.util.List;
import java.util.Optional;

import br.com.vendaspringboot.dto.PedidoDTO;
import br.com.vendaspringboot.enums.StatusPedido;
import br.com.vendaspringboot.model.Pedido;

public interface PedidoService {
	Pedido salvar(PedidoDTO dto);
	List<Pedido> find(Pedido filtro);
	Optional<Pedido> obterPedidoCompleto(Integer id);
	void atualizaStatus(Integer id, StatusPedido status);
}
