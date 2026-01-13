package com.lluanps.gestaoeventos.receita.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lluanps.gestaoeventos.evento.model.Evento;
import com.lluanps.gestaoeventos.evento.service.EventoService;
import com.lluanps.gestaoeventos.receita.ReceitaDTO;
import com.lluanps.gestaoeventos.receita.model.Receita;
import com.lluanps.gestaoeventos.receita.repository.ReceitaRepository;

@Service
public class ReceitaService {

	@Autowired
	private ReceitaRepository repository;
	
	@Autowired EventoService eventoService;
	
	public Receita cadastrarReceita(ReceitaDTO dto) {
		Receita receita = new Receita();
		
		Evento eventoId = eventoService.buscarPorId(dto.getEventoId());

		receita.setEvento(eventoId);
		receita.setDescricao(dto.getDescricao());
		receita.setValor(dto.getValor());
		return repository.save(receita);
	}

	public BigDecimal getTotalReceitaPorEventoId(Integer id) {
		Evento evento = eventoService.buscarPorId(id);
		
		return repository.getTotalReceitaByEventoId(evento.getId());
	}
	
}
