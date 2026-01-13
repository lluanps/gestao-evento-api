package com.lluanps.gestaoeventos.produto.model;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lluanps.gestaoeventos.evento.model.Evento;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer id;
	
	private String nome;
	private Integer quantidadeInicial;
	private Integer quantidadeVendida;
	private BigDecimal valorProduto;
	private BigDecimal totalVendido = BigDecimal.ZERO;
	
	@ManyToOne(fetch = FetchType.LAZY)
	private Evento evento;

	public BigDecimal getTotalVendido() {
	    return (totalVendido != null) ? totalVendido : BigDecimal.ZERO;
	}

	public void setTotalVendido(BigDecimal totalVendido) {
	    this.totalVendido = totalVendido;
	}

	
	public Integer getQuantidadeRestante() {
	    int inicial = (quantidadeInicial != null) ? quantidadeInicial : 0;
	    int vendida = (quantidadeVendida != null) ? quantidadeVendida : 0;
	    return inicial - vendida;
	}

}
