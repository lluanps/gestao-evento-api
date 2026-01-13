package com.lluanps.gestaoeventos.evento.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lluanps.gestaoeventos.api.exceptions.NotFoundException;
import com.lluanps.gestaoeventos.despesa.dto.DespesaDTO;
import com.lluanps.gestaoeventos.despesa.repository.DespesaRepository;
import com.lluanps.gestaoeventos.evento.dto.EventoDTO;
import com.lluanps.gestaoeventos.evento.model.Evento;
import com.lluanps.gestaoeventos.lucro.dto.LucroDTO;
import com.lluanps.gestaoeventos.produto.dto.ProdutoDTO;
import com.lluanps.gestaoeventos.produto.model.Produto;
import com.lluanps.gestaoeventos.receita.ReceitaDTO;
import com.lluanps.gestaoeventos.receita.repository.ReceitaRepository;
import com.lluanps.gestaoeventos.repository.EventoRepository;
import com.lluanps.gestaoeventos.resumo.dto.ResumoDTO;

@Service
public class EventoService {

    @Autowired
    private EventoRepository repository;

    @Autowired
    private ReceitaRepository receitaRepository;

    @Autowired
    private DespesaRepository despesaRepository;

    public Evento salvarEvento(EventoDTO dto) {
        Evento evento = new Evento();
        evento.setNome(dto.getNome());
        evento.setDescricao(dto.getDescricao());
        evento.setDataEvento(dto.getDataEvento());
        return repository.save(evento);
    }

