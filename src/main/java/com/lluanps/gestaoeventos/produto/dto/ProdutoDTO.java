package com.lluanps.gestaoeventos.produto.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoDTO {

    private Integer id;
    private String nome;
    private Integer quantidadeInicial;
    private Integer quantidadeVendida;
    private BigDecimal valorProduto;
    private Integer eventoId;

    public ProdutoDTO(Integer id, String nome, Integer quantidadeInicial, Integer quantidadeVendida, 
    		BigDecimal valorProduto, Integer eventoId) {
        this.id = id;
        this.nome = nome;
        this.quantidadeInicial = quantidadeInicial;
        this.quantidadeVendida = quantidadeVendida;
        this.valorProduto = valorProduto;
        this.eventoId = eventoId;
    }
}
