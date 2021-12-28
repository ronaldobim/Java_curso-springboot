package br.com.vendaspringboot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MinhaConifiguration {
	@Bean(name = "nomeAplicacao")
	public String nomeAplicacao() {
		return "Sistema de Vendas SpringBoot";
	}
}
