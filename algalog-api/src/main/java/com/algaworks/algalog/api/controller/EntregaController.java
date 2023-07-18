package com.algaworks.algalog.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algalog.api.assembler.EntregaAssembler;
import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.EntregaModel;
import com.algaworks.algalog.domain.model.input.EntregaInput;
import com.algaworks.algalog.domain.repository.EntregaRepository;
import com.algaworks.algalog.domain.service.FinalizacaoEntregaService;
import com.algaworks.algalog.domain.service.SolicitacaoEntregaService;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/entregas")
public class EntregaController {



	public EntregaController(EntregaRepository entregaRepository, SolicitacaoEntregaService solicitacaoEntregaService,
			EntregaAssembler entregaAssembler, FinalizacaoEntregaService finalizacaoEntregaService) {
		super();
		this.entregaRepository = entregaRepository;
		this.solicitacaoEntregaService = solicitacaoEntregaService;
		this.entregaAssembler = entregaAssembler;
		this.finalizacaoEntregaService = finalizacaoEntregaService;
	}
	private EntregaRepository entregaRepository;
	private SolicitacaoEntregaService solicitacaoEntregaService;
	private EntregaAssembler entregaAssembler;
	private FinalizacaoEntregaService finalizacaoEntregaService;

	@PostMapping 
	@ResponseStatus (HttpStatus.CREATED)
	public EntregaModel solicitar (@Valid @RequestBody EntregaInput entregaInput) {
		Entrega novaEntrega = entregaAssembler.toEntity(entregaInput);
		Entrega entregaSolicitada = solicitacaoEntregaService.solicitar(novaEntrega);
		return entregaAssembler.toModel(entregaSolicitada);
	}
	@GetMapping
	public List<EntregaModel> listar () {
		return entregaAssembler.toCollectionModel(entregaRepository.findAll());
	}
	@GetMapping("/{entregaId}")
	public ResponseEntity<EntregaModel> buscar (@PathVariable Long entregaId) {
		return entregaRepository.findById(entregaId)
				.map(entrega -> ResponseEntity.ok(entregaAssembler.toModel(entrega)))
				.orElse(ResponseEntity.notFound().build());
					
					// .map =(objeto, tipo de destino que quer ou seja pra qual o tipo quer que converta) 
					
					// o código acima substitui todo o codigo abaixo que está como comentário 
					
			//		EntregaModel entregaModel = new EntregaModel(null, null, null, null, null, null, null);
				//	entregaModel.setId(entrega.getId());
					//entregaModel.setNomeCliente(entrega.getDestinatario().getNome());
					//entregaModel.setDestinatario(new DestinatarioModel(null, null, null, null, null));
					//entregaModel.getDestinatario().setNome(entrega.getDestinatario().getNome());
					//entregaModel.getDestinatario().setLogradouro(entrega.getDestinatario().getLogradouro());
					//entregaModel.getDestinatario().setNumero(entrega.getDestinatario().getNumero());
					///entregaModel.getDestinatario().setComplemento(entrega.getDestinatario().getComplemento());
					//entregaModel.getDestinatario().setBairro(entrega.getDestinatario().getBairro());
					//entregaModel.setTaxa(entrega.getTaxa());
					//entregaModel.setDataPedido(entrega.getDataPedido());
					//entregaModel.setDataFinalizacao(entrega.getDataFinalizacao());
					
	}
	
	@PutMapping("/{entregaId}/finalizacao")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void finalizar(@PathVariable Long entregaId) {
		finalizacaoEntregaService.finalizar(entregaId);
		
	}
	public FinalizacaoEntregaService getFinalizacaoEntregaService() {
		return finalizacaoEntregaService;
	}
	public void setFinalizacaoEntregaService(FinalizacaoEntregaService finalizacaoEntregaService) {
		this.finalizacaoEntregaService = finalizacaoEntregaService;
	}
}