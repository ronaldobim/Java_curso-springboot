package br.com.vendaspringboot.rest.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.vendaspringboot.model.Cliente;
import br.com.vendaspringboot.repository.ClienteRepository;

@Controller
public class ClienteControllerManual {
	
	private ClienteRepository clientes;
	
	public ClienteControllerManual(ClienteRepository clientes) {
		super();
		this.clientes = clientes;
	}



	@GetMapping(value = "/api2/clientes/{id}")
	@ResponseBody
	public ResponseEntity getClienteById(@PathVariable  Integer id) {
		Optional<Cliente> cliente = clientes.findById(id);
		if (cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PostMapping(value = "/api2/clientes")
	@ResponseBody
	public ResponseEntity save(@RequestBody  Cliente cliente) {
		Cliente clienteSalvo = clientes.save(cliente);
		return ResponseEntity.ok(clienteSalvo);
	}
	
	@DeleteMapping(value = "/api2/clientes/{id}")
	@ResponseBody
	public ResponseEntity delete(@PathVariable  Integer id) {
		Optional<Cliente> cliente = clientes.findById(id);
		if (cliente.isPresent()) {
			clientes.delete(cliente.get());
			return ResponseEntity.noContent().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@PutMapping(value = "/api2/clientes/{id}")
	@ResponseBody
	public ResponseEntity update(@PathVariable Integer id, @RequestBody  Cliente cliente) {
		Optional<Cliente> clienteExistente = clientes.findById(id);
		if (clienteExistente.isPresent()) {
			cliente.setId(clienteExistente.get().getId());
			clientes.save(cliente);
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}
	
	@GetMapping(value = "/api2/clientes")
	@ResponseBody
	public ResponseEntity find(Cliente filtro) {
		ExampleMatcher matcher = 
				ExampleMatcher
					.matching()
					.withIgnoreCase()
					.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
		Example example = Example.of(filtro, matcher);
		List<Cliente> lista = clientes.findAll(example);
		return ResponseEntity.ok(lista);
	}
	
}
