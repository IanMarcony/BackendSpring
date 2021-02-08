package com.marcony.osmarcony.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcony.osmarcony.domain.exception.AppException;
import com.marcony.osmarcony.domain.model.Cliente;
import com.marcony.osmarcony.domain.repository.ClienteRepository;

@Service
public class CadastroClienteService {
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public Cliente salvar(Cliente cliente) {
		Cliente clienteExists = clienteRepository.findByEmail(cliente.getEmail());
		
		if(clienteExists !=null && !clienteExists.equals(cliente)) {
			throw new AppException("Já existe um cliente cadastrado com este e-mail.");
		}
		
		
		return clienteRepository.save(cliente);
	}
	
	public void excluir(Long clienteId) {
		clienteRepository.deleteById(clienteId);
	}
	
}
