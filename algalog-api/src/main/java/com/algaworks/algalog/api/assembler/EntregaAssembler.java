package com.algaworks.algalog.api.assembler;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.algaworks.algalog.domain.model.Entrega;
import com.algaworks.algalog.domain.model.EntregaModel;
import com.algaworks.algalog.domain.model.input.EntregaInput;


@Component
public class EntregaAssembler {

	public EntregaAssembler(ModelMapper modelMapper) {
		this.modelMapper = modelMapper;
	}
	private ModelMapper modelMapper;
	public EntregaModel toModel(Entrega entrega) {
	    return modelMapper.map(entrega, EntregaModel.class);
	}

	public List<EntregaModel> toCollectionModel(List<Entrega> entregas) {
		return entregas.stream()
		.map(this::toModel)
		.collect(Collectors.toList());
	}
	
	public Entrega toEntity(EntregaInput entregaInput ) {
		return modelMapper.map(entregaInput, Entrega.class);
	}
	
}
