package com.pedrodevjava.pdv.dto;

public class PagamentoDTO {

    private String tipoPagamento; // DINHEIRO, PIX, CARTAO
    private Double valorPago;

    public String getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(String tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Double getValorPago() {
        return valorPago;
    }

    public void setValorPago(Double valorPago) {
        this.valorPago = valorPago;
    }
}