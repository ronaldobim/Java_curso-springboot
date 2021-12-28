package br.com.vendaspringboot.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.vendaspringboot.model.Cliente;
import br.com.vendaspringboot.model.Produto;
import br.com.vendaspringboot.repository.ProdutoRepository;

@RestController
@RequestMapping("/api/produtos")
public class ProdutoController {
		
	private static final String PRODUTO_NAO_ENCONTRADO = "Produto não encontrado";
	private ProdutoRepository produtos;
	
	public ProdutoController(ProdutoRepository produtos) {
		super();
		this.produtos = produtos;
	}



	@GetMapping(value = "{id}")
	public Produto getProdutoById(@PathVariable  Integer id)  {
		return produtos.findById(id)
				.orElseThrow(
						() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, 
								PRODUTO_NAO_ENCONTRADO));		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Produto save(@RequestBody @Valid  Produto produto) {		 
		return produtos.save(produto);
	}
	
	@DeleteMapping(value = "{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable  Integer id) {
		Optional<Produto> produto = produtos.findById(id);
		if (produto.isPresent()) {
			produtos.delete(produto.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, PRODUTO_NAO_ENCONTRADO);
		}
	}
	
	@PutMapping(value = "{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody @Valid Produto produto) {
		Optional<Produto> produtoExistente = produtos.findById(id);
		if (produtoExistente.isPresent()) {
			produto.setId(produtoExistente.get().getId());
			produtos.save(produto);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");	
		}
		
	}
	
	@GetMapping
	public List<Produto> find(Produto filtro) {
		ExampleMatcher matcher = 
				ExampleMatcher
					.matching()
					.withIgnoreCase()
					.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher); 
		return produtos.findAll(example);
	}
	

}
