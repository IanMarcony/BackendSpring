package com.marcony.osmarcony.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcony.osmarcony.domain.exception.AppException;
import com.marcony.osmarcony.domain.model.Cliente;
import com.marcony.osmarcony.domain.model.OrdemServico;
import com.marcony.osmarcony.domain.model.StatusOrdemServico;
import com.marcony.osmarcony.domain.repository.ClienteRepository;
import com.marcony.osmarcony.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(()->new AppException("Cliente não encontrado"));
		
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(LocalDateTime.now());
				
		return ordemServicoRepository.save(ordemServico); 
	}
}
