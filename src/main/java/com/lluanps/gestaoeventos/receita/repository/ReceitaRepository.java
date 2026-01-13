package com.lluanps.gestaoeventos.receita.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lluanps.gestaoeventos.receita.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Integer> {
	
	@Query("SELECT COALESCE(SUM(r.valor), 0) FROM Receita r where r.evento.id = :eventoId")
	public BigDecimal getTotalReceitaByEventoId(Integer eventoId);
	
}
