package br.com.vendaspringboot.rest.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.com.vendaspringboot.model.Cliente;
import br.com.vendaspringboot.repository.ClienteRepository;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	private ClienteRepository clientes;
	
	public ClienteController(ClienteRepository clientes) {
		super();
		this.clientes = clientes;
	}



	@GetMapping(value = "{id}")
	public Cliente getClienteById(@PathVariable  Integer id)  {
		return clientes.findById(id)
				.orElseThrow(
						() ->  new ResponseStatusException(HttpStatus.NOT_FOUND, 
								"Cliente não encontrado"));		
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente save(@RequestBody  @Valid Cliente cliente) {		 
		return clientes.save(cliente);
	}
	
	@DeleteMapping(value = "{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable  Integer id) {
		Optional<Cliente> cliente = clientes.findById(id);
		if (cliente.isPresent()) {
			clientes.delete(cliente.get());
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");
		}
	}
	
	@PutMapping(value = "{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void update(@PathVariable Integer id, @RequestBody @Valid Cliente cliente) {
		Optional<Cliente> clienteExistente = clientes.findById(id);
		if (clienteExistente.isPresent()) {
			cliente.setId(clienteExistente.get().getId());
			clientes.save(cliente);
		} else {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado");	
		}
		
	}
	
	@GetMapping
	public List<Cliente> find(Cliente filtro) {
		ExampleMatcher matcher = 
				ExampleMatcher
					.matching()
					.withIgnoreCase()
					.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher); 
		return clientes.findAll(example);
	}
	
}
