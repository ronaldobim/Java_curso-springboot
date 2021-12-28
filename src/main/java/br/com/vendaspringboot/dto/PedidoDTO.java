package br.com.vendaspringboot.dto;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.constraints.NotNull;

import br.com.vendaspringboot.validation.NotEmptyList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
	@NotNull(message = "código do cliente obrigatório")
	private Integer cliente;
	@NotNull(message = "total do pedido obrigatório")
	private BigDecimal total;
	@NotEmptyList(message = "pedido nao pode ser realizado sem itens")
	private List<ItemPedidoDTO> items;
	
}
