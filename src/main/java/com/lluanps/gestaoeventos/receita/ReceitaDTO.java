package com.lluanps.gestaoeventos.receita;


import java.math.BigDecimal;

import com.lluanps.gestaoeventos.receita.model.Receita;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceitaDTO {

    private Integer id;
    private String descricao;
    private BigDecimal valor;
    private Integer eventoId;

    public static ReceitaDTO fromEntity(Receita r) {
        if (r == null) return null;
        return new ReceitaDTO(r.getId(), r.getDescricao(), r.getValor(),
                              r.getEvento() != null ? r.getEvento().getId() : null);
    }
}