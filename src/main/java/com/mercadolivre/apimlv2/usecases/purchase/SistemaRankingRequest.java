package com.mercadolivre.apimlv2.usecases.purchase;

public class SistemaRankingRequest {
    public final Long idCompra;
    public final Long idVendedor;

    public SistemaRankingRequest(Long idCompra, Long idVendedor) {
        this.idCompra = idCompra;
        this.idVendedor = idVendedor;
    }

    @Override
    public String toString() {
        return "SistemaRankingRequest{" + "idCompra=" + idCompra + ", idVendedor=" + idVendedor + '}';
    }
}
