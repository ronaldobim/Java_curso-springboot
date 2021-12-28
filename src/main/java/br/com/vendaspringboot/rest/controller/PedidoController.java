package br.com.vendaspringboot.rest.controller;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.cglib.core.CollectionUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.vendaspringboot.dto.AtualizacaoStatusPedidoDTO;
import br.com.vendaspringboot.dto.InformacoesItemPedidoDTO;
import br.com.vendaspringboot.dto.InformacoesPedidoDTO;
import br.com.vendaspringboot.dto.PedidoDTO;
import br.com.vendaspringboot.enums.StatusPedido;
import br.com.vendaspringboot.model.Cliente;
import br.com.vendaspringboot.model.ItemPedido;
import br.com.vendaspringboot.model.Pedido;
import br.com.vendaspringboot.service.PedidoService;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {
	
	private PedidoService service;

	public PedidoController(PedidoService service) {
		super();
		this.service = service;
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Integer save(@RequestBody @Valid PedidoDTO dto) {
		Pedido pedido = service.salvar(dto);
		return pedido.getId();
	}
	
	@GetMapping
	public List<Pedido> find(Pedido filtro) {		
		return service.find(filtro);
	}
	
	@GetMapping(value = "{id}")
	public InformacoesPedidoDTO getById(@PathVariable Integer id) {
		return service
				.obterPedidoCompleto(id)
				.map(  p -> converter(p))
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Pedido não encontrado") );
	}
	
	@PatchMapping("{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updateStatus(@PathVariable Integer id, 
							@RequestBody	AtualizacaoStatusPedidoDTO dto) {
		service.atualizaStatus(id, StatusPedido.valueOf(dto.getStatus()));
	}
	
	

	private InformacoesPedidoDTO converter(Pedido p) {
		return InformacoesPedidoDTO
				.builder()
				.codigo(p.getId())
				.cpf(p.getCliente().getCpf())
				.nomeCliente(p.getCliente().getNome())
				.total(p.getTotal())
				.status(p.getStatus().name())
				.items(converter(p.getItens()))
				.build();
	}
	
	private List<InformacoesItemPedidoDTO> converter(List<ItemPedido> itens) {
		if (itens == null) {
			return Collections.emptyList();
		}
		
		
		return itens.stream().map(
				item -> InformacoesItemPedidoDTO
							.builder()
							.descricaoProduto(item.getProduto().getDescricao())
							.precoUnitario(item.getProduto().getPreco())
							.quantidade(item.getQuantidade())
							.build()
		).collect(Collectors.toList());
	}

}
