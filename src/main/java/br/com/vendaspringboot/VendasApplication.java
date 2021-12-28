package br.com.vendaspringboot;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.vendaspringboot.model.Cliente;
import br.com.vendaspringboot.model.Pedido;
import br.com.vendaspringboot.model.Produto;
import br.com.vendaspringboot.repository.ClienteRepository;
import br.com.vendaspringboot.repository.PedidoRepository;
import br.com.vendaspringboot.repository.ProdutoRepository;


@SpringBootApplication
@RestController
public class VendasApplication {

	@Autowired
	@Qualifier(value = "nomeAplicacao")
	private String nomeAplicacao;
	@Value("${desenvolvedor}")
	private String desenvolvedor;

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);

	}

	@GetMapping("/olamundo")
	public String olaMundo() {
		return "Olá mundo spring boot, nome da aplicação: " + nomeAplicacao + " Desenvolvido por: " + desenvolvedor;
	}
	
	@Bean
	public CommandLineRunner init(@Autowired ClienteRepository clientes, 
			@Autowired PedidoRepository pedidos, 
			@Autowired ProdutoRepository produtos) {
		return args -> {
			
			  Cliente cliente = new Cliente(); cliente.setNome("Ronaldo");
			  clientes.save(cliente); Cliente ronaldo = cliente; cliente = new Cliente();
			  cliente.setNome("Franciele"); clientes.save(cliente); 
			  //pesquisando cliente  que contenha a palavra aldo 
			  List<Cliente> listaClientes = clientes.findByNomeIgnoreCaseContaining("ALDO"); 
			  listaClientes = clientes.encontrarPorNomeSQLNativo("aldo");
			  System.out.println("Pesquisando clientes pela palavra aldo.."); 
			  for (Cliente cli : listaClientes) { 
				  System.out.println("..."+cli.getNome()); 
			  }
			  //pesquisando todos os clientes listaClientes = clientes.findAll();
			  System.out.println("Pesquisando todos clientes.."); 
			  for (Cliente cli : listaClientes) { 
				  System.out.println("..."+cli.getNome()); 
			  } 
			  //pesquisando  todos os cliente apos delear ronaldo 
			  clientes.delete(ronaldo); 
			  listaClientes = clientes.findAll();
			  System.out.println("Pesquisando todos os clientes apos deletar Ronaldo..");
			  for (Cliente cli : listaClientes) { 
				  System.out.println("..."+cli.getNome());
			  } 
			  //testa exists 
			  if (clientes.existsByNome("Franciele")) {
				  System.out.println("Exists cliente com nome Franciele"); 
			  } else {
				  System.out.println("NÃO Exists cliente com nome Franciele"); 
			  } 
			  
			  //salva um produto
			  Produto produto = new Produto();
			  produto.setDescricao("coca cola");
			  produto.setPreco(BigDecimal.valueOf(1.99));
			  produtos.save(produto);

			  /*//cria um novo cliente para gravar um pedido
			  System.out.println("Criando cliente fulano");
			  Cliente fulano = new Cliente();
			  fulano.setNome("Fulano");
			  clientes.save(fulano);
			  System.out.println("fulano com id = "+fulano.getId());
			  Pedido pedido = new Pedido();
			  pedido.setCliente(fulano);
			  pedido.setDataPedido(LocalDate.now());
			  pedido.setTotal(BigDecimal.valueOf(1.99));
			  pedidos.save(pedido);
			  
			  pedido = new Pedido();
			  pedido.setCliente(fulano);
			  pedido.setDataPedido(LocalDate.now());
			  pedido.setTotal(BigDecimal.valueOf(2.99));
			  pedidos.save(pedido);			  
			  
			  System.out.println("Gravou pedido");
			  List<Pedido> listaPedidos = pedidos.findByCliente(fulano);
			  System.out.println("Listando pedidos do cliente fulano");
			  for (Pedido p : listaPedidos) {
				System.out.println(p);
			  }*/
			  
		};
	}	

}
