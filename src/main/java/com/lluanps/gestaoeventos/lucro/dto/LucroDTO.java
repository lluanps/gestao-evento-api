package com.lluanps.gestaoeventos.lucro.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LucroDTO {

    private Integer eventoId;
    private BigDecimal lucro;
    
	public LucroDTO(Integer eventoId, BigDecimal lucro) {
		this.eventoId = eventoId;
		this.lucro = lucro;
	}

}
