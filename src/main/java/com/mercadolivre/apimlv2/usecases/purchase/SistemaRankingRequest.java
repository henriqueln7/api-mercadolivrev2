package com.mercadolivre.apimlv2.usecases.purchase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SistemaRankingRequest {
    @NotBlank
    public final String idCompra;
    @NotNull
    public final Long idVendedor;

    public SistemaRankingRequest(String idCompra, Long idVendedor) {
        this.idCompra = idCompra;
        this.idVendedor = idVendedor;
    }

    @Override
    public String toString() {
        return "SistemaRankingRequest{" + "idCompra=" + idCompra + ", idVendedor=" + idVendedor + '}';
    }
}
