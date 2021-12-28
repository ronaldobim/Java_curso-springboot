package br.com.vendaspringboot.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.vendaspringboot.dto.ItemPedidoDTO;
import br.com.vendaspringboot.dto.PedidoDTO;
import br.com.vendaspringboot.enums.StatusPedido;
import br.com.vendaspringboot.exception.PedidoNaoEncontradoExceptioin;
import br.com.vendaspringboot.exception.RegraNegocioException;
import br.com.vendaspringboot.model.Cliente;
import br.com.vendaspringboot.model.ItemPedido;
import br.com.vendaspringboot.model.Pedido;
import br.com.vendaspringboot.model.Produto;
import br.com.vendaspringboot.repository.ClienteRepository;
import br.com.vendaspringboot.repository.ItemPedidoRepository;
import br.com.vendaspringboot.repository.PedidoRepository;
import br.com.vendaspringboot.repository.ProdutoRepository;
import br.com.vendaspringboot.service.PedidoService;

@Service
public class PedidoServiceImpl implements PedidoService {
	
	private PedidoRepository pedidos;
	private ClienteRepository clientes;
	private ProdutoRepository produtos;
	private ItemPedidoRepository itemsPedido;

	public PedidoServiceImpl(PedidoRepository pedidos, ClienteRepository clientes, 
			ProdutoRepository produtos, ItemPedidoRepository itemsPedido) {
		super();
		this.pedidos = pedidos;
		this.clientes = clientes;
		this.produtos = produtos;
		this.itemsPedido = itemsPedido;
	}

	@Override
	@Transactional
	public Pedido salvar(PedidoDTO dto) {
		Cliente cliente = clientes.findById(dto.getCliente()).orElseThrow(
				() -> new RegraNegocioException("Código de cliente inválido"));
		Pedido pedido = new Pedido();
		pedido.setTotal(dto.getTotal());
		pedido.setDataPedido(LocalDate.now());
		pedido.setCliente(cliente);
		pedido.setStatus(StatusPedido.REALIZADO);
		this.converterItems(pedido, dto.getItems());		
		pedidos.save(pedido);
		itemsPedido.saveAll(pedido.getItens());
				
		return pedido;
	}
	
	private void converterItems(Pedido pedido, List<ItemPedidoDTO> items) {
		if (items.isEmpty()) {
			throw new RegraNegocioException("Não é possível realizar um pedido sem items.");
		}
		
		List<ItemPedido> itensPedido = new ArrayList<ItemPedido>();
		
		for (ItemPedidoDTO c : items) {			
			Produto produto = produtos.findById(c.getProduto()).orElseThrow(
					() -> new RegraNegocioException("Código de produto inválido"));
			if (c.getQuantidade() <= 0) {
				throw new RegraNegocioException("Quantidade do produto deve ser maior que zero");
			}
			ItemPedido itemPedido = new ItemPedido();
			itemPedido.setPedido(pedido);
			itemPedido.setProduto(produto);
			itemPedido.setQuantidade(c.getQuantidade());
			itensPedido.add(itemPedido);
			
		}
		
		pedido.setItens(itensPedido);
	}
	
	public List<Pedido> find(Pedido filtro) {
		ExampleMatcher matcher = 
				ExampleMatcher
					.matching()
					.withIgnoreCase()
					.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher); 
		return pedidos.findAll(example);
	}

	@Override
	public Optional<Pedido> obterPedidoCompleto(Integer id) {
		return pedidos.findByIdFetchItens(id);
	}

	@Override
	@Transactional
	public void atualizaStatus(Integer id, StatusPedido status) {
		Pedido pedido = pedidos.findById(id).get();
		if (pedido != null) {
			pedido.setStatus(status);
			pedidos.save(pedido);
		} else {
			throw new PedidoNaoEncontradoExceptioin(); 
		}
			
	}
	

	
}
