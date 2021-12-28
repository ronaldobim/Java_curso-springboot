package br.com.vendaspringboot.exception;

public class PedidoNaoEncontradoExceptioin extends RuntimeException{

	public PedidoNaoEncontradoExceptioin() {
		super("Pedido não encontrado");
	}
}
