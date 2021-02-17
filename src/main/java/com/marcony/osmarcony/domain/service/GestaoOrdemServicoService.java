package com.marcony.osmarcony.domain.service;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marcony.osmarcony.api.model.OrdemServicoInput;
import com.marcony.osmarcony.domain.exception.AppException;
import com.marcony.osmarcony.domain.exception.EntityNotFoundException;
import com.marcony.osmarcony.domain.model.Cliente;
import com.marcony.osmarcony.domain.model.Comentario;
import com.marcony.osmarcony.domain.model.OrdemServico;
import com.marcony.osmarcony.domain.model.StatusOrdemServico;
import com.marcony.osmarcony.domain.repository.ClienteRepository;
import com.marcony.osmarcony.domain.repository.ComentarioRepository;
import com.marcony.osmarcony.domain.repository.OrdemServicoRepository;

@Service
public class GestaoOrdemServicoService {
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ComentarioRepository comentarioRepository;
	
	public OrdemServico criar(OrdemServico ordemServico) {
		
		Cliente cliente = clienteRepository.findById(ordemServico.getCliente().getId())
				.orElseThrow(()->new AppException("Cliente não encontrado"));
		ordemServico.setCliente(cliente);
		ordemServico.setStatus(StatusOrdemServico.ABERTA);
		ordemServico.setDataAbertura(OffsetDateTime.now());
				
		return ordemServicoRepository.save(ordemServico); 
	}
	
	public void finalizar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
	
		ordemServico.finalizar();
		
		ordemServicoRepository.save(ordemServico);
		
	}
	
	public void cancelar(Long ordemServicoId) {
		OrdemServico ordemServico = buscar(ordemServicoId);
	
		ordemServico.cancelar();
		
		ordemServicoRepository.save(ordemServico);
		
	}

	private OrdemServico buscar(Long ordemServicoId) {
		return ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(()-> new EntityNotFoundException("Ordem de Serviço não encontrada"));
	}
	
	public Comentario adicionarComentario(Long ordemServicoId, String descricao) {
		
		OrdemServico ordemServico = buscar(ordemServicoId);
		
		Comentario comentario = new Comentario(descricao,ordemServico,OffsetDateTime.now());
		
		return comentarioRepository.save(comentario);
	}
}
