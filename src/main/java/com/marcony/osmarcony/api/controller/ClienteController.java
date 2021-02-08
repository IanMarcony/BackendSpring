package com.marcony.osmarcony.api.controller;

import java.util.List;
import java.util.Optional;


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

import com.marcony.osmarcony.api.exceptionhandler.ApiExceptionHandler;
import com.marcony.osmarcony.domain.model.Cliente;
import com.marcony.osmarcony.domain.repository.ClienteRepository;
import com.marcony.osmarcony.domain.service.CadastroClienteService;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
	
	@Autowired
	private ClienteRepository clienteRepository;	
	
	@Autowired
	private CadastroClienteService cadastroClienteService;
	
	@GetMapping
	public List<Cliente> index() {			
		return clienteRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<Cliente> find(@PathVariable Long id) {
		Optional<Cliente> cliente=  clienteRepository.findById(id);
		
		if(cliente.isPresent()) {
			return ResponseEntity.ok(cliente.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente add(  @RequestBody  Cliente  cliente) {
		return cadastroClienteService.salvar(cliente);
		
	}
	 
	@PutMapping("/{id}")
	public ResponseEntity<Cliente> update( @PathVariable Long id,@RequestBody Cliente cliente) {
		if(!clienteRepository.existsById(id)) {
			return ResponseEntity.notFound().build();
		}
		cliente.setId(id);
		cliente = cadastroClienteService.salvar(cliente);
		
		
		return ResponseEntity.ok(cliente);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		if(!clienteRepository.existsById(id))return ResponseEntity.notFound().build();
		cadastroClienteService.excluir(id);
		
		return ResponseEntity.noContent().build();
		
	}
}
