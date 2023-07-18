package com.algaworks.algalog.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.domain.model.Cliente;
import com.algaworks.algalog.domain.repository.ClienteRepository;
import com.algaworks.algalog.domain.service.CatalogoClienteService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
@AllArgsConstructor
@RestController
@RequestMapping("/clientes")
public class ClienteController {
// @PersistenceContext
//	private EntityManager manager; é uma interface do J.P que é usada para fazer
//	as operações com as entidades que sao refletidas depois do banco de dados.
//	(inserções, atualizações, etc 
 
 //NAO COLOCAMOS REGRA DE NEGOCIO NO CONTROLADOR
 
 @Autowired
 /*define que quero injetar uma instancia que ta sendo 
  * gerenciada pelo spring*/
	private ClienteRepository clienteRepository;
    private CatalogoClienteService catalogoClienteService;
	
public ClienteController(ClienteRepository clienteRepository, CatalogoClienteService catalogoClienteService) {
		super();
		this.clienteRepository = clienteRepository;
		this.catalogoClienteService = catalogoClienteService;
	}
@GetMapping
public List<Cliente> listar () {
	return clienteRepository.findAll();
//	return clienteRepository.findByNomeContaining("joao");
	//dentro do parametro colocar o que ira pesquisar
	
}
@GetMapping("/{clienteId}")
public ResponseEntity<Cliente> buscar(@PathVariable Long clienteId) {
	return clienteRepository.findById(clienteId)
			.map(cliente -> ResponseEntity.ok(cliente))
	        .orElse(ResponseEntity.notFound().build());
//	Optional <Cliente> cliente = clienteRepository.findById(clienteId);
//	if (cliente.isPresent()) {
//		return ResponseEntity.ok(cliente.get());
//	}
//	return ResponseEntity.notFound().build();
	//o orelse retorna o que tem dentro do optional se nao tiver nada retorna null
}
@PostMapping	
@ResponseStatus(HttpStatus.CREATED)
public Cliente adicionar(@Valid @RequestBody  Cliente cliente) {
	//return clienteRepository.save(cliente);
	return catalogoClienteService.salvar(cliente);
	//metodo para salvar/cadastrar um novo cliente (tem que fazer tb no postman em modelo JSON
}
@PutMapping("/{clienteId}")
public ResponseEntity<Cliente> atualizar(@PathVariable Long clienteId, @Valid @RequestBody Cliente cliente) {
 if (!clienteRepository.existsById(clienteId)) {
	 return ResponseEntity.notFound().build();
 }
 cliente.setId(clienteId);
 //cliente = clienteRepository.save(cliente);
 cliente = catalogoClienteService.salvar(cliente);
 //se deixar só o método save, vai criar um novo cliente, adicionando o comando da linha 72 força a apenas atualizar o cliente.
 return ResponseEntity.ok(cliente);
}

@DeleteMapping("/{clienteId}")
public ResponseEntity<Void> remover(@PathVariable Long clienteId) {
	 if (!clienteRepository.existsById(clienteId)) {
		 return ResponseEntity.notFound().build();
}
	 //clienteRepository.deleteById(clienteId);
	 catalogoClienteService.excluir(clienteId);
	 
	 return ResponseEntity.noContent().build();
}
}