package com.algaworks.algalog.domain.service;

import org.springframework.stereotype.Service;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.Ocorrencia;

import jakarta.transaction.Transactional;
@Service
public class RegistroOcorrenciaService {
	
	private BuscaEntregaService buscaEntregaService;
	
	
	
	public RegistroOcorrenciaService(BuscaEntregaService buscaEntregaService) {
		this.buscaEntregaService = buscaEntregaService;
	}


@Transactional
	public Ocorrencia registrar (Long entregaId, String descricao) {
       Entrega entrega = buscaEntregaService.buscar(entregaId);
    return entrega.adicionarOcorrencia(descricao);   
	}
 	
}
