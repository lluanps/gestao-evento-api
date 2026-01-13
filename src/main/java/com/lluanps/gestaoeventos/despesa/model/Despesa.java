package com.lluanps.gestaoeventos.despesa.model;

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
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@Entity
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;
    private BigDecimal valor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties("despesas")      // CORRIGIDO: ignore a propriedade correta no Evento
    @EqualsAndHashCode.Exclude             // evita recursion em equals/hashCode
    @ToString.Exclude                      // evita recursion em toString()
    private Evento evento;
}