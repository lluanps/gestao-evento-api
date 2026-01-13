package com.lluanps.gestaoeventos.receita.controller;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lluanps.gestaoeventos.receita.ReceitaDTO;
import com.lluanps.gestaoeventos.receita.model.Receita;
import com.lluanps.gestaoeventos.receita.service.ReceitaService;

@RestController
@RequestMapping("/receita")
public class ReceitaController {

	@Autowired
	private ReceitaService service;
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Receita salvarReceita(@RequestBody ReceitaDTO dto) {
		return service.cadastrarReceita(dto);
	}
	
	@GetMapping("total-receita/{eventoId}")
	public BigDecimal getTotalReceitaPorEventoId(@PathVariable Integer eventoId) {
		return service.getTotalReceitaPorEventoId(eventoId);
	}
	
	
}
