package br.com.vendaspringboot.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.vendaspringboot.exception.PedidoNaoEncontradoExceptioin;
import br.com.vendaspringboot.exception.RegraNegocioException;
import br.com.vendaspringboot.rest.ApiErros;

@RestControllerAdvice
public class ApplicationControllerAdvice {
	@ExceptionHandler(RegraNegocioException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ApiErros handleRegraNegocioException(RegraNegocioException ex) {
		String msg = ex.getMessage();
		return new ApiErros(msg);
	}
	
	@ExceptionHandler(PedidoNaoEncontradoExceptioin.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErros handlePedidoNaoEncontradoException(PedidoNaoEncontradoExceptioin ex) {
		String msg = ex.getMessage();
		return new ApiErros(msg);
	}	
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ApiErros handleMethodNotValidException(MethodArgumentNotValidException ex) {
		List<String> erros = ex.getBindingResult().getAllErrors()
				.stream()
				.map(erro -> erro.getDefaultMessage())
				.collect(Collectors.toList());
		return new ApiErros(erros);
	}
}
