package com.marcony.osmarcony.api.controller;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.servlet.annotation.HttpMethodConstraint;
import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.marcony.osmarcony.api.model.ComentarioInput;
import com.marcony.osmarcony.api.model.ComentarioModel;
import com.marcony.osmarcony.domain.exception.EntityNotFoundException;
import com.marcony.osmarcony.domain.model.Comentario;
import com.marcony.osmarcony.domain.model.OrdemServico;
import com.marcony.osmarcony.domain.repository.OrdemServicoRepository;
import com.marcony.osmarcony.domain.service.GestaoOrdemServicoService;

@RestController
@RequestMapping("/ordens-servico/{ordemServicoId}/comentarios")
public class ComentarioController {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private GestaoOrdemServicoService gestaoOrdemServico;
	
	@Autowired
	private OrdemServicoRepository ordemServicoRepository;
	
	@GetMapping
	public List<ComentarioModel>  listar(@PathVariable Long ordemServicoId){
		OrdemServico ordemServico =ordemServicoRepository.findById(ordemServicoId)
				.orElseThrow(()->new EntityNotFoundException("Ordem de Servico não encontrado"));
		
		
		return toCollectionModel(ordemServico.getComentarios());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ComentarioModel adicionar( @PathVariable Long ordemServicoId,  @Valid @RequestBody ComentarioInput comentarioInput) {
		Comentario comentario = gestaoOrdemServico.adicionarComentario(ordemServicoId, comentarioInput.getDescricao());
		
		
		return toModel(comentario);
	}
	
	
	private ComentarioModel  toModel(Comentario comentario) {
		return modelMapper.map(comentario,ComentarioModel.class);
	}
	
	
	private List<ComentarioModel>  toCollectionModel(List<Comentario> comentarios){
		
		return comentarios.stream().map(comentario -> toModel(comentario))
				.collect(Collectors.toList());
		
	}
	
	
}
