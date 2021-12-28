package br.com.vendaspringboot.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class InformacoesItemPedidoDTO {
	private String descricaoProduto;
	private BigDecimal precoUnitario;
	private Integer quantidade;
}
