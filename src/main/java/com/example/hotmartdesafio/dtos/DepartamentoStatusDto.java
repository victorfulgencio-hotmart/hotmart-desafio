package com.example.hotmartdesafio.dtos;

import lombok.Data;

@Data
public class DepartamentoStatusDto {

    public DepartamentoStatusDto(StatusEnum status) {
        this.status = status;
    }

    public DepartamentoStatusDto(double totalCost, double numeroDepartamento) {
        this.custoTotal = totalCost;
        this.numeroDepartamento = numeroDepartamento;
    }

    public StatusEnum getStatus() {
        if(status != null)
            return status;

        if(custoTotal <= numeroDepartamento)
            return StatusEnum.VERDE;

        // acima em até 10%
        if(numeroDepartamento*1.1 >= custoTotal)
            return StatusEnum.AMARELO;

        return StatusEnum.VERMELHO;
    }

    private StatusEnum status;
    private double custoTotal;
    private double numeroDepartamento;
}
