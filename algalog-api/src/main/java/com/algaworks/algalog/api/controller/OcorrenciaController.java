package com.algaworks.algalog.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.algaworks.algalog.api.assembler.OcorrenciaAssembler;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.Ocorrencia;
import com.algaworks.algalog.domain.model.OcorrenciaModel;
import com.algaworks.algalog.domain.model.input.OcorrenciaInput;
import com.algaworks.algalog.domain.service.BuscaEntregaService;
import com.algaworks.algalog.domain.service.RegistroOcorrenciaService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/entregas/{entregaId}/ocorrencias") //referente ao caminho que coloca no postman que fica localhost:8080/entregas/1/ocorrencias
public class OcorrenciaController {
	
    private BuscaEntregaService buscaEntregaService;
	private RegistroOcorrenciaService registroOcorrenciaService;
	private OcorrenciaAssembler ocorrenciaAssembler;
	
	
	
	public OcorrenciaController(RegistroOcorrenciaService registroOcorrenciaService,
			OcorrenciaAssembler ocorrenciaAssembler, BuscaEntregaService buscaEntregaService) {
		this.registroOcorrenciaService = registroOcorrenciaService;
		this.ocorrenciaAssembler = ocorrenciaAssembler;
		this.setBuscaEntregaService(buscaEntregaService);
	}



	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public OcorrenciaModel registrar (@PathVariable Long entregaId, @Valid @RequestBody OcorrenciaInput ocorrenciaInput) {
		Ocorrencia ocorrenciaRegistrada = registroOcorrenciaService.registrar(entregaId, ocorrenciaInput.getDescricao());
		return ocorrenciaAssembler.toModel(ocorrenciaRegistrada);
	}

	@GetMapping
	public java.util.List<OcorrenciaModel> listar (@PathVariable Long entregaId) {
		Entrega entrega = buscaEntregaService.buscar(entregaId);
		
		return ocorrenciaAssembler.toCollectionModel(entrega.getOcorrencias());
	}



	public BuscaEntregaService getBuscaEntregaService() {
		return buscaEntregaService;
	}



	public void setBuscaEntregaService(BuscaEntregaService buscaEntregaService) {
		this.buscaEntregaService = buscaEntregaService;
	}
	
}
