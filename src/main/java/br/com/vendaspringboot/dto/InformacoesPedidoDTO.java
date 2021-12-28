package br.com.vendaspringboot.dto;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InformacoesPedidoDTO {
	private Integer codigo;
	private String cpf;
	private String nomeCliente;
	private BigDecimal total;
	private String status;
	private List<InformacoesItemPedidoDTO> items;
}
