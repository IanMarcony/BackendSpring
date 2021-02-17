package com.marcony.osmarcony.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marcony.osmarcony.domain.model.Comentario;

public interface ComentarioRepository extends  JpaRepository<Comentario, Long> {

}
