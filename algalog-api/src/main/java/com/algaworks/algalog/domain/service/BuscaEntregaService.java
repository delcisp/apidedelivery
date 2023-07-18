package com.algaworks.algalog.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.algalog.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;
@Service
public class BuscaEntregaService {

	public BuscaEntregaService(EntregaRepository entregaRepository) {
		this.entregaRepository = entregaRepository;
	}

	private EntregaRepository entregaRepository;
	
	public Entrega buscar(Long entregaId) {
		return entregaRepository.findById(entregaId)
	    		   .orElseThrow(() -> new EntidadeNaoEncontradaException("ENTREGA NAO ENCONTRADA!!"));
	}
	
}
