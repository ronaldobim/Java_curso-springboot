package br.com.vendaspringboot.rest;

import java.util.Arrays;
import java.util.List;
import lombok.Getter;

public class ApiErros {
	@Getter
	private List<String> erros;
	
	public ApiErros(String msg) {
		this.erros = Arrays.asList(msg);
	}
	
	public ApiErros(List<String> erros) {
		this.erros = erros;
	}
}
