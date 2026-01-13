package com.lluanps.gestaoeventos.despesa.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lluanps.gestaoeventos.despesa.dto.DespesaDTO;
import com.lluanps.gestaoeventos.despesa.model.Despesa;
import com.lluanps.gestaoeventos.despesa.repository.DespesaRepository;
import com.lluanps.gestaoeventos.evento.model.Evento;
import com.lluanps.gestaoeventos.evento.service.EventoService;

@Service
public class DespesaService {

	@Autowired
	public DespesaRepository repository;
	
	@Autowired
	private EventoService eventoService;
	
	public Despesa cadastrarDespesa(DespesaDTO dto) {
		Despesa despesa = new Despesa();
		
		Evento eventoId = eventoService.buscarPorId(dto.getEventoId());
		
		despesa.setDescricao(dto.getDescricao());
		despesa.setEvento(eventoId);
		despesa.setValor(dto.getValor());
		
		return repository.save(despesa);
	}
	

	public BigDecimal getTotalDespesaPorEventoId(Integer eventoId) {
		return repository.getTotalDespesaByEventoId(eventoId);
	}
	
}