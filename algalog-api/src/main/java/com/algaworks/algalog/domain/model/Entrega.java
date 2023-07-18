package com.algaworks.algalog.domain.model;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.algaworks.algalog.domain.exception.NegocioException;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

@Entity
public class Entrega {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@ManyToOne  //É o mapeamento de um relacionamento entre entrega e cliente do tipo muitos para um (muitas entregas possuem um cliente)
	private Cliente cliente;
	
	@Embedded //abstrair os dados para outra classe porém mapeando para a mesma tabela
	
	private Destinatario destinatario; 
	
	@NotNull
	private BigDecimal taxa;
	
	@OneToMany(mappedBy = "entrega", cascade = CascadeType.ALL) //recebe o lado inverso 
	private List<Ocorrencia> Ocorrencias = new ArrayList<>();
	
	@Enumerated(EnumType.STRING) //armazenar na colunaa a string que representa a constante da enumeração
	private StatusEntrega status;
	
	
	private OffsetDateTime dataPedido;
	
//	@JsonProperty(access = Access.READ_ONLY) //somente leitura o usuario n pode inserir/alterar
	private OffsetDateTime dataFinalizacao;
	
	public List<Ocorrencia> getOcorrencias() {
		return Ocorrencias;
	}
	public void setOcorrencias(List<Ocorrencia> ocorrencias) {
		Ocorrencias = ocorrencias;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Destinatario getDestinatario() {
		return destinatario;
	}
	public void setDestinatario(Destinatario destinatario) {
		this.destinatario = destinatario;
	}
	public BigDecimal getTaxa() {
		return taxa;
	}
	public void setTaxa(BigDecimal taxa) {
		this.taxa = taxa;
	}
	public StatusEntrega getStatus() {
		return status;
	}
	public void setStatus(StatusEntrega status) {
		this.status = status;
	}
	public OffsetDateTime getDataPedido() {
		return dataPedido;
	}
	public void setDataPedido(OffsetDateTime dataPedido) {
		this.dataPedido = dataPedido;
	}
	public OffsetDateTime getDataFinalizacao() {
		return dataFinalizacao;
	}
	public void setDataFinalizacao(OffsetDateTime dataFinalizacao) {
		this.dataFinalizacao = dataFinalizacao;
	}
	@Override
	public int hashCode() {
		return Objects.hash(cliente, dataFinalizacao, dataPedido, destinatario, id, status, taxa);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entrega other = (Entrega) obj;
		return Objects.equals(cliente, other.cliente) && Objects.equals(dataFinalizacao, other.dataFinalizacao)
				&& Objects.equals(dataPedido, other.dataPedido) && Objects.equals(destinatario, other.destinatario)
				&& Objects.equals(id, other.id) && status == other.status && Objects.equals(taxa, other.taxa);
	}
	public Ocorrencia adicionarOcorrencia(String descricao) {
		Ocorrencia ocorrencia = new Ocorrencia();
		ocorrencia.setDescricao(descricao);
		ocorrencia.setDataRegistro(OffsetDateTime.now());
		ocorrencia.setEntrega(this);
		
		this.getOcorrencias().add(ocorrencia);
		
		return ocorrencia;
		
	}
	public void finalizar() {
		if (naoPodeSerFinalizada()) {
			throw new NegocioException("ENTREGA NAO PODE SER FINALIZADA!!");
		}
		setStatus(StatusEntrega.FINALIZADA);
		setDataFinalizacao(OffsetDateTime.now());
		
	}
	
	public boolean podeSerFinalizada() {
		return StatusEntrega.PENDENTE.equals(getStatus());
	}
	public boolean naoPodeSerFinalizada() {
		return !podeSerFinalizada();
		}
	
	
}
