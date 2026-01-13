package com.lluanps.gestaoeventos.evento.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.lluanps.gestaoeventos.despesa.model.Despesa;
import com.lluanps.gestaoeventos.produto.model.Produto;
import com.lluanps.gestaoeventos.receita.model.Receita;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Evento {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Integer id;
    
    private String nome;
    private String descricao;
    private LocalDateTime dataEvento;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("evento")
    private Set<Receita> receitas = new HashSet<>();

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Despesa> despesas = new HashSet<>();


    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("evento")
    private Set<Produto> produtos = new HashSet<>();

    // Conveniência para manter relacionamento bidirecional
    public void addReceita(Receita r) {
        receitas.add(r);
        if (r != null) r.setEvento(this);
    }

    public void removeReceita(Receita r) {
        receitas.remove(r);
        if (r != null) r.setEvento(null);
    }

    public void addDespesa(Despesa d) {
        despesas.add(d);
        if (d != null) d.setEvento(this);
    }

    public void removeDespesa(Despesa d) {
        despesas.remove(d);
        if (d != null) d.setEvento(null);
    }

    public void addProduto(Produto p) {
        produtos.add(p);
        if (p != null) p.setEvento(this);
    }

    public void removeProduto(Produto p) {
        produtos.remove(p);
        if (p != null) p.setEvento(null);
    }
}