    public Evento buscarPorId(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new NotFoundException("Evento não encontrado com o id: " + id));
    }

    @Transactional(readOnly = true)
    public List<EventoDTO> listarEventos() {
        return repository.findAllWithRelations()
            .stream()
            .map(this::mapToDTO)
            .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EventoDTO buscarDTOPorId(Integer id) {
        Evento evento = repository.findByIdWithRelations(id)
            .orElseThrow(() -> new NotFoundException("Evento não encontrado com o id: " + id));
        return mapToDTO(evento);
    }

    @Transactional(readOnly = true)
    public LucroDTO getLucroPorEventoId(Integer eventoId) {
        BigDecimal totalReceitaByEventoId = receitaRepository.getTotalReceitaByEventoId(eventoId);
        BigDecimal totalDespesaByEventoId = despesaRepository.getTotalDespesaByEventoId(eventoId);

        totalReceitaByEventoId = totalReceitaByEventoId == null ? BigDecimal.ZERO : totalReceitaByEventoId;
        totalDespesaByEventoId = totalDespesaByEventoId == null ? BigDecimal.ZERO : totalDespesaByEventoId;

        BigDecimal totalVendasProdutos = repository.getProdutosByEventoId(eventoId)
            .stream()
            .map(p -> p.getTotalVendido() == null ? BigDecimal.ZERO : p.getTotalVendido())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal receitaTotal = totalReceitaByEventoId.add(totalVendasProdutos);
        BigDecimal lucroEvento = receitaTotal.subtract(totalDespesaByEventoId);

        return new LucroDTO(eventoId, lucroEvento);
    }

    // ---------- Helpers ----------
    private EventoDTO mapToDTO(Evento e) {
        EventoDTO dto = new EventoDTO();
        dto.setId(e.getId());
        dto.setNome(e.getNome());
        dto.setDescricao(e.getDescricao());
        dto.setDataEvento(e.getDataEvento());

        // receitas
        dto.setReceitas((e.getReceitas() == null ? List.of() : e.getReceitas()).stream()
            .filter(obj -> obj instanceof com.lluanps.gestaoeventos.receita.model.Receita)
            .map(obj -> {
                com.lluanps.gestaoeventos.receita.model.Receita r =
                    (com.lluanps.gestaoeventos.receita.model.Receita) obj;
                ReceitaDTO rd = new ReceitaDTO();
                rd.setId(r.getId());
                rd.setDescricao(r.getDescricao());
                rd.setValor(r.getValor());
                rd.setEventoId(r.getEvento() != null ? r.getEvento().getId() : null);
                return rd;
            }).collect(Collectors.toList()));

        // despesas
        dto.setDespesas((e.getDespesas() == null ? List.of() : e.getDespesas()).stream()
            .filter(obj -> obj instanceof com.lluanps.gestaoeventos.despesa.model.Despesa)
            .map(obj -> {
                com.lluanps.gestaoeventos.despesa.model.Despesa d =
                    (com.lluanps.gestaoeventos.despesa.model.Despesa) obj;
                DespesaDTO dd = new DespesaDTO();
                dd.setId(d.getId());
                dd.setDescricao(d.getDescricao());
                dd.setValor(d.getValor());
                dd.setEventoId(d.getEvento() != null ? d.getEvento().getId() : null);
                return dd;
            }).collect(Collectors.toList()));

        // produtos
        dto.setProdutos((e.getProdutos() == null ? List.of() : e.getProdutos()).stream()
            .filter(obj -> obj instanceof Produto)
            .map(obj -> {
                Produto p =
                    (Produto) obj;
                ProdutoDTO pd = new ProdutoDTO();
                pd.setId(p.getId());
                pd.setNome(p.getNome());
                pd.setQuantidadeInicial(p.getQuantidadeInicial());
                pd.setQuantidadeVendida(p.getQuantidadeVendida());
                pd.setValorProduto(p.getValorProduto());
                pd.setEventoId(p.getEvento() != null ? p.getEvento().getId() : null);
                return pd;
            }).collect(Collectors.toList()));

        // calcular resumo (inclui receitas + vendas de produtos)
        BigDecimal totalReceitas = dto.getReceitas().stream()
            .map(ReceitaDTO::getValor)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalVendasProdutos = (e.getProdutos() == null ? List.<com.lluanps.gestaoeventos.produto.model.Produto>of() : e.getProdutos()).stream()
            .filter(obj -> obj instanceof com.lluanps.gestaoeventos.produto.model.Produto)
            .map(obj -> ((com.lluanps.gestaoeventos.produto.model.Produto) obj).getTotalVendido())
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        totalReceitas = totalReceitas.add(totalVendasProdutos);

        BigDecimal totalDespesas = dto.getDespesas().stream()
            .map(DespesaDTO::getValor)
            .filter(Objects::nonNull)
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        ResumoDTO resumo = new ResumoDTO();
        resumo.setTotalReceitas(totalReceitas);
        resumo.setTotalDespesas(totalDespesas);
        resumo.setLucro(totalReceitas.subtract(totalDespesas));
        dto.setResumo(resumo);

        return dto;
    }

    @Transactional(readOnly = true)
    public ResumoDTO getResumoPorEventoId(Integer eventoId) {
        BigDecimal totalReceitaByEventoId = receitaRepository.getTotalReceitaByEventoId(eventoId);
        BigDecimal totalDespesaByEventoId = despesaRepository.getTotalDespesaByEventoId(eventoId);

        totalReceitaByEventoId = totalReceitaByEventoId == null ? BigDecimal.ZERO : totalReceitaByEventoId;
        totalDespesaByEventoId = totalDespesaByEventoId == null ? BigDecimal.ZERO : totalDespesaByEventoId;

        BigDecimal totalVendasProdutos = repository.getProdutosByEventoId(eventoId)
            .stream()
            .map(p -> p.getTotalVendido() == null ? BigDecimal.ZERO : p.getTotalVendido())
            .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal receitaTotal = totalReceitaByEventoId.add(totalVendasProdutos);
        BigDecimal despesasTotal = totalDespesaByEventoId;
        BigDecimal lucro = receitaTotal.subtract(despesasTotal);

        return new ResumoDTO(receitaTotal, despesasTotal, lucro);
    }
}