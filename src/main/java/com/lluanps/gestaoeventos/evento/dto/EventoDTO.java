package com.lluanps.gestaoeventos.evento.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.lluanps.gestaoeventos.despesa.dto.DespesaDTO;
import com.lluanps.gestaoeventos.produto.dto.ProdutoDTO;
import com.lluanps.gestaoeventos.receita.ReceitaDTO;
import com.lluanps.gestaoeventos.resumo.dto.ResumoDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EventoDTO {

    private Integer id;
    private String nome;
    private String descricao;
    private LocalDateTime dataEvento;

    private List<ReceitaDTO> receitas = new ArrayList<>();
    private List<DespesaDTO> despesas = new ArrayList<>();
    private List<ProdutoDTO> produtos = new ArrayList<>();
    private ResumoDTO resumo;

    public EventoDTO(Integer id, String nome, String descricao, LocalDateTime dataEvento) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataEvento = dataEvento;
    }
}