package com.lluanps.gestaoeventos.despesa.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.lluanps.gestaoeventos.despesa.model.Despesa;

public interface DespesaRepository extends JpaRepository<Despesa, Integer> {
	
	@Query("SELECT COALESCE(SUM(d.valor), 0) FROM Despesa d WHERE d.evento.id = :eventoId")
	public BigDecimal getTotalDespesaByEventoId(Integer eventoId);
	
}
