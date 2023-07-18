package com.algaworks.algalog.domain.service;

import org.springframework.stereotype.Service;

import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.repository.EntregaRepository;

import jakarta.transaction.Transactional;

@Service
public class FinalizacaoEntregaService {

	private BuscaEntregaService buscaEntregaService;
	private EntregaRepository entregaRepository;
	
	public FinalizacaoEntregaService(BuscaEntregaService buscaEntregaService, EntregaRepository entregaRepository) {
		this.buscaEntregaService = buscaEntregaService;
		this.entregaRepository = entregaRepository;
	}

	@Transactional
	public void finalizar (Long entregaId) {
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		
	entrega.finalizar();
	
		entregaRepository.save(entrega);
		
	}
}
