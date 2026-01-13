package com.lluanps.gestaoeventos.despesa.dto;

import java.math.BigDecimal;

import com.lluanps.gestaoeventos.evento.model.Evento;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DespesaDTO {
	
	private Integer id;
    private String descricao;
    private BigDecimal valor;
    private Integer eventoId;
    
	public DespesaDTO(Integer id, String descricao, BigDecimal valor, Evento evento) {
		this.descricao = descricao;
		this.valor = valor;
		this.eventoId = evento.getId();
	}
    
}
