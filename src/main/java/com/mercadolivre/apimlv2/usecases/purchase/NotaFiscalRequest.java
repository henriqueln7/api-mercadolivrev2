package com.mercadolivre.apimlv2.usecases.purchase;

import javax.validation.constraints.NotNull;

public class NotaFiscalRequest {
    @NotNull
    private final Long idCompra;
    @NotNull
    private final Long idComprador;

    public NotaFiscalRequest(@NotNull Long idCompra, @NotNull Long idComprador) {
        this.idCompra = idCompra;
        this.idComprador = idComprador;
    }

    public Long getIdCompra() {
        return idCompra;
    }

    public Long getIdComprador() {
        return idComprador;
    }

    @Override
    public String toString() {
        return "NotaFiscalRequest{" + "idCompra=" + idCompra + ", idComprador=" + idComprador + '}';
    }
}
