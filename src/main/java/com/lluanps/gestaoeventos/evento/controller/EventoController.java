package com.lluanps.gestaoeventos.evento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.lluanps.gestaoeventos.evento.dto.EventoDTO;
import com.lluanps.gestaoeventos.evento.model.Evento;
import com.lluanps.gestaoeventos.evento.service.EventoService;
import com.lluanps.gestaoeventos.lucro.dto.LucroDTO;
import com.lluanps.gestaoeventos.resumo.dto.ResumoDTO;

@RestController
@RequestMapping("/evento")
public class EventoController {
    
    @Autowired
    private EventoService service;
    
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public EventoDTO salvarEvento(@RequestBody EventoDTO dto) {
        Evento saved = service.salvarEvento(dto);
        return service.buscarDTOPorId(saved.getId());
    }
    
    @GetMapping("/{eventoId}")
    public EventoDTO getEventoId(@PathVariable Integer eventoId) {
        return service.buscarDTOPorId(eventoId);
    }
    
    @GetMapping("/{eventoId}/lucro")
    public LucroDTO getLucroPorEventoId(@PathVariable Integer eventoId) {
        return service.getLucroPorEventoId(eventoId);
    }
    
    @GetMapping
    public List<EventoDTO> listarEventos() {
        return service.listarEventos();
    }
    
    @GetMapping("/{eventoId}/resumo")
    public ResumoDTO getResumoPorEventoId(@PathVariable Integer eventoId) {
        return service.getResumoPorEventoId(eventoId);
    }

}