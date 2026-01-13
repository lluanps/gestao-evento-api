package com.lluanps.gestaoeventos.resumo.dto;

import java.math.BigDecimal;

public class ResumoDTO {
    private BigDecimal totalReceitas;
    private BigDecimal totalDespesas;
    private BigDecimal lucro;

    public ResumoDTO() {}

    public ResumoDTO(BigDecimal totalReceitas, BigDecimal totalDespesas, BigDecimal lucro) {
        this.totalReceitas = totalReceitas;
        this.totalDespesas = totalDespesas;
        this.lucro = lucro;
    }

    public BigDecimal getTotalReceitas() { return totalReceitas; }
    public void setTotalReceitas(BigDecimal totalReceitas) { this.totalReceitas = totalReceitas; }

    public BigDecimal getTotalDespesas() { return totalDespesas; }
    public void setTotalDespesas(BigDecimal totalDespesas) { this.totalDespesas = totalDespesas; }

    public BigDecimal getLucro() { return lucro; }
    public void setLucro(BigDecimal lucro) { this.lucro = lucro; }
}